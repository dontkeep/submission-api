package com.loyalty.mysubmissionapi.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loyalty.mysubmissionapi.model.connection.ApiConfig
import com.loyalty.mysubmissionapi.model.response.GithubList
import com.loyalty.mysubmissionapi.model.response.UserItemResponse
import retrofit2.Call
import retrofit2.Callback

class MainViewModel: ViewModel() {
   companion object {
      private val TAG = "MainViewModel"
   }
   private val _users = MutableLiveData<List<UserItemResponse>>()

   private val _loading = MutableLiveData<Boolean>()

   val users:  LiveData<List<UserItemResponse>> = _users
   val loading: LiveData<Boolean> = _loading

   init {
      _loading.value = false
   }

   fun getUsers(login: String){
      _loading.value = true
      _users.value = listOf()

      //connect to api
      val client = ApiConfig.getApiService().getUser(login)
      client.enqueue(object : Callback<GithubList> {
         override fun onResponse(call: Call<GithubList>, response: retrofit2.Response<GithubList>) {
            _loading.value = false
            if(response.isSuccessful){
              val res = response.body()
              if (res != null) {
                 Log.i("Debugging MainViewModel : ", res.toString())
                 _users.value = res.listItems
              }
            }else{
              Log.e(TAG, "Error : ${response.message()}")
            }
         }

         override fun onFailure(call: Call<GithubList>, t: Throwable) {
            _loading.value = false
            Log.e(TAG, "Error: ${t.message}")
         }
      })
   }
}