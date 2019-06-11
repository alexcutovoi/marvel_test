package br.com.alex.domain.model

data class CharacterModel(
    val charactedId: Int,
    val characterName: String,
    val charactedImageSource: String,
    val charactedImageSourceHighRes: String,
    val characterDescription: String,
    var characterImageData: ByteArray,
    var characterBookmarked: Boolean
)