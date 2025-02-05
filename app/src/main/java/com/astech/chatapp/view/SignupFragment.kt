package com.astech.chatapp.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.astech.chatapp.R
import com.astech.chatapp.databinding.FragmentSignupBinding
import com.astech.chatapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class SignupFragment : Fragment() {
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val dbReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")
    private val REQUEST_CODE_GALLERY = 1001
    private var profileImageUri: Uri? = null
//    private val selectImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
//        profileImageUri = it
//        binding.userImage.setImageURI(profileImageUri)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
            btnLogin.setOnClickListener{
                findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
            }

//            uploadIcon.setOnClickListener{
////            openGallery()
//                selectImage.launch("image/*")
//            }

            btnSignUp.setOnClickListener {
                val username = binding.txtUsername.text.toString().trim()
                val email = binding.txtEmail.text.toString().trim()
                val password = binding.txtPassword.text.toString().trim()

                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    binding.txtError.text = "Please fill out all fields"
                    return@setOnClickListener
                }

                // Attempt to sign up the user
                signUpUser(email, password, username, "https://dummyjson.com/icon/abc12/150")
            }

        }
    }

    private fun signUpUser(email: String, password: String, userName: String, imageUri: String) {
        binding.lasLoading.visibility = View.VISIBLE


        binding.txtError.text = ""

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val firebaseUser = auth.currentUser


                    // Get the current user

                    if (firebaseUser != null) {

                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(userName)
                            .setPhotoUri(Uri.parse(imageUri))
                            .build()


                        firebaseUser.updateProfile(profileUpdates)
                            .addOnCompleteListener { profileTask ->
                                if (profileTask.isSuccessful) {
                                    Log.d("SIGNUP", "Profile updated: success")
                                } else {
                                    Log.d("SIGNUP", "Profile update: error ${profileTask.exception?.message}")
                                }

                                val user = User(
                                    firebaseUser?.uid,
                                    email = email,
                                    name = userName,
                                    imageUrl = imageUri
                                )

                                dbReference.child(user.userId.toString()).setValue(user)

                            }
                    }

                    Log.d("SIGNUP", "signUpUser: success")

                    Toast.makeText(requireContext(), "Account Created! Login to start chatting...", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_signupFragment_to_chatListFragment)
                    binding.lasLoading.visibility = View.GONE

                } else {
                    // Failed sign-up
                    binding.txtError.text = task.exception?.message
                    Log.d("SIGNUP", "signUpUser: Error ${task.exception?.message}")
                    binding.lasLoading.visibility = View.GONE
                }



            }
    }



    private fun openGallery() {
        // Intent to open the gallery
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_GALLERY)
    }

    // Handle the result of image selection from gallery
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            // Get the URI of the selected image
            val selectedImageUri: Uri? = data.data

            // Convert the URI to a String (file path or content URI)
            val imageUriString = selectedImageUri?.toString()

            if (imageUriString != null) {
                // Use the image URI string for your required functionality
                // For example, display the image or upload it
                Log.d("SelectedImage", "Image URI: $imageUriString")
                // You can now use imageUriString as needed, e.g., to upload the image, show in an ImageView, etc.
            }
        }
    }



}