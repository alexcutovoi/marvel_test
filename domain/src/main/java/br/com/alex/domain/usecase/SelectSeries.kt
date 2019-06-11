package br.com.alex.domain.usecase

import br.com.alex.domain.SchedulerBuilder
import br.com.alex.domain.SingleUseCase
import br.com.alex.domain.model.CharacterDetailResultModel
import br.com.alex.domain.repository.MarvelRepository
import io.reactivex.Single

class SelectSeries(
    private val marvelRepository: MarvelRepository,
    schedulerBuilder: SchedulerBuilder
): SingleUseCase<List<CharacterDetailResultModel>, SelectSeries.Param>(schedulerBuilder) {
    class Param(private val characterId: Int? = 0, private val repositoryType: Int){
        val id: Int = characterId!!
        val selectedRepositoryType: Int = repositoryType
    }

    override fun buildUseCaseSingle(params: Param?): Single<List<CharacterDetailResultModel>> {
        return marvelRepository.getSeries(params!!.id, params!!.selectedRepositoryType)
    }
}