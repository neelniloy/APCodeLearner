package com.braineer.scheduler.viewmodels

import androidx.lifecycle.ViewModel
import com.braineer.scheduler.models.Users
import com.braineer.scheduler.repos.UserRepository
import com.google.firebase.auth.FirebaseAuth

class UserViewModel : ViewModel() {
    val userRepository = UserRepository()

    fun getCurrentUserId() = FirebaseAuth.getInstance().currentUser?.uid
    fun updateUser(users: Users) = userRepository.updateUser(users)
    fun getUser(userId: String) = userRepository.getUser(userId)
    fun isUserExits(userId: String) = userRepository.isUserExits(userId)
    fun getUserUID():String = userRepository.getUserUID()
}