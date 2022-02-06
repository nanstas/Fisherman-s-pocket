package com.nanoshkin.fishermanspocket.data.repository

import com.nanoshkin.fishermanspocket.data.db.LureDao
import com.nanoshkin.fishermanspocket.data.entities.LureEntity
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

    override suspend fun saveLure(lure: Lure) {
        dao.insertLure(lure.toEntity())
    }
}