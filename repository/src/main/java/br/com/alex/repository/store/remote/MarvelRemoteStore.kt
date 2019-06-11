package br.com.alex.repository.store.remote

import br.com.alex.repository.mapper.ResultMapper
import br.com.alex.repository.store.MarvelStore
import br.com.alex.repository.store.local.dao.MarvelDatabaseDao
import br.com.alex.repository.store.model.base.BaseDetailEntity
import br.com.alex.repository.store.model.local.CharacterEntity
import br.com.alex.repository.store.model.remote.BaseDataWrapperResultEntity
import br.com.alex.repository.store.model.remote.CharacterDetailResultEntity
import br.com.alex.repository.store.model.remote.CharacterResultEntity
import br.com.alex.repository.store.remote.api.MarvelApi
import br.com.alex.repository.store.util.NetworkUtils
import io.reactivex.Single

class MarvelRemoteStore(private val characterListMapper: ResultMapper<BaseDataWrapperResultEntity<CharacterResultEntity>, List<CharacterEntity>>,
                        private val characterMapper: ResultMapper<CharacterResultEntity, CharacterEntity>,
                        private val comicListMapper: ResultMapper<BaseDataWrapperResultEntity<CharacterDetailResultEntity>, List<BaseDetailEntity>>,
                        private val marvelDataBaseDao: MarvelDatabaseDao,
                        private val marvelApi: MarvelApi,
                        networkUtils: NetworkUtils) : BaseRemoteStore(networkUtils),
    MarvelStore {
    override fun getCharacters(offset:Int?): Single<List<CharacterEntity>> {
        return makeBaseEntityApiRequest{
            marvelApi.getCharacters(offset)
        }.map {
            characterListMapper.fromEntityApi(it)
        }
    }

    override fun getCharacter(characterId: Int): Single<CharacterEntity> {
        return makeBaseEntityApiRequest{
            marvelApi.getCharacter(characterId)
        }.map{
            characterMapper.fromEntityApi(it.data.results[0])
        }
    }

    override fun insertCharacter(character: CharacterEntity): Single<Long>{
        return marvelDataBaseDao.insertCharacter(character)
    }

    override fun deleteCharacter(character: CharacterEntity): Single<Int>{
        return marvelDataBaseDao.deleteCharacter(character)
    }

    override fun getComics(characterId: Int): Single<List<BaseDetailEntity>> {
        return makeBaseEntityApiRequest{
            marvelApi.getComics(characterId)
        }.map {
            comicListMapper.fromEntityApi(it)
        }
    }

    override fun getSeries(characterId: Int): Single<List<BaseDetailEntity>> {
        return makeBaseEntityApiRequest{
            marvelApi.getSeries(characterId)
        }.map {
            comicListMapper.fromEntityApi(it)
        }
    }
}