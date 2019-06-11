package br.com.alex.repository.mapper

import br.com.alex.domain.model.BaseDataContainerResultModel
import br.com.alex.domain.model.BaseDataWrapperResultModel
import br.com.alex.domain.model.CharacterDetailResultModel
import br.com.alex.repository.store.model.remote.BaseDataContainerResultEntity
import br.com.alex.repository.store.model.remote.BaseDataWrapperResultEntity
import br.com.alex.repository.store.model.remote.CharacterDetailResultEntity

class CharacterDetailDataWrapperEntityMapper(
    private val characterDataContainerEntityMapper: EntityMapper<BaseDataContainerResultEntity<CharacterDetailResultEntity>, BaseDataContainerResultModel<CharacterDetailResultModel>>) :
    EntityMapper<BaseDataWrapperResultEntity<CharacterDetailResultEntity>, BaseDataWrapperResultModel<BaseDataContainerResultModel<CharacterDetailResultModel>>> {
    override fun fromEntity(e: BaseDataWrapperResultEntity<CharacterDetailResultEntity>): BaseDataWrapperResultModel<BaseDataContainerResultModel<CharacterDetailResultModel>> {
        return BaseDataWrapperResultModel(
            e.code,
            e.status,
            characterDataContainerEntityMapper.fromEntity(e.data)
        )
    }

    override fun toEntity(m: BaseDataWrapperResultModel<BaseDataContainerResultModel<CharacterDetailResultModel>>): BaseDataWrapperResultEntity<CharacterDetailResultEntity> {
        return BaseDataWrapperResultEntity(
            m.code,
            m.status,
            characterDataContainerEntityMapper.toEntity(m.data)
        )
    }
}