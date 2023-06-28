package com.intive.picover.images.viewmodel

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intive.picover.common.viewmodel.state.ViewModelState
import com.intive.picover.common.viewmodel.state.ViewModelState.Error
import com.intive.picover.common.viewmodel.state.ViewModelState.Loaded
import com.intive.picover.common.viewmodel.state.ViewModelState.Loading
import com.intive.picover.images.repository.ImagesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ImagesViewModel @Inject constructor(
	private val repository: ImagesRepository,
) : ViewModel() {

	private val _state = mutableStateOf<ViewModelState<List<Uri>>>(Loading)
	val state: State<ViewModelState<List<Uri>>> = _state

	init {
		viewModelScope.launch {
			loadImages()
		}
	}

	private suspend fun loadImages() {
		runCatching {
			repository.fetchImages()
		}.onSuccess {
			_state.value = Loaded(it.sorted())
		}.onFailure {
			_state.value = Error
		}
	}
}
