package br.com.alex.repository.mapper

import br.com.alex.domain.model.BaseDataContainerResultModel
import br.com.alex.domain.model.CharacterDetailResultModel
import br.com.alex.repository.store.model.remote.BaseDataContainerResultEntity
import br.com.alex.repository.store.model.remote.CharacterDetailResultEntity

class CharacterDetailDataContainerEntityMapper(private val characterDetailResultEntityMapper : EntityMapper<CharacterDetailResultEntity, CharacterDetailResultModel>):
    EntityMapper<BaseDataContainerResultEntity<CharacterDetailResultEntity>, BaseDataContainerResultModel<CharacterDetailResultModel>> {

    override fun fromEntity(e: BaseDataContainerResultEntity<CharacterDetailResultEntity>): BaseDataContainerResultModel<CharacterDetailResultModel> {
        return BaseDataContainerResultModel(
            e.offset,
            e.limit,
            e.total,
            e.count,
            e.results.map {
                characterDetailResultEntityMapper.fromEntity(it)
            }
        )
    }

    override fun toEntity(m: BaseDataContainerResultModel<CharacterDetailResultModel>): BaseDataContainerResultEntity<CharacterDetailResultEntity> {
        return BaseDataContainerResultEntity(
            m.offset,
            m.limit,
            m.total,
            m.count,
            m.results.map {
                characterDetailResultEntityMapper.toEntity(it)
            }
        )
    }
}