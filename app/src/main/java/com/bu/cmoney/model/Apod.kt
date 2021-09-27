package com.bu.cmoney.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Apod(
    val description: String,
    val copyright: String,
    val title: String,
    val url: String,
    val apod_site: String,
    val date: String,
    val media_type: String,
    val hdurl: String
) : Parcelable