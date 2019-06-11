package br.com.alex.repository.store.model.remote

import com.google.gson.annotations.SerializedName

class ImageResultEntity(
    @SerializedName("path") val path: String,
    @SerializedName("extension") val extension: String
)