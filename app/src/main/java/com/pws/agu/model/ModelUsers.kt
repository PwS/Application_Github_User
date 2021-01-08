package com.pws.agu.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ModelUsers(
    var userName: String? = "NoName",
    var name: String? = "NoName",
    var avatar: String? = "",
    var company: String? = "",
    var location: String? = "",
    var repository: String? = "",
    var followers: String? = "",
    var following: String? = ""
) : Parcelable

