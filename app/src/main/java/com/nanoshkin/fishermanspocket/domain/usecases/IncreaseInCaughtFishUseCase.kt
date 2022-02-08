package com.nanoshkin.fishermanspocket.domain.usecases

import com.nanoshkin.fishermanspocket.domain.models.Lure
import com.nanoshkin.fishermanspocket.domain.repository.LureRepository

class IncreaseInCaughtFishUseCase(
    private val lureRepository: LureRepository
) {
    suspend operator fun invoke(lure: Lure) {
        return lureRepository.increaseInCaughtFish(lure = lure)
    }
}