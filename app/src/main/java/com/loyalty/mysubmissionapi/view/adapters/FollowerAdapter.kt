package com.loyalty.mysubmissionapi.view.adapters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.loyalty.mysubmissionapi.view.fragments.FollowingFollowersFragment

class FollowerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
   override fun getItemCount(): Int {
      return 2
   }

   var username: String = ""

   override fun createFragment(position: Int): Fragment {
      val fragment = FollowingFollowersFragment()

      fragment.arguments = Bundle().apply {
         putInt(FollowingFollowersFragment.ARG_SECTION_NUMBER, position + 1)
         putString(FollowingFollowersFragment.GITHUB_USERNAME, username)
      }
      return fragment
   }

}