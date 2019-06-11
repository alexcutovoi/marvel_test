package br.com.alex.repository.mapper

import br.com.alex.domain.model.CharacterModel
import br.com.alex.repository.store.model.local.CharacterEntity
import br.com.alex.repository.store.model.remote.CharacterResultEntity
import java.lang.RuntimeException


class CharacterListEntityMapper(private val characterDbEntityMapper: EntityMapper<CharacterEntity, CharacterModel>) : EntityMapper<List<CharacterEntity>, List<CharacterModel>> {
    override fun fromEntity(e: List<CharacterEntity>): List<CharacterModel> {
        val characters = ArrayList<CharacterModel>()
        e.map {
            characters.add(characterDbEntityMapper.fromEntity(it))
        }

        return characters
    }

    override fun toEntity(m: List<CharacterModel>): List<CharacterEntity> {
        throw RuntimeException()
    }
}