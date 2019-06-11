package br.com.alex.repository.store.model.remote

import com.google.gson.annotations.SerializedName

class CharacterResultEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("thumbnail") val thumbnail: ImageResultEntity,
    @SerializedName("description") val description: String,
    var bookmarkedCharacter: Boolean = false
)
