package br.com.alex.repository.mapper

import br.com.alex.domain.model.CharacterResultModel
import br.com.alex.domain.model.ImageResultModel
import br.com.alex.repository.store.model.remote.CharacterResultEntity
import br.com.alex.repository.store.model.remote.ImageResultEntity

class CharacterResultEntityMapper(private val imageEntityMapper: EntityMapper<ImageResultEntity, ImageResultModel>):
    EntityMapper<CharacterResultEntity, CharacterResultModel> {
    override fun fromEntity(e: CharacterResultEntity): CharacterResultModel {
        return CharacterResultModel(
            e.id,
            e.name,
            imageEntityMapper.fromEntity(e.thumbnail),
            e.description,
            e.bookmarkedCharacter
        )
    }

    override fun toEntity(m: CharacterResultModel): CharacterResultEntity {
        return CharacterResultEntity(
            m.id,
            m.name,
            imageEntityMapper.toEntity(m.thumbnail),
            m.description,
            m.bookmarkedCharacter
        )
    }
}