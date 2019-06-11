package br.com.alex.repository.store.model.remote

import com.google.gson.annotations.SerializedName

class CharacterDetailResultEntity(
    @SerializedName("title") val title: String,
    @SerializedName("thumbnail")val thumbnail: ImageResultEntity
)