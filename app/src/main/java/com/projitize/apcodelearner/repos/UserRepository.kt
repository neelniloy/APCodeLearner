package com.braineer.scheduler.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.projitize.apcodelearner.utils.collectionUser
import com.projitize.apcodelearner.models.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserRepository {
    val firebaseAuth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    fun insertNewUser(user: Users) {
        db.collection(collectionUser).document(user.userId!!)
            .set(user)
            .addOnSuccessListener {

            }.addOnFailureListener {

            }
    }

    fun getUserUID():String{
        return firebaseAuth.currentUser!!.uid
    }

    fun getUser(userId: String) : LiveData<Users>{
        val userLD = MutableLiveData<Users>()
        db.collection(collectionUser)
            .document(userId)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                userLD.value = value!!.toObject(Users::class.java)
            }
        return userLD
    }

    fun updateUser(ecomUser: Users) {
        db.collection(collectionUser).document(ecomUser.userId!!)
            .set(ecomUser)
            .addOnSuccessListener {

            }.addOnFailureListener {

            }
    }

    fun isUserExits(userId: String) : Boolean{
        var userExits = false
        db.collection(collectionUser)
            .document(userId)
            .addSnapshotListener { value, error ->
                if (value != null) {
                    userExits = value.exists()
                }
            }
        return userExits
    }

}