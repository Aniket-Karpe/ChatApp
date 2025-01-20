package com.example.android_notes_app.viemodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_notes_app.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.android.gms.tasks.Task

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

