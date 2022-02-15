package com.nanoshkin.fishermanspocket.domain.usecases

import com.nanoshkin.fishermanspocket.domain.models.lure.Lure
import com.nanoshkin.fishermanspocket.domain.repository.LureRepository
import kotlinx.coroutines.flow.Flow

class GetLuresByIdUseCase(
    private val lureRepository: LureRepository
) {
    operator fun invoke(idLure: Int): Flow<Lure> {
        return lureRepository.getLureById(idLure = idLure)
    }
}