package com.nanoshkin.fishermanspocket.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Lure(
    val id: Int? = null,
    val name: String,
    val manufacturer: String? = null,
    val type: LureType? = null,
    val divingDepth: LureDivingDepth? = null,
    val floatation: LureFloatation? = null,
    val weight: Int? = null,
    val length: Int? = null,
    val description: String? = null,
    val color: String? = null,
    val imageUrl: String? = null,
    val effectiveness: Int? = null,
    val notes: String? = null,
): Parcelable