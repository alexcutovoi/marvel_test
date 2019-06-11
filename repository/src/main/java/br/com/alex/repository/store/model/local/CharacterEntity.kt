package br.com.alex.repository.store.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="characters_table")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = false)
    val charactedId: Int,

    @ColumnInfo(name = "character_name")
    val characterName: String,

    @ColumnInfo(name = "character_image_source")
    val charactedImageSource: String,

    @ColumnInfo(name = "character_image_source_high_res")
    val charactedImageHighResSource: String,

    @ColumnInfo(name = "character_description")
    val characterDescription: String,

    @ColumnInfo(name = "character_image_data", typeAffinity = ColumnInfo.BLOB)
    val characterImageData: ByteArray,

    @ColumnInfo(name = "character_bookmarked")
    var characterBookmarked: Boolean
)