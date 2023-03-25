package com.intive.picover.images.viewmodel

import android.net.Uri

sealed class ImagesStorageState {
	data class Success(val uris: List<Uri>) : ImagesStorageState()
	data class Error(val exception: Throwable) : ImagesStorageState()
	object Loading : ImagesStorageState()
}
