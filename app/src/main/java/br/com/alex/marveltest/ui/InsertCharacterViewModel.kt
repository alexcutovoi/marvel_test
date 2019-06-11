package br.com.alex.marveltest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.alex.domain.SingleUseCase
import br.com.alex.domain.model.CharacterModel
import br.com.alex.domain.usecase.InsertCharacter
import br.com.alex.marveltest.MarvelApplication
import br.com.alex.marveltest.di.MarvelModule
import br.com.alex.marveltest.model.ViewData
import io.reactivex.observers.DisposableSingleObserver
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class InsertCharacterViewModel : ViewModel(){
    private val kodein by closestKodein(MarvelApplication.appContext)

    private val _interactorInsertCharacter: SingleUseCase<Long, InsertCharacter.Param>
            by kodein.instance(MarvelModule.INTERACTOR_MARVEL_INSERT_CHARACTER_MAPPER)

    private val _insertCharacterResult: MutableLiveData<ViewData<Long>> = MutableLiveData<ViewData<Long>>().apply {
        value = ViewData.undefined()
    }

    val insertCharactersResult: LiveData<ViewData<Long>> = _insertCharacterResult

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
}