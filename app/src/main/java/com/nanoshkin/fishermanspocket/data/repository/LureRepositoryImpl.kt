package com.nanoshkin.fishermanspocket.data.repository

import com.nanoshkin.fishermanspocket.data.db.LureDao
import com.nanoshkin.fishermanspocket.data.entities.LureEntity
import com.nanoshkin.fishermanspocket.domain.models.Lure
import com.nanoshkin.fishermanspocket.domain.repository.LureRepository

class LureRepositoryImpl(
    private val dao: LureDao
) : LureRepository {

//    override fun getAllLures(): Flow<List<Lure>> {
//        return dao.getAllLures()
//    }

    override fun getAllLures(): List<Lure> = dao.getAllLures()

    override fun saveLure(lure: Lure) {
        dao.insertLure(mapToLureEntity(lure))
    }

    private fun mapToLureEntity(lure: Lure): LureEntity =
        LureEntity(
            id = lure.id,
            name = lure.name,
            manufacturer = lure.manufacturer,
            type = lure.type?.name,
            divingDepth = lure.divingDepth,
            floatation = lure.floatation,
            weight = lure.weight,
            length = lure.length,
            description = lure.description,
            color = lure.color,
            imageUrl = lure.imageUrl,
            effectiveness = lure.effectiveness,
            notes = lure.notes
        )
}