package com.nanoshkin.fishermanspocket.utils

import com.nanoshkin.fishermanspocket.data.exceptions.*
import com.nanoshkin.fishermanspocket.domain.models.lure.LureDivingDepth
import com.nanoshkin.fishermanspocket.domain.models.lure.LureFloatation
import com.nanoshkin.fishermanspocket.domain.models.lure.LureType
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException

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

    suspend fun <T, R> makeRequest(
        request: suspend () -> Response<T>,
        onSuccess: suspend (body: T) -> R,
        onFailure: (response: Response<T>) -> R = { throw ApiException(it.code(), it.message()) }
    ): R {
        try {
            val response = request()
            if (!response.isSuccessful) return onFailure(response)
            val body =
                response.body() ?: throw ApiException(response.code(), response.message())
            return onSuccess(body)
        } catch (e: ConnectException) {
            throw LostConnectException
        } catch (e: IOException) {
            throw ServerException
        } catch (e: AuthorizationException) {
            throw AuthorizationException
        } catch (e: Exception) {
            throw UnknownException
        }
    }
}
