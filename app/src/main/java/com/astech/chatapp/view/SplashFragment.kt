package com.astech.chatapp.view

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.astech.chatapp.R
import com.astech.chatapp.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth

class SplashFragment : Fragment() {

    private var _binding : FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        var anim = binding.laSplash

        anim.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                // Create an intent to start SecondActivity
                launchLoginActivity()
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {
            }

        })

        return binding.root
    }

    private fun launchLoginActivity() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            // User is logged in, navigate to the main screen (e.g., HomeFragment)
            findNavController().navigate(R.id.action_splashFragment_to_chatListFragment)
        } else {
            // User is not logged in, stay on the login screen
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)

        }
    }


}