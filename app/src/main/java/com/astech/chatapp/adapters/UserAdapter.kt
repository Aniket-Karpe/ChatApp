package com.astech.chatapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.astech.chatapp.R
import com.astech.chatapp.models.User

class UserAdapter(
    private val onItemClickListener: (User) -> Unit // Callback function
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    var userList = listOf<User>()

    fun updateUserList(newUserList: List<User>) {
        userList = newUserList
        notifyDataSetChanged()
    }

    // ViewHolder to bind data to the views
    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userImage: ImageView = itemView.findViewById(R.id.user_image)
        val userName: TextView = itemView.findViewById(R.id.user_name)
        val userEmail: TextView = itemView.findViewById(R.id.user_email)

        init {
            // Set click listener for the item
            itemView.setOnClickListener {
                // Pass the clicked user object to the callback
                onItemClickListener(userList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]

        // Set the user data to the views
        holder.userName.text = user.name
        holder.userEmail.text = user.email

        // Load the user image using Glide or Picasso (make sure to add the necessary dependencies)
        // Glide.with(holder.itemView.context)
        //     .load(user.imageUrl) // URL of the user's image
        //     .circleCrop() // Make the image circular
        //     .into(holder.userImage)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}
