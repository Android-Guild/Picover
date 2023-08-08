package com.intive.picover.images.repository

import com.google.firebase.storage.StorageReference
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ImagesRepository @Inject constructor(
	storageReference: StorageReference,
	private val ioDispatcher: CoroutineDispatcher,
) {

	private val imageReference =
		storageReference.child("image")

	suspend fun fetchImages() =
		withContext(ioDispatcher) {
			imageReference.listAll().await().items
				.map { it.downloadUrl.await() }
		}
}
