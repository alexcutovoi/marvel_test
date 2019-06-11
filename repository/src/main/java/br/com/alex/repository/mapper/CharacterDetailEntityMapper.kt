package br.com.alex.repository.mapper

import br.com.alex.domain.model.CharacterDetailResultModel
import br.com.alex.repository.store.model.base.BaseDetailEntity

class CharacterDetailEntityMapper: EntityMapper<BaseDetailEntity, CharacterDetailResultModel> {
    override fun fromEntity(e: BaseDetailEntity): CharacterDetailResultModel {
        return CharacterDetailResultModel(e.title, e.thumbnail)
    }

    override fun toEntity(m: CharacterDetailResultModel): BaseDetailEntity {
        return BaseDetailEntity(m.title, m.thumbnail)
    }
}