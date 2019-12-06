package com.mo.jetpack.navigation.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ContentModel(val title: String, val body: String, val author: String): Parcelable