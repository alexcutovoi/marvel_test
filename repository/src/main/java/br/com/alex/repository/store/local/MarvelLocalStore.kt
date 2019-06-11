package br.com.alex.repository.store.local

import br.com.alex.repository.store.MarvelStore
import br.com.alex.repository.store.local.dao.MarvelDatabaseDao
import br.com.alex.repository.store.model.base.BaseDetailEntity
import br.com.alex.repository.store.model.local.CharacterEntity
import io.reactivex.Single
import java.lang.RuntimeException

class MarvelLocalStore(private val marvelDatabaseDao: MarvelDatabaseDao) : MarvelStore {
    override fun getCharacters(offset: Int?): Single<List<CharacterEntity>> {
        return marvelDatabaseDao.getBookmarkedCharacters()
    }

    override fun getCharacter(characterId: Int): Single<CharacterEntity> {
        return marvelDatabaseDao.getBookMarkedCharacter(characterId)
    }

    override fun insertCharacter(character: CharacterEntity): Single<Long>{
        return marvelDatabaseDao.insertCharacter(character)
    }

    override fun deleteCharacter(character: CharacterEntity): Single<Int>{
        return marvelDatabaseDao.deleteCharacter(character)
    }

    override fun getComics(characterId: Int): Single<List<BaseDetailEntity>>{
        throw RuntimeException()
    }

    override fun getSeries(characterId: Int): Single<List<BaseDetailEntity>> {
        throw RuntimeException()
    }
}