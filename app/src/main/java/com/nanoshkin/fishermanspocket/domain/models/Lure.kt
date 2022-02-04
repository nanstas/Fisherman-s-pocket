package com.nanoshkin.fishermanspocket.domain.models

data class Lure(
    val id: Int? = null,
    val name: String,
    val manufacturer: String? = null,
    val type: LureType? = null,
    val divingDepth: String? = null,
    val floatation: String? = null,
    val weight: Int? = null,
    val length: Int? = null,
    val description: String? = null,
    val color: String? = null,
    val imageUrl: String? = null,
    val effectiveness: Int? = null,
    val notes: String? = null,
)