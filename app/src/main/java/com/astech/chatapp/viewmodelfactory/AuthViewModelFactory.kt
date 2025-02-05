package com.astech.chatapp.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.astech.chatapp.repository.AuthRepository
import com.astech.chatapp.viemodels.AuthViewModel

class AuthViewModelFactory(private val authRepository: AuthRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(authRepository) as T
    }

}