package com.intive.picover.camera.viewmodel

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import com.intive.picover.camera.repository.ImageFileRepository
import com.intive.picover.common.viewmodel.LifecycleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// TODO-BCH Should be unit tested after Kotest setup
@HiltViewModel
class CameraViewModel @Inject constructor(
	private val imageFileRepository: ImageFileRepository,
) : LifecycleViewModel() {

	val takenImageUri = mutableStateOf<Uri?>(null)
	val isImageTaken = mutableStateOf(false)

	override fun onFirstCreate() {
		takenImageUri.value = imageFileRepository.createTempFileAndGetUri()
	}

	fun onImageTaken(wasSaved: Boolean) {
		if (wasSaved) {
			isImageTaken.value = true
		}
	}
}
