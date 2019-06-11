package br.com.alex.domain.usecase

import br.com.alex.domain.SchedulerBuilder
import br.com.alex.domain.SingleUseCase
import br.com.alex.domain.model.CharacterModel
import br.com.alex.domain.repository.MarvelRepository
import io.reactivex.Single

class SelectCharacters(
    private val marvelRepository: MarvelRepository,
    schedulerBuilder: SchedulerBuilder
): SingleUseCase<List<CharacterModel>, SelectCharacters.Param>(schedulerBuilder) {
    class Param(private val offset: Int? = 0, private val repositoryType: Int){
        val pageOffset: Int = offset!!
        val selectedRepositoryType: Int = repositoryType
    }

    override fun buildUseCaseSingle(params: Param?): Single<List<CharacterModel>> {
        return marvelRepository.getCharacters(params!!.pageOffset, params!!.selectedRepositoryType)
    }
}