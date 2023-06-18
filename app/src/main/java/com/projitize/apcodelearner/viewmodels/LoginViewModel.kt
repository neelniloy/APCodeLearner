package com.projitize.apcodelearner.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.projitize.apcodelearner.models.Users
import com.braineer.scheduler.repos.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginViewModel : ViewModel() {
    enum class AuthState {
        AUTHENTICATED, UNAUTHENTICATED
    }
    val firebaseAuth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val authStateLD: MutableLiveData<AuthState> = MutableLiveData()
    val errMsgLD: MutableLiveData<String> = MutableLiveData()


    init {
        if (firebaseAuth.currentUser != null) {
            authStateLD.value = AuthState.AUTHENTICATED
        } else {
            authStateLD.value = AuthState.UNAUTHENTICATED
        }
    }

    fun loginUser(email: String, pass: String, callback: (String) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, pass)
            .addOnSuccessListener {
                authStateLD.value = AuthState.AUTHENTICATED

                callback("Success")

            }.addOnFailureListener {
                errMsgLD.value = it.localizedMessage

                it.localizedMessage?.let { it1 -> callback(it1) }
            }
    }
    fun registerUser(email: String, pass: String, name: String, callback: (String) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, pass)
            .addOnSuccessListener {
                val users = Users(
                    userId = firebaseAuth.currentUser?.uid,
                    userName = name,
                    emailAddress = firebaseAuth.currentUser?.email,
                    userType = "user",
                    userCreationTimeStamp = firebaseAuth.currentUser?.metadata?.creationTimestamp
                )
                UserRepository().insertNewUser(users)
                authStateLD.value = AuthState.AUTHENTICATED

                callback("Success")

            }.addOnFailureListener {
                errMsgLD.value = it.localizedMessage
                it.localizedMessage?.let { it1 -> callback(it1) }
            }
    }


    fun resetPass(email: String,callback: (String) -> Unit) {
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnSuccessListener {

                callback("Success")

            }.addOnFailureListener {
                callback(it.localizedMessage)
            }
    }


    fun logout() {
        firebaseAuth.currentUser?.let {
            firebaseAuth.signOut()
            authStateLD.value = AuthState.UNAUTHENTICATED
        }
    }
}