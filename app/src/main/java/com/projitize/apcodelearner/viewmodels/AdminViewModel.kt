package com.projitize.apcodelearner.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.projitize.apcodelearner.models.MiniProjectModel
import com.projitize.apcodelearner.models.QaModel
import com.projitize.apcodelearner.models.QuizModel
import com.projitize.apcodelearner.models.ReferenceModel
import com.projitize.apcodelearner.utils.collectionMiniProject
import com.projitize.apcodelearner.utils.collectionQA
import com.projitize.apcodelearner.utils.collectionQuiz
import com.projitize.apcodelearner.utils.collectionReference

class AdminViewModel:ViewModel() {

    val db = FirebaseFirestore.getInstance()


    fun addQA(model:QaModel, callback: (String) -> Unit) {
        val modelDoc = db.collection(collectionQA).document()

        modelDoc.set(model)
            .addOnSuccessListener {
                callback("Success")
            }.addOnFailureListener {
                callback("Failed")
            }
    }

    fun addReference(model: ReferenceModel, callback: (String) -> Unit) {
        val modelDoc = db.collection(collectionReference).document()

        modelDoc.set(model)
            .addOnSuccessListener {
                callback("Success")
            }.addOnFailureListener {
                callback("Failed")
            }
    }

    fun addMiniProject(model: MiniProjectModel, callback: (String) -> Unit) {
        val modelDoc = db.collection(collectionMiniProject).document()

        modelDoc.set(model)
            .addOnSuccessListener {
                callback("Success")
            }.addOnFailureListener {
                callback("Failed")
            }
    }

    fun addQuizItem(model: QuizModel, callback: (String) -> Unit) {
        val modelDoc = db.collection(collectionQuiz).document()

        modelDoc.set(model)
            .addOnSuccessListener {
                callback("Success")
            }.addOnFailureListener {
                callback("Failed")
            }
    }

    fun getQaList() : LiveData<List<QaModel>> {
        val modelLD = MutableLiveData<List<QaModel>>()
        db.collection(collectionQA)
            .orderBy("time")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                val tempList = mutableListOf<QaModel>()
                for (doc in value!!.documents) {
                    doc.toObject(QaModel::class.java)?.let { tempList.add(it) }
                }
                modelLD.value = tempList
            }
        return modelLD
    }


    fun getReferenceList() : LiveData<List<ReferenceModel>> {
        val modelLD = MutableLiveData<List<ReferenceModel>>()
        db.collection(collectionReference)
            .orderBy("time")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                val tempList = mutableListOf<ReferenceModel>()
                for (doc in value!!.documents) {
                    doc.toObject(ReferenceModel::class.java)?.let { tempList.add(it) }
                }
                modelLD.value = tempList
            }
        return modelLD
    }

    fun getMiniProjectList() : LiveData<List<MiniProjectModel>> {
        val modelLD = MutableLiveData<List<MiniProjectModel>>()
        db.collection(collectionMiniProject)
            .orderBy("time")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                val tempList = mutableListOf<MiniProjectModel>()
                for (doc in value!!.documents) {
                    doc.toObject(MiniProjectModel::class.java)?.let { tempList.add(it) }
                }
                modelLD.value = tempList
            }
        return modelLD
    }

    fun getQuizItemList() : LiveData<List<QuizModel>> {
        val modelLD = MutableLiveData<List<QuizModel>>()
        db.collection(collectionQuiz)
            .orderBy("time")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                val tempList = mutableListOf<QuizModel>()
                for (doc in value!!.documents) {
                    doc.toObject(QuizModel::class.java)?.let { tempList.add(it) }
                }
                modelLD.value = tempList.shuffled()
            }
        return modelLD
    }

}