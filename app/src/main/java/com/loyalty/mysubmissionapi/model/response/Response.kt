package com.loyalty.mysubmissionapi.model.response

import com.google.gson.annotations.SerializedName

data class GithubList(
	@field:SerializedName("total_count")
	val totalUser: Int? = null,

	@field:SerializedName("incomplete_results")
	val isIncomplete: Boolean? = null,

	@field:SerializedName("items")
	val listItems: List<UserItemResponse>
)

data class UserItemResponse(

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null

)
