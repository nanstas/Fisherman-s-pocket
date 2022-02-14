package com.nanoshkin.fishermanspocket.domain.usecases

import com.nanoshkin.fishermanspocket.domain.repository.LureRepository

class SaveNoteUseCase(
    private val lureRepository: LureRepository
) {
    suspend operator fun invoke(idLure: Int, note: String) {
        return lureRepository.saveNote(idLure = idLure, note = note)
    }
}