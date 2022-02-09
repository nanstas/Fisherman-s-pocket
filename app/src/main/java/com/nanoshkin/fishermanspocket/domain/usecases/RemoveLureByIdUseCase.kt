package com.nanoshkin.fishermanspocket.domain.usecases

import com.nanoshkin.fishermanspocket.domain.repository.LureRepository

class RemoveLureByIdUseCase(
    private val lureRepository: LureRepository
) {
    suspend operator fun invoke(id: Int) {
        lureRepository.removeLureById(idLure = id)
    }
}