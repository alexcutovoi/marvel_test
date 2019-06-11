package br.com.alex.marveltest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.alex.domain.SingleUseCase
import br.com.alex.domain.model.CharacterModel
import br.com.alex.domain.usecase.DeleteCharacter
import br.com.alex.marveltest.MarvelApplication
import br.com.alex.marveltest.di.MarvelModule
import br.com.alex.marveltest.model.ViewData
import io.reactivex.observers.DisposableSingleObserver
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance


class DeleteCharacterViewModel : ViewModel(){
    private val kodein by closestKodein(MarvelApplication.appContext)

    private val _interactorDeleteCharacter: SingleUseCase<Int, DeleteCharacter.Param>
            by kodein.instance(MarvelModule.INTERACTOR_MARVEL_DELETE_CHARACTER_MAPPER)

    private val _deleteCharacterResult: MutableLiveData<ViewData<Int>> = MutableLiveData<ViewData<Int>>().apply {
        value = ViewData.undefined()
    }

    val deleteCharactersResult: LiveData<ViewData<Int>> = _deleteCharacterResult

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