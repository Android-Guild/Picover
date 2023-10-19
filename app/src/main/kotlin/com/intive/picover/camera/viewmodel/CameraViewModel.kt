package com.intive.picover.camera.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.intive.picover.camera.repository.ImageFileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
	imageFileRepository: ImageFileRepository,
) : ViewModel() {

	val takenImageUri = imageFileRepository.createTempFileAndGetUri()
	val isImageTaken = mutableStateOf(false)

	fun onImageTaken(wasSaved: Boolean) {
		if (wasSaved) {
			isImageTaken.value = true
		}
	}
}
