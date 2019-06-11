package br.com.alex.repository.mapper

import br.com.alex.domain.model.CharacterDetailResultModel
import br.com.alex.repository.store.model.base.BaseDetailEntity
import java.lang.RuntimeException

class CharacterDetailListEntityMapper(private val characterDetailEntityMapper: EntityMapper<BaseDetailEntity, CharacterDetailResultModel>): EntityMapper<List<BaseDetailEntity>, List<CharacterDetailResultModel>> {
    override fun fromEntity(e: List<BaseDetailEntity>): List<CharacterDetailResultModel> {
        val comics = ArrayList<CharacterDetailResultModel>()
        e.map {
            comics.add(characterDetailEntityMapper.fromEntity(it))
        }
        return comics
    }

    override fun toEntity(m: List<CharacterDetailResultModel>): List<BaseDetailEntity> {
        throw RuntimeException()
    }
}