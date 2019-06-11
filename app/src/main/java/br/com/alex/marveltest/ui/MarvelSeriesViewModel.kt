package br.com.alex.marveltest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.alex.domain.SingleUseCase
import br.com.alex.domain.model.CharacterDetailResultModel
import br.com.alex.domain.usecase.SelectSeries
import br.com.alex.marveltest.MarvelApplication
import br.com.alex.marveltest.di.MarvelModule
import br.com.alex.marveltest.model.ViewData
import io.reactivex.observers.DisposableSingleObserver
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class MarvelSeriesViewModel : ViewModel(){
    private val kodein by closestKodein(MarvelApplication.appContext)

    //private val _interactorSelectSeries: SingleUseCase<List<CharacterDetailResultModel>, SelectSeries.Param>
            //by kodein.instance(MarvelModule.INTERACTOR_MARVEL_SERIES_MAPPER)

    private val _marvelSeriesResult: MutableLiveData<ViewData<List<CharacterDetailResultModel>>> = MutableLiveData<ViewData<List<CharacterDetailResultModel>>>().apply {
        value = ViewData.undefined()
    }

    val marvelSeriesResult: LiveData<ViewData<List<CharacterDetailResultModel>>> = _marvelSeriesResult

    fun getSeries(offset:Int? = 0, repositoryType: Int){
//        _marvelSeriesResult.postValue(ViewData.loading())
//        _interactorSelectSeries.execute(object: DisposableSingleObserver<List<CharacterDetailResultModel>>(){
//            override fun onSuccess(t: List<CharacterDetailResultModel>) {
//                _marvelSeriesResult.postValue(ViewData.success(t))
//            }
//
//            override fun onError(e: Throwable) {
//                _marvelSeriesResult.postValue(ViewData.fail(e))
//            }
//        }, SelectSeries.Param(offset, repositoryType))
    }
}