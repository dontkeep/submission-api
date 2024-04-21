package com.loyalty.mysubmissionapi.model.connection

import com.loyalty.mysubmissionapi.model.response.GithubList
import com.loyalty.mysubmissionapi.model.response.UserItemResponse
import com.loyalty.mysubmissionapi.model.response.ResponseDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
   @GET("search/users")
   fun getUser(@Query("q") name: String): Call<GithubList>

   @GET("users/{username}")
   fun getOneUser(@Path("username") username: String): Call<ResponseDetail>

   @GET("users/{username}/followers")
   fun getFollowers(@Path("username")  username: String): Call<List<UserItemResponse>>

   @GET("users/{username}/following")
   fun getFollowing(@Path("username") username: String): Call<List<UserItemResponse>>
}