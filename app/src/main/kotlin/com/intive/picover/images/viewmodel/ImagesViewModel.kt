package com.intive.picover.images.viewmodel

import android.net.Uri
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.viewModelScope
import com.intive.picover.common.viewmodel.StatefulViewModel
import com.intive.picover.common.viewmodel.state.ViewModelState.Error
import com.intive.picover.common.viewmodel.state.ViewModelState.Loaded
import com.intive.picover.images.repository.ImagesRepository
import com.intive.picover.photos.usecase.ScheduleUploadPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ImagesViewModel @Inject constructor(
	private val repository: ImagesRepository,
	private val scheduleUploadPhotoUseCase: ScheduleUploadPhotoUseCase,
	private val snackbarHostState: SnackbarHostState,
) : StatefulViewModel<List<Uri>>() {

	init {
		viewModelScope.launch {
			loadImages()
		}
	}

	fun scheduleUploadPhoto(uri: Uri) {
		viewModelScope.launch {
			runCatching {
				scheduleUploadPhotoUseCase(uri)
			}.onSuccess {
				snackbarHostState.showSnackbar("Upload scheduled successfully")
			}.onFailure {
				snackbarHostState.showSnackbar("Error: $it")
			}
		}
	}

	private suspend fun loadImages() {
		runCatching {
			repository.fetchImages()
		}.onSuccess {
			_state.value = Loaded(it)
		}.onFailure {
			_state.value = Error
		}
	}
}
