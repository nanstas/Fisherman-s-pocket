package com.nanoshkin.fishermanspocket.utils

import com.nanoshkin.fishermanspocket.domain.models.LureType

object Utils {
    fun convertNewsCategory(category: String): LureType {
        return when (category) {
            "MINNOW" -> LureType.MINNOW
            "SHAD" -> LureType.SHAD
            "FAT" -> LureType.FAT
            "CRANK" -> LureType.CRANK
            "RATTLIN" -> LureType.RATTLIN
            "SWIMBAIT" -> LureType.SWIMBAIT
            "STICKBAIT" -> LureType.STICKBAIT
            "WALKER" -> LureType.WALKER
            "POPPER" -> LureType.POPPER
            "CRAWLER" -> LureType.CRAWLER
            "PROPBAIT" -> LureType.PROPBAIT
            "TOPWATER" -> LureType.TOPWATER
            "JERKBAIT" -> LureType.JERKBAIT
            "SPOON" -> LureType.SPOON
            "SPINNER" -> LureType.SPINNER
            else -> LureType.OTHER
        }
    }
}