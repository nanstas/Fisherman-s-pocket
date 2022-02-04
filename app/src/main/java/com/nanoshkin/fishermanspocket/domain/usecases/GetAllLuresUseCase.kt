package com.nanoshkin.fishermanspocket.domain.usecases

import com.nanoshkin.fishermanspocket.domain.models.Lure
import com.nanoshkin.fishermanspocket.domain.repository.LureRepository
import kotlinx.coroutines.flow.Flow

class GetAllLuresUseCase(private val lureRepository: LureRepository) {
    fun execute(): List<Lure> {
        return lureRepository.getAllLures()
    }
}