package br.com.alex.repository.mapper

import br.com.alex.domain.model.CharacterModel
import br.com.alex.repository.store.model.local.CharacterEntity

class CharacterDbEntityMapper: EntityMapper<CharacterEntity, CharacterModel>{
    override fun fromEntity(e: CharacterEntity): CharacterModel {
        return CharacterModel(
            e.charactedId,
            e.characterName,
            e.charactedImageSource,
            e.charactedImageHighResSource,
            e.characterDescription,
            e.characterImageData,
            e.characterBookmarked)
    }

    override fun toEntity(m: CharacterModel): CharacterEntity {
        return CharacterEntity(
            m.charactedId,
            m.characterName,
            m.charactedImageSource,
            m.charactedImageSourceHighRes,
            m.characterDescription,
            m.characterImageData,
            m.characterBookmarked
        )
    }
}