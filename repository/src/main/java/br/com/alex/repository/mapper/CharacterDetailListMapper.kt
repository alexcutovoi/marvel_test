package br.com.alex.repository.mapper

import br.com.alex.repository.store.model.base.BaseDetailEntity
import br.com.alex.repository.store.model.remote.BaseDataWrapperResultEntity
import br.com.alex.repository.store.model.remote.CharacterDetailResultEntity

class CharacterDetailListMapper(private val comicMapper: ResultMapper<CharacterDetailResultEntity, BaseDetailEntity>): ResultMapper<BaseDataWrapperResultEntity<CharacterDetailResultEntity>, List<BaseDetailEntity>> {
    override fun fromEntityApi(a: BaseDataWrapperResultEntity<CharacterDetailResultEntity>): List<BaseDetailEntity> {
        val comicList = ArrayList<BaseDetailEntity>()
        a.data.results.map{
            comicList.add(comicMapper.fromEntityApi(it))
        }

        return comicList
    }

    override fun toEntityApi(e: List<BaseDetailEntity>): BaseDataWrapperResultEntity<CharacterDetailResultEntity> {
        throw RuntimeException()
    }
}