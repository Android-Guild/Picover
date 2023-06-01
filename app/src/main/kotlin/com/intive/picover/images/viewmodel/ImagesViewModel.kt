package com.intive.picover.images.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intive.picover.images.repository.ImagesRepository
import com.intive.picover.images.viewmodel.ImagesStorageState.Error
import com.intive.picover.images.viewmodel.ImagesStorageState.Loading
import com.intive.picover.images.viewmodel.ImagesStorageState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ImagesViewModel @Inject constructor(private val repository: ImagesRepository) : ViewModel() {

	private val _uiState = mutableStateOf<ImagesStorageState>(Loading)
	val uiState: State<ImagesStorageState> = _uiState

	init {
		viewModelScope.launch {
			loadImages()
		}
	}

	private suspend fun loadImages() {
		runCatching {
			repository.fetchImages()
		}.onSuccess {
			_uiState.value = Success(it.sorted())
		}.onFailure {
			_uiState.value = Error(it)
		}
	}
}
