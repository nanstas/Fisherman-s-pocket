package com.nanoshkin.fishermanspocket.presentation.ui.create_edit_lure

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nanoshkin.fishermanspocket.domain.models.Lure
import com.nanoshkin.fishermanspocket.domain.usecases.SaveLureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateEditLureViewModel @Inject constructor(
    private val saveLureUseCase: SaveLureUseCase
) : ViewModel() {

    fun save(lure: Lure) {
        viewModelScope.launch {
            saveLureUseCase.execute(lure = lure)
        }
    }
}