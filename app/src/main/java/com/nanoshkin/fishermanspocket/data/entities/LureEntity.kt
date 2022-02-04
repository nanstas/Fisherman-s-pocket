package com.nanoshkin.fishermanspocket.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nanoshkin.fishermanspocket.domain.models.Lure

@Entity(tableName = "LureEntity")
data class LureEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int?,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "manufacturer")
    val manufacturer: String?,
    @ColumnInfo(name = "type")
    val type: String?,
    @ColumnInfo(name = "divingDepth")
    val divingDepth: String?,
    @ColumnInfo(name = "floatation")
    val floatation: String?,
    @ColumnInfo(name = "weight")
    val weight: Int?,
    @ColumnInfo(name = "length")
    val length: Int?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "color")
    val color: String?,
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String?,
    @ColumnInfo(name = "effectiveness")
    val effectiveness: Int?,
    @ColumnInfo(name = "notes")
    val notes: String?,
)

fun Lure.toEntity() = LureEntity(
    id = id,
    name = name,
    manufacturer = manufacturer,
    type = type?.name,
    divingDepth = divingDepth,
    floatation = floatation,
    weight = weight,
    length = length,
    description = description,
    color = color,
    imageUrl = imageUrl,
    effectiveness = effectiveness,
    notes = notes
)

fun List<Lure>.toEntity(): List<LureEntity> = map(Lure::toEntity)

