package com.nanoshkin.fishermanspocket.domain.usecases

import com.nanoshkin.fishermanspocket.domain.models.Lure
import com.nanoshkin.fishermanspocket.domain.repository.LureRepository

class SaveLureUseCase(private val lureRepository: LureRepository) {
    fun execute(lure: Lure) {
        return lureRepository.saveLure(lure = lure)
    }
}