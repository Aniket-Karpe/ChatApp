package com.example.android_notes_app.repository


import android.app.Application
import android.content.Context
import android.util.Log
import com.example.android_notes_app.utils.NetworkUtils
import com.example.android_notes_app.utils.Utils
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

class AuthRepository(val context: Context) {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun firebaseAuthWithGoogle(account: GoogleSignInAccount?, callback: (Task<AuthResult>) -> Unit) {

        if(NetworkUtils.isInternetAvailable(context.applicationContext)) {
            Log.d("INTERNET", "Internet available")
        } else {
            Log.d("INTERNET", "Internet not available")

        }

        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                callback(task)
            }
    }

    fun getCurrentUser(): FirebaseUser? {
        return mAuth.currentUser
    }

    fun signOut() {
        mAuth.signOut()
    }
}
