package com.loyalty.mysubmissionapi.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.loyalty.mysubmissionapi.databinding.ActivityMainBinding
import com.loyalty.mysubmissionapi.view.adapters.UserListAdapter
import com.loyalty.mysubmissionapi.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
   private lateinit var binding: ActivityMainBinding
   private lateinit var mainViewModel: MainViewModel

   private val searchResult: ActivityResultLauncher<Intent> = registerForActivityResult(
      ActivityResultContracts.StartActivityForResult()
   ) {
      res -> if (res.resultCode == SearchActivity.RESULT && res.data != null) {
         val username = res.data?.getStringExtra(SearchActivity.EXTRA_SELECTED_USER)
      if (username != null) {
         mainViewModel.getUsers(username)
      }
      }
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityMainBinding.inflate(layoutInflater)
      setContentView(binding.root)

      binding.searchBtn.setOnClickListener {
         val intent = Intent(this@MainActivity, SearchActivity::class.java)
         searchResult.launch(intent)

      }

      mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

      val layoutManager = LinearLayoutManager(this)
      binding.rvUsers.layoutManager = layoutManager

      val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
      binding.rvUsers.addItemDecoration(itemDecoration)

      binding.fabGotoFav.setOnClickListener {
         val intent = Intent(this@MainActivity, FavouriteActivity::class.java)
         startActivity(intent)
      }
      
      mainViewModel.users.observe(this) { user ->
         Log.i("debugging", user.toString())
         val adapter = UserListAdapter()
         adapter.submitList(user)
         binding.rvUsers.adapter = adapter
      }
      mainViewModel.loading.observe(this) { loading ->
         if (loading) {
            binding.progressBar.visibility = View.VISIBLE
         } else {
            binding.progressBar.visibility = View.GONE
         }
      }
   }
}