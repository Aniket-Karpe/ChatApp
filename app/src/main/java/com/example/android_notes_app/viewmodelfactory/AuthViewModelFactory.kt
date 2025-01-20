package com.example.android_notes_app.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android_notes_app.repository.AuthRepository
import com.example.android_notes_app.viemodels.AuthViewModel

class AuthViewModelFactory(private val authRepository: AuthRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(authRepository) as T
    }

}