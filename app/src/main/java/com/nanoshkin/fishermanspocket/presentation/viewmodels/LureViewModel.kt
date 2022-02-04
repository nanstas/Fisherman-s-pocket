package com.nanoshkin.fishermanspocket.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nanoshkin.fishermanspocket.domain.models.Lure
import com.nanoshkin.fishermanspocket.domain.models.LureType
import com.nanoshkin.fishermanspocket.domain.usecases.GetAllLuresUseCase
import com.nanoshkin.fishermanspocket.domain.usecases.SaveLureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LureViewModel @Inject constructor(
    private val getAllLuresUseCase: GetAllLuresUseCase,
    private val saveLureUseCase: SaveLureUseCase
) : ViewModel() {

    private val testLure = Lure(
        id = null,
        name = "MAG SQUAD",
        manufacturer = "JACKALL",
        type = LureType.MINNOW,
        divingDepth = "1.5",
        floatation = "SP",
        weight = 21,
        length = 128,
        description = "Габаритный суспендер с точеной геометрией тела. При твитчинговой проводке очень чувствителен к любым движениям спиннинга. При резких рывках создает очень сильные вибрации в воде, распространяемые на дальние дистанции и улавливаемые рыбой. При паузах во время проводки выполняет угасающие покачивающие движения из стороны в сторону провоцируя хищника к атаке.\"",
        color = "HL Silver & Black",
        imageUrl = null,
        effectiveness = 0,
        notes = "",
    )

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
