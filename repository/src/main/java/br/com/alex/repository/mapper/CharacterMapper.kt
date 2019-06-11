package br.com.alex.repository.mapper

import br.com.alex.repository.store.model.local.CharacterEntity
import br.com.alex.repository.store.model.remote.CharacterResultEntity
import br.com.alex.repository.store.model.remote.ImageResultEntity

class CharacterMapper: ResultMapper<CharacterResultEntity, CharacterEntity>{
    override fun fromEntityApi(a: CharacterResultEntity): CharacterEntity {
        return CharacterEntity(
            a.id,
            a.name,
            "${a.thumbnail.path}/standard_medium.${a.thumbnail.extension}",
            "${a.thumbnail.path}.${a.thumbnail.extension}",
            a.description,
            ByteArray(0),
            a.bookmarkedCharacter
        )
    }

    override fun toEntityApi(e: CharacterEntity): CharacterResultEntity {
        val imageSourceData = e.charactedImageSource.split(".")
        return CharacterResultEntity(
            e.charactedId,
            e.characterName,
            ImageResultEntity(imageSourceData[0], imageSourceData[1]),
            e.characterDescription,
            e.characterBookmarked
        )
    }
}