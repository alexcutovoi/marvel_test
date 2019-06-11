package br.com.alex.domain.repository

import br.com.alex.domain.model.CharacterModel
import br.com.alex.domain.model.CharacterDetailResultModel
import io.reactivex.Single

interface MarvelRepository {
    fun getCharacters(offset:Int, repositoryType: Int): Single<List<CharacterModel>>
    fun getCharacter(characterId: Int): Single<CharacterModel>
    fun insertCharacter(character: CharacterModel): Single<Long>
    fun deleteCharacter(character: CharacterModel): Single<Int>
    fun getComics(characterId: Int, repositoryType: Int): Single<List<CharacterDetailResultModel>>
    fun getSeries(characterId: Int, repositoryType: Int): Single<List<CharacterDetailResultModel>>
}