package com.braineer.scheduler.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.braineer.scheduler.models.Users
import com.braineer.scheduler.repos.UserRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
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
                    userCreationTimeStamp = firebaseAuth.currentUser?.metadata?.creationTimestamp,
                    routineKey = firebaseAuth.currentUser?.uid?.substring(7,14),
                    lastModification = 0,
                )
                UserRepository().insertNewUser(users)
                authStateLD.value = AuthState.AUTHENTICATED

                callback("Success")

            }.addOnFailureListener {
                errMsgLD.value = it.localizedMessage
                it.localizedMessage?.let { it1 -> callback(it1) }
            }
    }


    fun loginWithGmail(completedTask: Task<GoogleSignInAccount>, callback: (String) -> Unit) {

        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                firebaseAuth.signInWithCredential(credential).addOnCompleteListener {

                    if (UserRepository().isUserExits(firebaseAuth.currentUser?.uid!!)) {

                        authStateLD.value = AuthState.AUTHENTICATED
                        callback("Success")

                    }else{
                        val users = Users(
                            userId = firebaseAuth.currentUser?.uid,
                            userName = account.displayName,
                            emailAddress = firebaseAuth.currentUser?.email,
                            userCreationTimeStamp = firebaseAuth.currentUser?.metadata?.creationTimestamp,
                            routineKey = firebaseAuth.currentUser?.uid?.substring(7,14)
                        )
                        UserRepository().insertNewUser(users)
                        authStateLD.value = AuthState.AUTHENTICATED

                        callback("Success")

                    }
                }.addOnFailureListener {
                    errMsgLD.value = it.localizedMessage
                    it.localizedMessage?.let { it1 -> callback(it1) }
                }
            }
        } catch (e: ApiException) {
            errMsgLD.value = "Something went wrong"
            e.localizedMessage?.let { it1 -> callback(it1) }
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