package br.com.alex.marveltest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.alex.domain.SingleUseCase
import br.com.alex.domain.model.CharacterModel
import br.com.alex.domain.usecase.DeleteCharacter
import br.com.alex.domain.usecase.InsertCharacter
import br.com.alex.domain.usecase.SelectCharacters
import br.com.alex.marveltest.MarvelApplication
import br.com.alex.marveltest.di.MarvelModule
import br.com.alex.marveltest.model.ViewData
import io.reactivex.observers.DisposableSingleObserver
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class MarvelMainViewModel : ViewModel(){
    private val kodein by closestKodein(MarvelApplication.appContext)

    private val _interactorSelectCharacters: SingleUseCase<List<CharacterModel>, SelectCharacters.Param>
            by kodein.instance(MarvelModule.INTERACTOR_MARVEL_SELECT_CHARACTERS_MAPPER)

    private val _interactorInsertCharacter: SingleUseCase<Long, InsertCharacter.Param>
            by kodein.instance(MarvelModule.INTERACTOR_MARVEL_INSERT_CHARACTER_MAPPER)

    private val _interactorDeleteCharacter: SingleUseCase<Int, DeleteCharacter.Param>
            by kodein.instance(MarvelModule.INTERACTOR_MARVEL_DELETE_CHARACTER_MAPPER)

    private val _marvelBookmarkedCharactersResult: MutableLiveData<ViewData<List<CharacterModel>>> = MutableLiveData<ViewData<List<CharacterModel>>>().apply {
        value = ViewData.undefined()
    }

    val marvelBookmarkedCharactersResult: LiveData<ViewData<List<CharacterModel>>> = _marvelBookmarkedCharactersResult

    private val _insertCharacterResult: MutableLiveData<ViewData<Long>> = MutableLiveData<ViewData<Long>>().apply {
        value = ViewData.undefined()
    }

    val insertCharactersResult: LiveData<ViewData<Long>> = _insertCharacterResult

    private val _deleteCharacterResult: MutableLiveData<ViewData<Int>> = MutableLiveData<ViewData<Int>>().apply {
        value = ViewData.undefined()
    }

    val deleteCharactersResult: LiveData<ViewData<Int>> = _deleteCharacterResult

    fun getBookmarkedCharacters(offset:Int? = 0, repositoryType: Int){
        _marvelBookmarkedCharactersResult.postValue(ViewData.loading())
        _interactorSelectCharacters.execute(object: DisposableSingleObserver<List<CharacterModel>>(){
            override fun onSuccess(t: List<CharacterModel>) {
                _marvelBookmarkedCharactersResult.postValue(ViewData.success(t))
            }

            override fun onError(e: Throwable) {
                _marvelBookmarkedCharactersResult.postValue(ViewData.fail(e))
            }
        }, SelectCharacters.Param(offset, repositoryType))
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