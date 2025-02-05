package com.astech.chatapp.viemodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.astech.chatapp.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    val authState: MutableLiveData<FirebaseUser?> = MutableLiveData(null)
    val signInError: MutableLiveData<String> = MutableLiveData()

    fun signInWithGoogle(account: GoogleSignInAccount?) {
        authRepository.firebaseAuthWithGoogle(account) { task ->
            if (task.isSuccessful) {
                val user = task.result?.user
                authState.value = user
            } else {
                signInError.value = "Authentication failed"
            }
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        return authRepository.getCurrentUser()
    }

    fun signOut() {
        authRepository.signOut()
        authState.value = null
    }
}

