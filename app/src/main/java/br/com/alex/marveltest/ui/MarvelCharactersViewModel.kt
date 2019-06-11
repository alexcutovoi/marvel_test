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

class MarvelCharactersViewModel : ViewModel(){
    private val kodein by closestKodein(MarvelApplication.appContext)

    private val _interactorSelectCharacters: SingleUseCase<List<CharacterModel>, SelectCharacters.Param>
    by kodein.instance(MarvelModule.INTERACTOR_MARVEL_SELECT_CHARACTERS_MAPPER)

    private val _marvelCharactersResult: MutableLiveData<ViewData<List<CharacterModel>>> = MutableLiveData<ViewData<List<CharacterModel>>>().apply {
        value = ViewData.undefined()
    }

    val marvelCharactersResult: LiveData<ViewData<List<CharacterModel>>> = _marvelCharactersResult

    fun getCharacters(offset:Int? = 0, repositoryType: Int){
        _marvelCharactersResult.postValue(ViewData.loading())
        _interactorSelectCharacters.execute(object: DisposableSingleObserver<List<CharacterModel>>(){
            override fun onSuccess(t: List<CharacterModel>) {
                _marvelCharactersResult.postValue(ViewData.success(t))
            }

            override fun onError(e: Throwable) {
                _marvelCharactersResult.postValue(ViewData.fail(e))
            }
        }, SelectCharacters.Param(offset, repositoryType))
    }
}