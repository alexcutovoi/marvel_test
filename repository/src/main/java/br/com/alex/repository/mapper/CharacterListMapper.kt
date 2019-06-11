package br.com.alex.repository.mapper

import br.com.alex.repository.store.model.remote.BaseDataWrapperResultEntity
import br.com.alex.repository.store.model.local.CharacterEntity
import br.com.alex.repository.store.model.remote.CharacterResultEntity
import java.lang.RuntimeException

class CharacterListMapper(private val characterMapper: ResultMapper<CharacterResultEntity, CharacterEntity>): ResultMapper<BaseDataWrapperResultEntity<CharacterResultEntity>, List<CharacterEntity>> {
    override fun fromEntityApi(a: BaseDataWrapperResultEntity<CharacterResultEntity>): List<CharacterEntity> {
        val characterList = ArrayList<CharacterEntity>()
        a.data.results.map{
            characterList.add(characterMapper.fromEntityApi(it))
        }

        return characterList
    }

    override fun toEntityApi(e: List<CharacterEntity>): BaseDataWrapperResultEntity<CharacterResultEntity> {
        throw RuntimeException()
    }
}