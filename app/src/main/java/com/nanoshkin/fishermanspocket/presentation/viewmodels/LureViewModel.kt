package com.nanoshkin.fishermanspocket.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nanoshkin.fishermanspocket.adapter.OnLureItemClickListener
import com.nanoshkin.fishermanspocket.domain.models.Lure
import com.nanoshkin.fishermanspocket.domain.usecases.GetAllLuresUseCase
import com.nanoshkin.fishermanspocket.domain.usecases.SaveLureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LureViewModel @Inject constructor(
    private val getAllLuresUseCase: GetAllLuresUseCase,
    private val saveLureUseCase: SaveLureUseCase
) : ViewModel(){

    private val _dataLures = MutableSharedFlow<List<Lure>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val dataLures: SharedFlow<List<Lure>> = _dataLures.asSharedFlow()

    init {
        viewModelScope.launch {
            _dataLures.emitAll(getAllLuresUseCase())
        }
    }

    fun save(lure: Lure) {
        viewModelScope.launch {
            saveLureUseCase(lure = lure)
        }
    }

}
