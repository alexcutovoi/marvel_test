package br.com.alex.repository

import br.com.alex.domain.model.CharacterModel
import br.com.alex.domain.model.CharacterDetailResultModel
import br.com.alex.domain.repository.MarvelRepository
import br.com.alex.repository.mapper.EntityMapper
import br.com.alex.repository.store.MarvelStore
import br.com.alex.repository.store.model.base.BaseDetailEntity
import br.com.alex.repository.store.model.local.CharacterEntity
import io.reactivex.Single

class MarvelRepositoryImpl(
    private val marvelLocalStore: MarvelStore,
    private val marvelRemoteStore: MarvelStore,
    private val characterLocalMapper: EntityMapper<CharacterEntity, CharacterModel>,
    private val characterListLocalMapper: EntityMapper<List<CharacterEntity>, List<CharacterModel>>,
    private val characterDetailListMapper: EntityMapper<List<BaseDetailEntity>, List<CharacterDetailResultModel>>
    ) : MarvelRepository {
    override fun getCharacters(offset:Int, repositoryType: Int): Single<List<CharacterModel>> {
        val marvelStore: MarvelStore = when(repositoryType){
            0 -> marvelRemoteStore
            1 -> marvelLocalStore
            else -> marvelRemoteStore
        }
        return marvelStore.getCharacters(offset).map{
            characterListLocalMapper.fromEntity(it)
        }
    }

    override fun getCharacter(characterId: Int): Single<CharacterModel> {
        return marvelRemoteStore.getCharacter(characterId).map{
            characterLocalMapper.fromEntity(it)
        }
    }

    override fun insertCharacter(character: CharacterModel): Single<Long>{
        return marvelLocalStore.insertCharacter(
            characterLocalMapper.toEntity(character)
        )
    }

    override fun deleteCharacter(character: CharacterModel): Single<Int>{
        return marvelLocalStore.deleteCharacter(
            characterLocalMapper.toEntity(character)
        )
    }

    override fun getComics(characterId: Int, repositoryType: Int): Single<List<CharacterDetailResultModel>> {
        return marvelRemoteStore.getComics(characterId).map{
            characterDetailListMapper.fromEntity(it)
        }
    }

    override fun getSeries(characterId: Int, repositoryType: Int): Single<List<CharacterDetailResultModel>> {
        return marvelRemoteStore.getSeries(characterId).map{
            characterDetailListMapper.fromEntity(it)
        }
    }
}