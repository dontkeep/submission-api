package com.loyalty.mysubmissionapi.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.loyalty.mysubmissionapi.R
import com.loyalty.mysubmissionapi.databinding.ActivityFavouriteBinding
import com.loyalty.mysubmissionapi.databinding.ActivityMainBinding
import com.loyalty.mysubmissionapi.model.response.UserItemResponse
import com.loyalty.mysubmissionapi.view.adapters.UserListAdapter
import com.loyalty.mysubmissionapi.viewModel.ProfileViewModel

class FavouriteActivity : AppCompatActivity() {

   private lateinit var binding: ActivityFavouriteBinding

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityFavouriteBinding.inflate(layoutInflater)
      setContentView(binding.root)

      val profileViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[ProfileViewModel::class.java]

      val layoutManager = LinearLayoutManager(this)
      binding.rvUserFav.layoutManager = layoutManager

      val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
      binding.rvUserFav.addItemDecoration(itemDecoration)

      profileViewModel.getAll().observe(this) { favData ->
         val userItemList: List<UserItemResponse> = favData.map { data ->
            UserItemResponse(
               data.id,
               data.username,
               data.imageUrl
            )
         }
         setUserFavListData(userItemList)
      }
   }
   private fun setUserFavListData(user: List<UserItemResponse>) {
      val adapter = UserListAdapter()
      adapter.submitList(user)
      binding.rvUserFav.adapter = adapter
   }

}