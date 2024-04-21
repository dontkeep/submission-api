package com.loyalty.mysubmissionapi.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.loyalty.mysubmissionapi.model.LocalDB.FavouriteRepository
import com.loyalty.mysubmissionapi.model.LocalDB.entity.FavouriteEntity
import com.loyalty.mysubmissionapi.model.connection.ApiConfig
import com.loyalty.mysubmissionapi.model.response.GithubList
import com.loyalty.mysubmissionapi.model.response.ResponseDetail
import com.loyalty.mysubmissionapi.model.response.UserItemResponse
import retrofit2.Call
import retrofit2.Callback

class ProfileViewModel(application : Application): ViewModel() {


   private val FavRepo: FavouriteRepository = FavouriteRepository(application)

   private val _user = MutableLiveData<ResponseDetail>()

   private val _followers = MutableLiveData<List<UserItemResponse>>()

   private val _following = MutableLiveData<List<UserItemResponse>>()

   private val _isLoading = MutableLiveData<Boolean>()

   val user: LiveData<ResponseDetail> = _user
   val followers: LiveData<List<UserItemResponse>> = _followers
   val following: LiveData<List<UserItemResponse>> = _following
   val isLoading: LiveData<Boolean> = _isLoading

   fun insert(note: FavouriteEntity) = FavRepo.insert(note)

   fun delete(note: FavouriteEntity) = FavRepo.delete(note)

   fun getAll() = FavRepo.getAll()

   fun getUsingId(id: String) = FavRepo.getUsingId(id)

   init {
      _isLoading.value = false
   }

   fun getUser(login: String){
      _isLoading.value = true

      val client = ApiConfig.getApiService().getOneUser(login)
      client.enqueue(object : Callback<ResponseDetail> {
         override fun onResponse(
            call: Call<ResponseDetail>,
            response: retrofit2.Response<ResponseDetail>
         ) {
            _isLoading.value = false

            if (response.isSuccessful) {
               val responseBody = response.body()
               if (responseBody != null) {
                  _user.value = responseBody ?: null
               }
            } else {
               Log.i("Error response : ", response.message())
               Log.e(TAG, "Error 1 : ${response.toString()}")
            }
         }

         override fun onFailure(call: Call<ResponseDetail>, t: Throwable) {
            _isLoading.value = false
         }
      })
   }

   fun getFollowers(login: String){
      _isLoading.value = true

      _followers.value = listOf()
      //connect to api
      val client = ApiConfig.getApiService().getFollowers(login)
      client.enqueue(object : Callback<List<UserItemResponse>>{
         override fun onResponse(call: Call<List<UserItemResponse>>, response: retrofit2.Response<List<UserItemResponse>>) {
            _isLoading.value = false
            if(response.isSuccessful){
               val res = response.body()
               if (res != null) {
                  _followers.value = res ?: null
               }
            }else{
               Log.e(TAG, "Error 2 : ${response.message()}")
            }
         }

         override fun onFailure(call: Call<List<UserItemResponse>>, t: Throwable) {
            _isLoading.value = false
            Log.e(TAG, "Error: ${t.message}")
         }
      })
   }

   fun getFollowing(login: String){
      _isLoading.value = true

      _followers.value = listOf()
      //connect to api
      val client = ApiConfig.getApiService().getFollowing(login)
      client.enqueue(object : Callback<List<UserItemResponse>>{
         override fun onResponse(call: Call<List<UserItemResponse>>, response: retrofit2.Response<List<UserItemResponse>>) {
            _isLoading.value = false
            if(response.isSuccessful){
               val res = response.body()
               if (res != null) {
                  _followers.value = res ?: null
               }
            }else{
               Log.e(TAG, "Error 3 : ${response.message()}")
            }
         }

         override fun onFailure(call: Call<List<UserItemResponse>>, t: Throwable) {
            _isLoading.value = false
            Log.e(TAG, "Error: ${t.message}")
         }
      })
   }

   class Factory(private val application: Application) : ViewModelProvider.Factory {

      @Suppress("UNCHECKED_CAST")
      override fun <T : ViewModel> create(modelClass: Class<T>): T =
         ProfileViewModel(application) as T

   }

   companion object {
      private const val TAG = "ProfileViewModel"
   }
}