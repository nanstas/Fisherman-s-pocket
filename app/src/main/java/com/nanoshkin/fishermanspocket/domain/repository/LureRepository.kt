package com.nanoshkin.fishermanspocket.domain.repository

import com.nanoshkin.fishermanspocket.domain.models.Lure

interface LureRepository {
    fun getAllLures(): List<Lure>
    fun saveLure(lure: Lure)
}