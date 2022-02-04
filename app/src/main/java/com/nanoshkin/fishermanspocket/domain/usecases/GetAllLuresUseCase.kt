package com.nanoshkin.fishermanspocket.domain.usecases

import com.nanoshkin.fishermanspocket.domain.models.Lure
import com.nanoshkin.fishermanspocket.domain.repository.LureRepository
import kotlinx.coroutines.flow.Flow

class GetAllLuresUseCase(
    private val lureRepository: LureRepository
) {
    suspend operator fun invoke(): Flow<List<Lure>> {
        return lureRepository.getAllLures()
    }
}