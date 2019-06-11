package br.com.alex.marveltest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.alex.domain.SingleUseCase
import br.com.alex.domain.model.CharacterDetailResultModel
import br.com.alex.domain.usecase.SelectComics
import br.com.alex.marveltest.MarvelApplication
import br.com.alex.marveltest.di.MarvelModule
import br.com.alex.marveltest.model.ViewData
import io.reactivex.observers.DisposableSingleObserver
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class MarvelComicsViewModel : ViewModel(){
    private val kodein by closestKodein(MarvelApplication.appContext)

//    private val _interactorSelectComics: SingleUseCase<List<CharacterDetailResultModel>, SelectComics.Param>
//            by kodein.instance(MarvelModule.INTERACTOR_MARVEL_COMICS_MAPPER)

    private val _marvelComicsResult: MutableLiveData<ViewData<List<CharacterDetailResultModel>>> = MutableLiveData<ViewData<List<CharacterDetailResultModel>>>().apply {
        value = ViewData.undefined()
    }

    val marvelComicsResult: LiveData<ViewData<List<CharacterDetailResultModel>>> = _marvelComicsResult

    fun getComics(offset:Int? = 0, repositoryType: Int){
//        _marvelComicsResult.postValue(ViewData.loading())
//        _interactorSelectComics.execute(object: DisposableSingleObserver<List<CharacterDetailResultModel>>(){
//            override fun onSuccess(t: List<CharacterDetailResultModel>) {
//                _marvelComicsResult.postValue(ViewData.success(t))
//            }
//
//            override fun onError(e: Throwable) {
//                _marvelComicsResult.postValue(ViewData.fail(e))
//            }
//        }, SelectComics.Param(offset, repositoryType))
    }
}