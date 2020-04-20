package com.manwal.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("description") val description: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("title") val title: String? = null
)