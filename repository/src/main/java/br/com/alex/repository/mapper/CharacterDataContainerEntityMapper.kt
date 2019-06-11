package br.com.alex.repository.mapper

import br.com.alex.domain.model.BaseDataContainerResultModel
import br.com.alex.domain.model.CharacterResultModel
import br.com.alex.repository.store.model.remote.BaseDataContainerResultEntity
import br.com.alex.repository.store.model.remote.CharacterResultEntity

class CharacterDataContainerEntityMapper(private val characterResultEntityMapper : EntityMapper<CharacterResultEntity, CharacterResultModel>):
    EntityMapper<BaseDataContainerResultEntity<CharacterResultEntity>, BaseDataContainerResultModel<CharacterResultModel>> {

    override fun fromEntity(e: BaseDataContainerResultEntity<CharacterResultEntity>): BaseDataContainerResultModel<CharacterResultModel> {
        return BaseDataContainerResultModel(
            e.offset,
            e.limit,
            e.total,
            e.count,
            e.results.map {
                characterResultEntityMapper.fromEntity(it)
            }
        )
    }

    override fun toEntity(m: BaseDataContainerResultModel<CharacterResultModel>): BaseDataContainerResultEntity<CharacterResultEntity> {
        return BaseDataContainerResultEntity(
            m.offset,
            m.limit,
            m.total,
            m.count,
            m.results.map {
                characterResultEntityMapper.toEntity(it)
            }
        )
    }
}