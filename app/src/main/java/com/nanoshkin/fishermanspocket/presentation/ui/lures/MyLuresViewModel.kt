package com.nanoshkin.fishermanspocket.presentation.ui.lures

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nanoshkin.fishermanspocket.domain.models.Lure
import com.nanoshkin.fishermanspocket.domain.models.LureType
import com.nanoshkin.fishermanspocket.domain.usecases.GetAllLuresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyLuresViewModel @Inject constructor(
    private val getAllLuresUseCase: GetAllLuresUseCase
) : ViewModel() {

    private val emptyLure = Lure(
        id = null,
        name = "",
        manufacturer = "",
        type = LureType.OTHER,
        divingDepth = "",
        floatation = "",
        weight = 0,
        length = 0,
        description = "",
        color = "",
        imageUrl = null,
        effectiveness = 0,
        notes = "",
    )

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

    private val _dataLures = MutableLiveData<List<Lure>>()
    val dataLures = _dataLures

    init {
        getAllLures()
    }

    private fun getAllLures() {
        viewModelScope.launch {
            _dataLures.value = getAllLuresUseCase.execute()
        }
    }

//    private val _text = MutableLiveData<String>().apply {
//        value = "This is My lures Fragment"
//    }
//    val text: LiveData<String> = _text
}