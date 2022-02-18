package com.nanoshkin.fishermanspocket.utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.nanoshkin.fishermanspocket.domain.models.lure.LureDivingDepth
import com.nanoshkin.fishermanspocket.domain.models.lure.LureFloatation
import com.nanoshkin.fishermanspocket.domain.models.lure.LureType
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

object Utils {
    fun convertLureTypeCategory(lureType: String): LureType {
        return when (lureType) {
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

    fun convertLureDivingDepthCategory(divingDepth: String): LureDivingDepth {
        return when (divingDepth) {
            "SSR" -> LureDivingDepth.SSR
            "SR" -> LureDivingDepth.SR
            "MDR" -> LureDivingDepth.MDR
            "DD" -> LureDivingDepth.DD
            "EDD" -> LureDivingDepth.EDD
            else -> LureDivingDepth.UNKNOWN
        }
    }

    fun convertLureFloatationCategory(floatation: String): LureFloatation {
        return when (floatation) {
            "FS" -> LureFloatation.FS
            "S" -> LureFloatation.S
            "SS" -> LureFloatation.SS
            "SSS" -> LureFloatation.SSS
            "SP" -> LureFloatation.SP
            "SF" -> LureFloatation.SP
            "F" -> LureFloatation.F
            "FF" -> LureFloatation.FF
            else -> LureFloatation.UNKNOWN
        }
    }
    fun formatDate(date: Int): String =
        SimpleDateFormat.getDateInstance().format(Date(date * 1000L))

    fun formatTime(date: Int): String =
        SimpleDateFormat.getTimeInstance().format(Date(date * 1000L))
}
