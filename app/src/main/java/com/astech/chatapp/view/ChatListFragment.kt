package com.astech.chatapp.view

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.astech.chatapp.R
import com.astech.chatapp.adapters.UserAdapter
import com.astech.chatapp.databinding.FragmentChatListBinding
import com.astech.chatapp.databinding.FragmentSignupBinding
import com.astech.chatapp.models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.gson.Gson
import okhttp3.internal.filterList

class ChatListFragment : Fragment() {
    private var _binding: FragmentChatListBinding? = null
    private val binding get() = _binding!!


//    private var _binding: FragmentChatListBinding? = null
//    private val binding get() = _binding!!
    private lateinit var userAdapter: UserAdapter
    private val dbReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")
    val userList = mutableListOf<User>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentChatListBinding.inflate(inflater, container, false)
        println("USERID: ${FirebaseAuth.getInstance().currentUser?.uid}")
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun filterList(query: String?) {
        val filteredUsers = userList.filter { it.name.contains(query.toString() , ignoreCase = true) || it.email.contains(query.toString() , ignoreCase = true) }
        Log.d("LISTTTT", "filterList: ${filteredUsers.toString()}")
        // how can i pass this new list to userAdapter

        userAdapter.updateUserList(filteredUsers)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Not needed for this use case
                Log.d("QUERYYYYYYY", "newSubmittedQuery: $query")

//                filterList(query)

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Filter users as the user types the query

                Log.d("QUERYYYYYYY", "newQuery: $newText")
//                filterUsers(newText)
                filterList(newText)

                return true
            }
        })

        binding.iconLogout.setOnClickListener{
            logout()
        }

        // Initialize RecyclerView
        val recyclerView: RecyclerView = binding.recyclerViewUsers
        recyclerView.layoutManager = LinearLayoutManager(context)

//        showLoadingAnimation()

        try {
            if(_binding != null) {
                fetchUsers {
//                    hideLoadingAnimation()

                    Log.d("USERS", "onViewCreated: users: ${it.toString()}")
//            userAdapter = UserAdapter(it)
                    userAdapter = UserAdapter(::userClickHandlerCallback)
                    userAdapter.updateUserList(it)
//                    userAdapter = UserAdapter() { user ->
//
//                        // Handle item click, you now have the clicked user object
//
//                        Log.d("USER ITEM", "onViewCreated: ${user.toString()}")
//
//                        var bundle = Bundle()
//
//                        bundle.putString("selected_user", Gson().toJson(user))
//
//                        findNavController().navigate(R.id.action_chatListFragment_to_chatFragment, bundle)
//                    }
                    recyclerView.adapter = userAdapter

                }

            }

        } catch (e: Exception) {
            Log.e("ERROR", e.message.toString())
        }

//        hideLoadingAnimation()


        // Initialize adapter with user data


        // Read from the database

    }

    private fun fetchUsers(callback: (List<User>) -> Unit) {
//        showLoadingAnimation()
        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Clear the list before adding fresh data
                Log.d("FIREBASE", "Snapshot exists: ${snapshot.exists()}")
                userList.clear()
                // Loop through each child in the snapshot
                for (userSnapshot in snapshot.children) {
                    Log.d("FIREBASE", "Raw User Data: ${userSnapshot.value}")
                    val user = userSnapshot.getValue(User::class.java)
                    if (user != null && user.userId != FirebaseAuth.getInstance().currentUser?.uid) {
                        Log.d("FIREBASE", "Fetched User: ${user.name}, ${user.email}, ${user.userId}")
                        println("USERID: ${FirebaseAuth.getInstance().currentUser?.uid}")
                        userList.add(user)
                        println("USER: ${user.name}")    // Add user to the list
                    }
                    else{
                        Log.e("FIREBASE_ERROR", "User is null for snapshot: ${userSnapshot.value}")
                    }
                }
                // Call the callback function with the list of users
                callback(userList)
            }
            override fun onCancelled(error: DatabaseError) {
                // Handle any errors (e.g., permission issues)
                println("Error: ${error.message}")
                Log.e("FIREBASE", "Database Error: ${error.message}")
            }
        })
//        hideLoadingAnimation()
    }

    private fun hideLoadingAnimation() {
        binding.apply {
            laLoading.visibility = View.GONE
            recyclerViewUsers.visibility = View.VISIBLE
        }
    }

    private fun loadingAnimation(anim: Boolean) {
        if(anim) {
            binding.apply {
                laLoading.visibility = View.VISIBLE
                recyclerViewUsers.visibility = View.GONE
            }
        } else {
            binding.apply {
                laLoading.visibility = View.GONE
                recyclerViewUsers.visibility = View.VISIBLE
            }
        }

    }

    private fun showLoadingAnimation() {
        binding.apply {
            laLoading.visibility = View.VISIBLE
            recyclerViewUsers.visibility = View.GONE
        }
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        findNavController().navigate(R.id.action_chatListFragment_to_loginFragment)
    }

    fun userClickHandlerCallback(user: User) {

        // Handle item click, you now have the clicked user object

        Log.d("USER ITEM", "onViewCreated: ${user.toString()}")

        var bundle = Bundle()

        bundle.putString("selected_user", Gson().toJson(user))

        findNavController().navigate(R.id.action_chatListFragment_to_chatFragment, bundle)
    }





}