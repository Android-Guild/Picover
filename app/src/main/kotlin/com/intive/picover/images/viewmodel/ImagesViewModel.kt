package com.intive.picover.images.viewmodel

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.intive.picover.common.viewmodel.StatefulViewModel
import com.intive.picover.common.viewmodel.state.ViewModelState.Error
import com.intive.picover.common.viewmodel.state.ViewModelState.Loaded
import com.intive.picover.images.repository.ImagesRepository
import com.intive.picover.photos.usecase.UploadPhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ImagesViewModel @Inject constructor(
	private val repository: ImagesRepository,
	private val uploadPhotoUseCase: UploadPhotoUseCase,
) : StatefulViewModel<List<Uri>>() {

	init {
		viewModelScope.launch {
			loadImages()
		}
	}

	fun uploadPhoto(uri: Uri) {
		uploadPhotoUseCase(uri)
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
