package com.nanoshkin.fishermanspocket.data.repository

import com.nanoshkin.fishermanspocket.data.db.LureDao
import com.nanoshkin.fishermanspocket.data.entities.toEntity
import com.nanoshkin.fishermanspocket.domain.models.Lure
import com.nanoshkin.fishermanspocket.domain.repository.LureRepository
import kotlinx.coroutines.flow.Flow

class LureRepositoryImpl(
    private val dao: LureDao
) : LureRepository {

    override suspend fun getAllLures(): Flow<List<Lure>> {
        return dao.getAllLures()
    }

    override fun getLureById(idLure: Int): Flow<Lure> {
        return dao.getLureByIdFlow(idLure)
    }

    override suspend fun saveLure(lure: Lure) {
        dao.insertLure(lure.toEntity())
    }

    override suspend fun increaseInCaughtFish(lure: Lure) {
        val lureForUpdate = lure.copy(effectiveness = lure.effectiveness + 1)
        dao.insertLure(lureForUpdate.toEntity())
    }

    override suspend fun removeLureById(idLure: Int) {
        dao.removeLureById(idLure)
    }

    override suspend fun saveNote(idLure: Int, note: String) {
        val currentLureUpdate = dao.getLureById(idLure)
        val lureForUpdate = currentLureUpdate.copy(notes = note)
        dao.insertLure(lureForUpdate.toEntity())
    }
}