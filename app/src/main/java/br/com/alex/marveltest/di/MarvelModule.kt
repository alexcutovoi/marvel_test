package br.com.alex.marveltest.di

import br.com.alex.domain.SingleUseCase
import br.com.alex.domain.model.*
import br.com.alex.domain.repository.MarvelRepository
import br.com.alex.domain.usecase.*
import br.com.alex.repository.MarvelRepositoryImpl
import br.com.alex.repository.mapper.*
import br.com.alex.repository.store.MarvelStore
import br.com.alex.repository.store.local.DataBase
import br.com.alex.repository.store.local.MarvelLocalStore
import br.com.alex.repository.store.local.dao.MarvelDatabaseDao
import br.com.alex.repository.store.model.base.BaseDetailEntity
import br.com.alex.repository.store.model.local.CharacterEntity
import br.com.alex.repository.store.model.remote.BaseDataContainerResultEntity
import br.com.alex.repository.store.model.remote.BaseDataWrapperResultEntity
import br.com.alex.repository.store.model.remote.CharacterDetailResultEntity
import br.com.alex.repository.store.model.remote.CharacterResultEntity
import br.com.alex.repository.store.remote.MarvelRemoteStore
import br.com.alex.repository.store.remote.Remote
import br.com.alex.repository.store.remote.api.MarvelApi
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MarvelModule {
    companion object{
        const val MARVEL_LOCAL_STORE = "MARVEL_LOCAL_STORE"
        const val MARVEL_REMOTE_STORE = "MARVEL_REMOTE_STORE"
        const val MARVEL_CHARACTER_DATA_WRAPPER_MAPPER = "MARVEL_CHARACTER_DATA_WRAPPER_MAPPER"
        const val MARVEL_CHARACTER_DATA_CONTAINER_MAPPER = "MARVEL_CHARACTER_DATA_CONTAINER_MAPPER"
        const val MARVEL_CHARACTER_RESULT_MAPPER = "MARVEL_CHARACTER_RESULT_MAPPER"
        const val MARVEL_IMAGE_MAPPER = "MARVEL_IMAGE_MAPPER"
        const val MARVEL_CHARACTER_LIST_MAPPER = "MARVEL_CHARACTER_LIST_MAPPER"
        const val MARVEL_CHARACTER_MAPPER = "MARVEL_CHARACTER_MAPPER"

        const val MARVEL_COMIC_DATA_WRAPPER_MAPPER = "MARVEL_COMIC_DATA_WRAPPER_MAPPER"
        const val MARVEL_COMIC_DATA_CONTAINER_MAPPER = "MARVEL_COMIC_DATA_CONTAINER_MAPPER"
        const val MARVEL_COMIC_MAPPER = "MARVEL_COMIC_MAPPER"
        const val MARVEL_COMIC_LIST_MAPPER = "MARVEL_COMIC_LIST_MAPPER"
        const val MARVEL_COMIC_ENTITY_MAPPER = "MARVEL_COMIC_ENTITY_MAPPER"
        const val MARVEL_CHARACTER_DETAIL_LIST_ENTITY_MAPPER = "MARVEL_CHARACTER_DETAIL_LIST_ENTITY_MAPPER"

        const val MARVEL_CHARACTER_ENTITY_MAPPER = "MARVEL_CHARACTER_ENTITY_MAPPER"
        const val MARVEL_CHARACTER_LIST_ENTITY_MAPPER = "MARVEL_CHARACTER_LIST_ENTITY_MAPPER"

        const val INTERACTOR_MARVEL_SELECT_CHARACTERS_MAPPER = "INTERACTOR_MARVEL_SELECT_CHARACTERS_MAPPER"
        const val INTERACTOR_MARVEL_INSERT_CHARACTER_MAPPER = "INTERACTOR_MARVEL_INSERT_CHARACTER_MAPPER"
        const val INTERACTOR_MARVEL_DELETE_CHARACTER_MAPPER = "INTERACTOR_MARVEL_DELETE_CHARACTER_MAPPER"
        const val INTERACTOR_MARVEL_CHARACTER_MAPPER = "INTERACTOR_MARVEL_CHARACTER_MAPPER"
        const val INTERACTOR_MARVEL_COMICS_MAPPER = "INTERACTOR_MARVEL_COMICS_MAPPER"
        const val INTERACTOR_MARVEL_SERIES_MAPPER = "INTERACTOR_MARVEL_SERIES_MAPPER"
        const val INTERACTOR_MARVEL_COMICS_AND_SERIES = "INTERACTOR_MARVEL_COMICS_AND_SERIES"


        val module = Kodein.Module(name = "MarvelModule"){
            //Stores
            bind<MarvelStore>(MARVEL_LOCAL_STORE) with
                provider { MarvelLocalStore(instance()) }
            bind<MarvelStore>(MARVEL_REMOTE_STORE) with
                provider { MarvelRemoteStore(instance(MARVEL_CHARACTER_LIST_MAPPER), instance(MARVEL_CHARACTER_MAPPER), instance(MARVEL_COMIC_LIST_MAPPER), instance(), instance(), instance()) }

            //Mappers
            bind<ImageEntityMapper>(MARVEL_IMAGE_MAPPER) with
                provider {ImageEntityMapper()}

            //Characters Mappers
            bind<ResultMapper<BaseDataWrapperResultEntity<CharacterResultEntity>, List<CharacterEntity>>>(MARVEL_CHARACTER_LIST_MAPPER) with
                provider { CharacterListMapper(instance(MARVEL_CHARACTER_MAPPER)) }
            bind<ResultMapper<CharacterResultEntity, CharacterEntity>>(MARVEL_CHARACTER_MAPPER) with
                provider { CharacterMapper() }

            bind<EntityMapper<CharacterResultEntity, CharacterResultModel>>(MARVEL_CHARACTER_RESULT_MAPPER) with
                    provider { CharacterResultEntityMapper(instance(MARVEL_IMAGE_MAPPER)) }
            bind<EntityMapper<BaseDataContainerResultEntity<CharacterResultEntity>, BaseDataContainerResultModel<CharacterResultModel>>>(MARVEL_CHARACTER_DATA_CONTAINER_MAPPER) with
                    provider { CharacterDataContainerEntityMapper(instance(MARVEL_CHARACTER_RESULT_MAPPER)) }
            bind<EntityMapper<BaseDataWrapperResultEntity<CharacterResultEntity>, BaseDataWrapperResultModel<BaseDataContainerResultModel<CharacterResultModel>>>>(MARVEL_CHARACTER_DATA_WRAPPER_MAPPER) with
                    provider { CharacterDataWrapperEntityMapper(instance(MARVEL_CHARACTER_DATA_CONTAINER_MAPPER)) }
            bind<EntityMapper<CharacterEntity, CharacterModel>>(MARVEL_CHARACTER_ENTITY_MAPPER) with provider {
                CharacterDbEntityMapper()
            }
            bind<EntityMapper<List<CharacterEntity>, List<CharacterModel>>>(MARVEL_CHARACTER_LIST_ENTITY_MAPPER) with
                    provider { CharacterListEntityMapper(instance(MARVEL_CHARACTER_ENTITY_MAPPER)) }

            //Comics Mappers
            bind<ResultMapper<BaseDataWrapperResultEntity<CharacterDetailResultEntity>, List<BaseDetailEntity>>>(MARVEL_COMIC_LIST_MAPPER) with
                    provider { CharacterDetailListMapper(instance(MARVEL_COMIC_MAPPER)) }
            bind<ResultMapper<CharacterDetailResultEntity, BaseDetailEntity>>(MARVEL_COMIC_MAPPER) with
                    provider { CharacterDetailMapper() }

            bind<EntityMapper<BaseDetailEntity, CharacterDetailResultModel>>(MARVEL_COMIC_ENTITY_MAPPER) with
                    provider { CharacterDetailEntityMapper() }
            bind<EntityMapper<List<BaseDetailEntity>, List<CharacterDetailResultModel>>>(MARVEL_CHARACTER_DETAIL_LIST_ENTITY_MAPPER) with
                    provider { CharacterDetailListEntityMapper(instance(MARVEL_COMIC_ENTITY_MAPPER)) }
            bind<EntityMapper<BaseDataContainerResultEntity<CharacterDetailResultEntity>, BaseDataContainerResultModel<CharacterDetailResultModel>>>(MARVEL_COMIC_DATA_CONTAINER_MAPPER) with
                    provider { CharacterDetailDataContainerEntityMapper(instance(MARVEL_COMIC_ENTITY_MAPPER)) }
            bind<EntityMapper<BaseDataWrapperResultEntity<CharacterDetailResultEntity>, BaseDataWrapperResultModel<BaseDataContainerResultModel<CharacterDetailResultModel>>>>(MARVEL_COMIC_DATA_WRAPPER_MAPPER) with
                    provider { CharacterDetailDataWrapperEntityMapper(instance(MARVEL_COMIC_DATA_CONTAINER_MAPPER)) }


            //Interactors
            bind<SingleUseCase<List<CharacterModel>, SelectCharacters.Param>>(INTERACTOR_MARVEL_SELECT_CHARACTERS_MAPPER) with
                singleton { SelectCharacters(instance(), instance()) }
            bind<SingleUseCase<CharacterModel, SelectCharacter.Param>>(INTERACTOR_MARVEL_CHARACTER_MAPPER) with
                singleton {SelectCharacter(instance(), instance()) }
            bind<SingleUseCase<Long, InsertCharacter.Param>>(INTERACTOR_MARVEL_INSERT_CHARACTER_MAPPER) with
                singleton { InsertCharacter(instance(), instance()) }
            bind<SingleUseCase<Int, DeleteCharacter.Param>>(INTERACTOR_MARVEL_DELETE_CHARACTER_MAPPER) with
                singleton { DeleteCharacter(instance(), instance()) }
            bind<SingleUseCase<List<CharacterDetailResultModel>, SelectComics.Param>>(INTERACTOR_MARVEL_COMICS_MAPPER) with
                singleton { SelectComics(instance(), instance())}
            bind<SingleUseCase<List<CharacterDetailResultModel>, SelectSeries.Param>>(INTERACTOR_MARVEL_SERIES_MAPPER) with
                singleton { SelectSeries(instance(), instance())}
            bind<SingleUseCase<Pair<List<CharacterDetailResultModel>, List<CharacterDetailResultModel>>, LoadComicsAndSeries.Param>>(INTERACTOR_MARVEL_COMICS_AND_SERIES) with
                singleton { LoadComicsAndSeries(instance(), instance()) }

            //Api
            bind<MarvelApi>() with
                singleton { Remote.getApi<MarvelApi>(instance(AppModule.BASE_URL)) }

            bind<MarvelRepository>() with
                provider { MarvelRepositoryImpl(
                    instance(MARVEL_LOCAL_STORE),
                    instance(MARVEL_REMOTE_STORE),
                    instance(MARVEL_CHARACTER_ENTITY_MAPPER),
                    instance(MARVEL_CHARACTER_LIST_ENTITY_MAPPER),
                    instance(MARVEL_CHARACTER_DETAIL_LIST_ENTITY_MAPPER))}

            //Database
            bind<MarvelDatabaseDao>() with singleton {
                DataBase.instance(instance()).marvelDatabaseDao()
            }
        }
    }
}