package br.com.alex.domain.usecase

import br.com.alex.domain.SchedulerBuilder
import br.com.alex.domain.SingleUseCase
import br.com.alex.domain.model.CharacterModel
import br.com.alex.domain.repository.MarvelRepository
import io.reactivex.Single

class DeleteCharacter(
    private val marvelRepository: MarvelRepository,
    schedulerBuilder: SchedulerBuilder
): SingleUseCase<Int, DeleteCharacter.Param>(schedulerBuilder) {
    class Param(private val character: CharacterModel){
        val characterToInsert: CharacterModel = character
    }

    override fun buildUseCaseSingle(params: Param?): Single<Int> {
        return marvelRepository.deleteCharacter(params!!.characterToInsert)
    }
}