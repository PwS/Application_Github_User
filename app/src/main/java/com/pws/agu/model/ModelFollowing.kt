package com.pws.agu.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ModelFollowing(
    var userName: String? = "",
    var name: String? = "",
    var avatar: String? = "",
    var company: String? = "",
    var location: String? = "",
    var repository: String? = "",
    var followers: String? = "",
    var following: String? = ""
) : Parcelable