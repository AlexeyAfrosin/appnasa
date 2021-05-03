package com.afrosin.appnasa.model

import com.google.gson.annotations.SerializedName


data class PictureDTO(
    @field:SerializedName("date") val date: String?,
    @field:SerializedName("explanation") val explanation: String?,
    @field:SerializedName("hdurl") val hdurl: String?,
    @field:SerializedName("media_type") val media_type: String?,
    @field:SerializedName("service_version") val service_version: String?,
    @field:SerializedName("title") val title: String?,
    @field:SerializedName("url") val url: String?
)
