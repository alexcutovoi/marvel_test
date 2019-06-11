package br.com.alex.repository.store

import br.com.alex.repository.store.model.base.BaseDetailEntity
import br.com.alex.repository.store.model.local.CharacterEntity
import io.reactivex.Single

interface MarvelStore {
    fun getCharacters(offset: Int? = 0): Single<List<CharacterEntity>>
    fun getCharacter(characterId: Int): Single<CharacterEntity>
    fun insertCharacter(character: CharacterEntity): Single<Long>
    fun deleteCharacter(character: CharacterEntity): Single<Int>
    fun getComics(characterId: Int): Single<List<BaseDetailEntity>>
    fun getSeries(characterId: Int): Single<List<BaseDetailEntity>>
}