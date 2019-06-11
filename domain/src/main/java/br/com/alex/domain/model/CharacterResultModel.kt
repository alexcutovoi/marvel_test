package br.com.alex.domain.model

class CharacterResultModel(
    val id: Int,
    val name: String,
    val thumbnail: ImageResultModel,
    val description: String,
    var bookmarkedCharacter: Boolean
)