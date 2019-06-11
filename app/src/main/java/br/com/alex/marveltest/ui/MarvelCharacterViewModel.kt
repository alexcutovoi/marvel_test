package br.com.alex.marveltest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.alex.domain.SingleUseCase
import br.com.alex.domain.model.CharacterDetailResultModel
import br.com.alex.domain.model.CharacterModel
import br.com.alex.domain.usecase.DeleteCharacter
import br.com.alex.domain.usecase.InsertCharacter
import br.com.alex.domain.usecase.LoadComicsAndSeries
import br.com.alex.domain.usecase.SelectCharacter
import br.com.alex.marveltest.MarvelApplication
import br.com.alex.marveltest.di.MarvelModule
import br.com.alex.marveltest.model.ViewData
import io.reactivex.observers.DisposableSingleObserver
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class MarvelCharacterViewModel : ViewModel(){
    private val kodein by closestKodein(MarvelApplication.appContext)

    private val _interactorSelectCharacter: SingleUseCase<CharacterModel, SelectCharacter.Param>
            by kodein.instance(MarvelModule.INTERACTOR_MARVEL_CHARACTER_MAPPER)

    private val _interactorComicsAndSeries: SingleUseCase<Pair<List<CharacterDetailResultModel>, List<CharacterDetailResultModel>>, LoadComicsAndSeries.Param>
        by kodein.instance(MarvelModule.INTERACTOR_MARVEL_COMICS_AND_SERIES)

    private val _interactorInsertCharacter: SingleUseCase<Long, InsertCharacter.Param>
            by kodein.instance(MarvelModule.INTERACTOR_MARVEL_INSERT_CHARACTER_MAPPER)

    private val _interactorDeleteCharacter: SingleUseCase<Int, DeleteCharacter.Param>
            by kodein.instance(MarvelModule.INTERACTOR_MARVEL_DELETE_CHARACTER_MAPPER)

    private val _deleteCharacterResult: MutableLiveData<ViewData<Int>> = MutableLiveData<ViewData<Int>>().apply {
        value = ViewData.undefined()
    }

    val deleteCharactersResult: LiveData<ViewData<Int>> = _deleteCharacterResult

    private val _insertCharacterResult: MutableLiveData<ViewData<Long>> = MutableLiveData<ViewData<Long>>().apply {
        value = ViewData.undefined()
    }

    val insertCharacterResult: LiveData<ViewData<Long>> = _insertCharacterResult

    private val _marvelCharacterResult: MutableLiveData<ViewData<CharacterModel>> = MutableLiveData<ViewData<CharacterModel>>().apply {
        value = ViewData.undefined()
    }

    val marvelCharactersResult: LiveData<ViewData<CharacterModel>> = _marvelCharacterResult

    private val _marvelComicsResult: MutableLiveData<ViewData<List<CharacterDetailResultModel>>> = MutableLiveData<ViewData<List<CharacterDetailResultModel>>>().apply {
        value = ViewData.undefined()
    }

    val marvelComicsResult: LiveData<ViewData<List<CharacterDetailResultModel>>> = _marvelComicsResult

    private val _marvelSeriesResult: MutableLiveData<ViewData<List<CharacterDetailResultModel>>> = MutableLiveData<ViewData<List<CharacterDetailResultModel>>>().apply {
        value = ViewData.undefined()
    }

    val marvelSeriesResult: LiveData<ViewData<List<CharacterDetailResultModel>>> = _marvelSeriesResult

    private val _marvelComicsAndSeriesResult: MutableLiveData<ViewData<Pair<List<CharacterDetailResultModel>, List<CharacterDetailResultModel>>>> =
        MutableLiveData<ViewData<Pair<List<CharacterDetailResultModel>, List<CharacterDetailResultModel>>>>().apply {
        value = ViewData.undefined()
    }

    val marvelComicsAndSeriesResult: LiveData<ViewData<Pair<List<CharacterDetailResultModel>, List<CharacterDetailResultModel>>>> = _marvelComicsAndSeriesResult

    fun getCharacter(characterId: Int){
        _marvelCharacterResult.postValue(ViewData.loading())
        _interactorSelectCharacter.execute(object: DisposableSingleObserver<CharacterModel>(){
            override fun onSuccess(t: CharacterModel) {
                _marvelCharacterResult.postValue(ViewData.success(t))
            }

            override fun onError(e: Throwable) {
                _marvelCharacterResult.postValue(ViewData.fail(e))
            }
        }, SelectCharacter.Param(characterId))
    }

    fun getComicsAndSeries(characterId: Int, repositoryType: Int){
        _marvelComicsAndSeriesResult.postValue(ViewData.loading())
        _interactorComicsAndSeries.execute(object: DisposableSingleObserver<Pair<List<CharacterDetailResultModel>, List<CharacterDetailResultModel>>>(){
            override fun onSuccess(t: Pair<List<CharacterDetailResultModel>, List<CharacterDetailResultModel>>) {
                _marvelComicsAndSeriesResult.postValue(ViewData.success(Pair(t.first, t.second)))
            }

            override fun onError(e: Throwable) {
                _marvelComicsAndSeriesResult.postValue(ViewData.fail(e))
            }

        }, LoadComicsAndSeries.Param(characterId, repositoryType))
    }

    fun insertCharacter(character: CharacterModel){
        character.characterBookmarked = true
        _insertCharacterResult.postValue(ViewData.loading())
        _interactorInsertCharacter.execute(object: DisposableSingleObserver<Long>(){
            override fun onSuccess(t: Long) {
                _insertCharacterResult.postValue(ViewData.success(t))
            }

            override fun onError(e: Throwable) {
                _insertCharacterResult.postValue(ViewData.fail(e))
            }
        }, InsertCharacter.Param(character))
    }

    fun deleteCharacter(character: CharacterModel){
        character.characterBookmarked = false
        _deleteCharacterResult.postValue(ViewData.loading())
        _interactorDeleteCharacter.execute(object: DisposableSingleObserver<Int>(){
            override fun onSuccess(t: Int) {
                _deleteCharacterResult.postValue(ViewData.success(t))
            }

            override fun onError(e: Throwable) {
                _deleteCharacterResult.postValue(ViewData.fail(e))
            }
        }, DeleteCharacter.Param(character))
    }
}