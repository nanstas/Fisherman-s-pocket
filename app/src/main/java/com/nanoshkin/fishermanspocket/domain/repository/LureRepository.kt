package com.nanoshkin.fishermanspocket.domain.repository

import com.nanoshkin.fishermanspocket.domain.models.Lure
import kotlinx.coroutines.flow.Flow


interface LureRepository {
    suspend fun getAllLures(): Flow<List<Lure>>
    suspend fun saveLure(lure: Lure)
}