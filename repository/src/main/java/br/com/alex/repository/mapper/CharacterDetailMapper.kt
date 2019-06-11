package br.com.alex.repository.mapper

import br.com.alex.repository.store.model.base.BaseDetailEntity
import br.com.alex.repository.store.model.remote.CharacterDetailResultEntity
import br.com.alex.repository.store.model.remote.ImageResultEntity

class CharacterDetailMapper: ResultMapper<CharacterDetailResultEntity, BaseDetailEntity>{
    override fun fromEntityApi(a: CharacterDetailResultEntity): BaseDetailEntity {
        return BaseDetailEntity(
            a.title,
            "${a.thumbnail.path}/standard_medium.${a.thumbnail.extension}"
        )
    }

    override fun toEntityApi(e: BaseDetailEntity): CharacterDetailResultEntity {
        val imageSourceData = e.thumbnail.split(".")
        return CharacterDetailResultEntity(
            e.title,
            ImageResultEntity(imageSourceData[0], imageSourceData[1])
        )
    }
}