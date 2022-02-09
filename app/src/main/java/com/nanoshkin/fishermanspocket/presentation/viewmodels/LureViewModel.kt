package com.nanoshkin.fishermanspocket.presentation.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nanoshkin.fishermanspocket.domain.models.Lure
import com.nanoshkin.fishermanspocket.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class LureViewModel @Inject constructor(
    private val getAllLuresUseCase: GetAllLuresUseCase,
    private val saveLureUseCase: SaveLureUseCase,
    private val increaseInCaughtFishUseCase: IncreaseInCaughtFishUseCase,
    private val getLuresByIdUseCase: GetLuresByIdUseCase,
    private val removeLureByIdUseCase: RemoveLureByIdUseCase
) : ViewModel() {

    private var lureId by Delegates.notNull<Int>()

    val currentLure: Flow<Lure> by lazy {
        getLuresByIdUseCase(lureId)
    }

    private val _dataLures =
        MutableSharedFlow<List<Lure>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val dataLures: SharedFlow<List<Lure>> = _dataLures.asSharedFlow()

    private val _currentLureImage = MutableLiveData<Uri?>()
    val currentLureImage: LiveData<Uri?> = _currentLureImage

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

    fun removeLure(id: Int) {
        viewModelScope.launch {
            removeLureByIdUseCase(id)
        }
    }

    fun changeCurrentLureImage(uri: Uri?) {
        _currentLureImage.value = uri
    }

    fun removeCurrentLureImage() {
        _currentLureImage.value = null
    }

    fun increaseInCaughtFish(lure: Lure) {
        viewModelScope.launch(Dispatchers.Default) {
            increaseInCaughtFishUseCase(lure)
        }
    }

    fun init(lureId: Int) {
        this.lureId = lureId
    }
}
