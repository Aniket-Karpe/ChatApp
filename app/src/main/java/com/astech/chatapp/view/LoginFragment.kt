package com.astech.chatapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.astech.chatapp.R
import com.astech.chatapp.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.android.gms.tasks.Task
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.AuthResult

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private var mAuth: FirebaseAuth? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private val RC_SIGN_IN = 9001

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()


        binding.btnLogin.setOnClickListener {
            val email = binding.txtEmail.text.toString().trim()
            val password = binding.txtPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                binding.txtError.text = "Please enter email and password"
                return@setOnClickListener
            }

            // Attempt to sign in
            signInUser(email, password)
        }

        binding.apply {
            btnSignUp.setOnClickListener{
                findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
            }
        }



    }
    private fun signInUser(email: String, password: String) {
        showLoadingAnimation()
        binding.txtError.text = ""

        mAuth?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Successful sign-in
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                    // Navigate to the next screen or activity
                    findNavController().navigate(R.id.action_loginFragment_to_chatListFragment)
                } else {
                    // Failed sign-in
                    stopLoadingAnimation()
                    binding.txtError.text = task.exception?.message
                }
            }
    }

    private fun showLoadingAnimation() {
        binding.apply {
            laLoading.visibility = View.VISIBLE
            btnSignUp.visibility = View.GONE
            btnLogin.visibility = View.GONE
            txtError.visibility = View.GONE
            txtEmail.visibility = View.GONE
            txtPassword.visibility = View.GONE
            txtHeading.visibility = View.GONE
            txtSubHeading.visibility = View.GONE
            txtEmailHeading.visibility = View.GONE
            txtPasswordHeading.visibility = View.GONE
            v1.visibility = View.GONE
            v2.visibility = View.GONE
        }
    }
    private fun stopLoadingAnimation() {
        binding.apply {
            laLoading.visibility = View.GONE
            btnSignUp.visibility = View.VISIBLE
            btnLogin.visibility = View.VISIBLE
            txtError.visibility = View.VISIBLE
            txtEmail.visibility = View.VISIBLE
            txtPassword.visibility = View.VISIBLE
            txtHeading.visibility = View.VISIBLE
            txtSubHeading.visibility = View.VISIBLE
            txtEmailHeading.visibility = View.VISIBLE
            txtPasswordHeading.visibility = View.VISIBLE
            v1.visibility = View.VISIBLE
            v2.visibility = View.VISIBLE
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
