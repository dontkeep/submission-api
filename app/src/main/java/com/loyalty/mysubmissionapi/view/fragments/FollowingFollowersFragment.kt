package com.loyalty.mysubmissionapi.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.loyalty.mysubmissionapi.R
import com.loyalty.mysubmissionapi.databinding.FragmentFollowingFollowersBinding
import com.loyalty.mysubmissionapi.model.response.UserItemResponse
import com.loyalty.mysubmissionapi.view.UserProfileActivity
import com.loyalty.mysubmissionapi.view.adapters.UserListAdapter
import com.loyalty.mysubmissionapi.viewModel.ProfileViewModel


class FollowingFollowersFragment : Fragment() {

   private lateinit var binding: FragmentFollowingFollowersBinding

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      // Inflate the layout for this fragment
      return inflater.inflate(R.layout.fragment_following_followers, container, false)
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding = FragmentFollowingFollowersBinding.bind(view)

      val username = arguments?.getString(GITHUB_USERNAME)
      val position = arguments?.getInt(ARG_SECTION_NUMBER)

      val profileViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[ProfileViewModel::class.java]

      val layoutManager = LinearLayoutManager(activity)
      binding.rvFollow.layoutManager = layoutManager

      val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
      binding.rvFollow.addItemDecoration(itemDecoration)

      if (position == 1) {
         profileViewModel.getFollowers(username ?: "")
      }
      if (position == 2) {
         profileViewModel.getFollowing(username ?: "")
      }

      profileViewModel.followers.observe(viewLifecycleOwner) { users: List<UserItemResponse> ->
         addUserFol(users)
      }
      profileViewModel.following.observe(viewLifecycleOwner) { users: List<UserItemResponse> ->
         addUserFol(users)
      }
      profileViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
         } else {
            binding.progressBar.visibility = View.GONE
         }
      }
   }

   private fun addUserFol(users: List<UserItemResponse>): Unit {
      val adapter = UserListAdapter()
      adapter.submitList(users)
      binding.rvFollow.adapter = adapter
   }

   companion object {
      const val ARG_SECTION_NUMBER = "section_number"
      const val GITHUB_USERNAME = "github_username"
   }

}