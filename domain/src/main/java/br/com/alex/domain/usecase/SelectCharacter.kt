package br.com.alex.domain.usecase

import br.com.alex.domain.SchedulerBuilder
import br.com.alex.domain.SingleUseCase
import br.com.alex.domain.model.CharacterModel
import br.com.alex.domain.repository.MarvelRepository
import io.reactivex.Single

class SelectCharacter(
    private val marvelRepository: MarvelRepository,
    schedulerBuilder: SchedulerBuilder
): SingleUseCase<CharacterModel, SelectCharacter.Param>(schedulerBuilder) {
    class Param(private val characterId: Int){
        val id: Int = characterId
    }

    override fun buildUseCaseSingle(params: Param?): Single<CharacterModel> {
        return marvelRepository.getCharacter(params!!.id)
    }
}