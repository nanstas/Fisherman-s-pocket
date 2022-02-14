package com.nanoshkin.fishermanspocket.domain.repository

import com.nanoshkin.fishermanspocket.domain.models.Lure
import kotlinx.coroutines.flow.Flow

interface LureRepository {
    suspend fun getAllLures(): Flow<List<Lure>>
    suspend fun saveLure(lure: Lure)
    suspend fun increaseInCaughtFish(lure: Lure)
    fun getLureById(idLure: Int): Flow<Lure>
    suspend fun removeLureById(idLure: Int)
    suspend fun saveNote(idLure: Int, note: String)
}