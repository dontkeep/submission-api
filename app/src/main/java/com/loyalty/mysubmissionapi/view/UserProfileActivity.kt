package com.loyalty.mysubmissionapi.view

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.loyalty.mysubmissionapi.R
import com.loyalty.mysubmissionapi.databinding.ActivityUserProfileBinding
import com.loyalty.mysubmissionapi.model.GHUser
import com.loyalty.mysubmissionapi.model.LocalDB.entity.FavouriteEntity
import com.loyalty.mysubmissionapi.model.response.ResponseDetail
import com.loyalty.mysubmissionapi.view.adapters.FollowerAdapter
import com.loyalty.mysubmissionapi.view.fragments.FollowingFollowersFragment
import com.loyalty.mysubmissionapi.viewModel.ProfileViewModel

class UserProfileActivity : AppCompatActivity() {

   private lateinit var binding: ActivityUserProfileBinding
   private lateinit var userData: ResponseDetail
   private lateinit var userFavData: List<FavouriteEntity>

   private val profileViewModel: ProfileViewModel by viewModels<ProfileViewModel>() {
      ProfileViewModel.Factory(this@UserProfileActivity.application)
   }


   @RequiresApi(Build.VERSION_CODES.TIRAMISU)
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityUserProfileBinding.inflate(layoutInflater)
      setContentView(binding.root)

//      val profileViewModel = ViewModelProvider(
//         this,
//         ViewModelProvider.AndroidViewModelFactory.getInstance(Application()))[ProfileViewModel::class.java]

      val gHUser = if (Build.VERSION.SDK_INT >= 33) {
         intent.getParcelableExtra<GHUser>(GITHUB_USER, GHUser::class.java)
      } else {
         @Suppress("DEPRECATION")
         intent.getParcelableExtra<GHUser>(GITHUB_USER)
      }


      profileViewModel.getUser(gHUser?.username ?: "")

      profileViewModel.user.observe(this) { user ->
         userData = user

         setUserProfile(user)

         val viewPager: ViewPager2 = binding.viewPager

         val sectionPagerAdapter = FollowerAdapter(this)
         sectionPagerAdapter.username = gHUser?.username ?: ""

         viewPager.adapter = sectionPagerAdapter
         val tabs: TabLayout = binding.tabLayout

         TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = when (position) {
               0 -> "Followers ${user.followers.toString()}"
               1 -> "Following ${user.following.toString()}"
               else -> ""
            }
         }.attach()

         profileViewModel.getUsingId(userData.id.toString()).observe(this) { favData ->
            userFavData = favData

            if (favData.isNotEmpty()) {
               binding.fabFav.setImageResource(R.drawable.baseline_favorite_24)
            } else {
               binding.fabFav.setImageResource(R.drawable.baseline_favorite_border_24)
            }
         }
      }
      binding.fabFav.setOnClickListener{
         if (userFavData.isNotEmpty()) {
            profileViewModel.delete(
               FavouriteEntity(
                  userData.id.toString(),
                  userData.name.toString(),
                  userData.login.toString(),
                  userData.avatarUrl.toString(),
               )
            )
         } else {
            profileViewModel.insert(
               FavouriteEntity(
                  userData.id.toString(),
                  userData.name.toString(),
                  userData.login.toString(),
                  userData.avatarUrl.toString(),
               )
            )
         }
      }
   }



   @SuppressLint("SetTextI18n")
   private fun setUserProfile(user: ResponseDetail) {
      Glide.with(binding.imageProfile.context).load(user.avatarUrl).into(binding.imageProfile)

      binding.name.text = user.name

      binding.username.text = "@" + user.login

      binding.location.text = user.location as CharSequence?

      binding.company.text = user.company
   }

   companion object {
      const val GITHUB_USER = "github_user"
   }
}