package br.com.alex.repository.store.remote.api

import br.com.alex.repository.store.model.remote.BaseDataWrapperResultEntity
import br.com.alex.repository.store.model.remote.CharacterDetailResultEntity
import br.com.alex.repository.store.model.remote.CharacterResultEntity
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {
    @GET("/v1/public/characters?orderBy=name&limit=20")
    fun getCharacters(@Query("offset")offset: Int? = 0): Single<Response<BaseDataWrapperResultEntity<CharacterResultEntity>>>

    @GET("/v1/public/characters/{characterId}")
    fun getCharacter(@Path("characterId") characterId: Int): Single<Response<BaseDataWrapperResultEntity<CharacterResultEntity>>>

    @GET("/v1/public/characters/{characterId}/comics")
    fun getComics(@Path("characterId") characterId: Int): Single<Response<BaseDataWrapperResultEntity<CharacterDetailResultEntity>>>

    @GET("/v1/public/characters/{characterId}/series")
    fun getSeries(@Path("characterId") characterId: Int): Single<Response<BaseDataWrapperResultEntity<CharacterDetailResultEntity>>>
}