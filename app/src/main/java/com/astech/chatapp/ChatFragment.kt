package com.astech.chatapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.astech.chatapp.adapters.ChatAdapter
import com.astech.chatapp.databinding.FragmentChatBinding
import com.astech.chatapp.models.ChatMessage
import com.astech.chatapp.models.Message
import com.astech.chatapp.models.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson


class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private var senderRoom : String? = null
    private var receiverRoom : String? = null
    val messageList = mutableListOf<ChatMessage>()

    private val messages = mutableListOf(
        ChatMessage("Hello!", false),
        ChatMessage("Hi, how are you?", true),
        ChatMessage("I'm good, thanks! What about you?", false),
        ChatMessage("I'm great! 😊", true)
    )

    private val dbReference: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val userJson = arguments?.getString("selected_user")
        val user = Gson().fromJson(userJson, User::class.java)

        Log.d("CHAT_USER", "onViewCreated: user = $user ")

        val senderId = Firebase.auth.currentUser?.uid
        val receiverId = user.userId

        receiverRoom = senderId + receiverId
        senderRoom = receiverId + senderId

        val recyclerView: RecyclerView = binding.recyclerViewMessages
        recyclerView.layoutManager = LinearLayoutManager(context)
        val chatAdapter = ChatAdapter(messageList)
//        val chatAdapter = ChatAdapter(messages)
        recyclerView.adapter = chatAdapter


        binding.toolbarTitle.title = user.name

//        binding.toolbarTitle.setOnClickListener{
//            findNavController().navigate(R.id.action_chatFragment_to_UITestFragment)
//        }

        /* recycler view */

        dbReference.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()

                    for (messageSnapshot in snapshot.children) {
                        val message = messageSnapshot.getValue(ChatMessage::class.java)
                        if (message != null) {

                            Log.d("MESSAGE_FROM", message.toString())
                            messageList.add(message)  // Add user to the list
                        }
                    }

                    chatAdapter.notifyDataSetChanged()


                }

                override fun onCancelled(error: DatabaseError) {

                }

            })

       binding.btnSend.setOnClickListener{
           if((binding.editTextMessage.text.isEmpty())) {
               Log.d("MESSAGE", "ERROR>>>>>>>>>>>>>")
               return@setOnClickListener
           }

           Log.d("MESSAGE", "onViewCreated: ${binding.editTextMessage.text}")





           val messageObjSender = ChatMessage(message = binding.editTextMessage.text.toString(), sentByUser = true, senderUid = senderId!!)
           val messageObjReceiver = ChatMessage(message =  binding.editTextMessage.text.toString(), sentByUser = false, senderUid = receiverId!!)

           dbReference.child("chats").child(senderRoom!!).child("messages").push()
               .setValue(messageObjSender).addOnSuccessListener {
                   dbReference.child("chats").child(receiverRoom!!).child("messages").push()
                       .setValue(messageObjReceiver)
               }

           binding.editTextMessage.setText("")



       }











    }


}