package com.loyalty.mysubmissionapi.view.adapters

import android.annotation.SuppressLint
import android.app.LauncherActivity.ListItem
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyalty.mysubmissionapi.databinding.ItemUserBinding
import com.loyalty.mysubmissionapi.model.GHUser
import com.loyalty.mysubmissionapi.model.response.UserItemResponse
import com.loyalty.mysubmissionapi.view.UserProfileActivity

class UserListAdapter: ListAdapter<UserItemResponse, UserListAdapter.ViewHolder>(DIFF_CALLBACK) {
   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)

      return ViewHolder(binding)
   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val item = getItem(position)
      holder.bind(item)

      val githubUser = GHUser(
         item.login
      )
      holder.itemView.setOnClickListener { view ->
         val intent = Intent(holder.itemView.context, UserProfileActivity::class.java)
         intent.putExtra(UserProfileActivity.GITHUB_USER, githubUser)
         view.context.startActivity(intent)
      }
   }

   class ViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
      @SuppressLint("SetTextI18n")
      fun bind(item: UserItemResponse) {
         binding.profileUsername.text = item.login
         binding.profileName.text = "ID : " + item.id.toString()

         Glide.with(binding.profileImage.context).load(item.avatarUrl).into(binding.profileImage)
      }
   }

   companion object {
      val DIFF_CALLBACK = object :DiffUtil.ItemCallback<UserItemResponse>(){
         override fun areItemsTheSame(oldItem: UserItemResponse, newItem: UserItemResponse): Boolean {
            return oldItem == newItem
         }

         override fun areContentsTheSame(oldItem: UserItemResponse, newItem: UserItemResponse): Boolean {
            return oldItem == newItem
         }
      }
   }

}