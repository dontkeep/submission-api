package com.loyalty.mysubmissionapi.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GHUser(
   val username: String?
) : Parcelable
