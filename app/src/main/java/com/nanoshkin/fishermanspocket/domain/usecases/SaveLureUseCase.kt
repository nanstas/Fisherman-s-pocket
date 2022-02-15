package com.nanoshkin.fishermanspocket.domain.usecases

import com.nanoshkin.fishermanspocket.domain.models.lure.Lure
import com.nanoshkin.fishermanspocket.domain.repository.LureRepository

class SaveLureUseCase(
    private val lureRepository: LureRepository
) {
    suspend operator fun invoke(lure: Lure) {
        return lureRepository.saveLure(lure = lure)
    }
}