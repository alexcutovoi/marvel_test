package br.com.alex.repository.mapper

import br.com.alex.domain.model.BaseDataContainerResultModel
import br.com.alex.domain.model.BaseDataWrapperResultModel
import br.com.alex.domain.model.CharacterResultModel
import br.com.alex.repository.store.model.remote.BaseDataContainerResultEntity
import br.com.alex.repository.store.model.remote.BaseDataWrapperResultEntity
import br.com.alex.repository.store.model.remote.CharacterResultEntity

class CharacterDataWrapperEntityMapper(
    private val baseDataContainerEntityMapper:
    EntityMapper<BaseDataContainerResultEntity<CharacterResultEntity>, BaseDataContainerResultModel<CharacterResultModel>>) :
    EntityMapper<BaseDataWrapperResultEntity<CharacterResultEntity>, BaseDataWrapperResultModel<BaseDataContainerResultModel<CharacterResultModel>>> {
    override fun fromEntity(e: BaseDataWrapperResultEntity<CharacterResultEntity>): BaseDataWrapperResultModel<BaseDataContainerResultModel<CharacterResultModel>> {
        return BaseDataWrapperResultModel(
            e.code,
            e.status,
            baseDataContainerEntityMapper.fromEntity(e.data)
        )
    }

    override fun toEntity(m: BaseDataWrapperResultModel<BaseDataContainerResultModel<CharacterResultModel>>): BaseDataWrapperResultEntity<CharacterResultEntity> {
        return BaseDataWrapperResultEntity(
            m.code,
            m.status,
            baseDataContainerEntityMapper.toEntity(m.data)
        )
    }
}