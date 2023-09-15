package com.intive.picover.images.repository

import com.google.firebase.storage.StorageReference
import com.intive.picover.common.coroutines.dispatcher.Dispatcher
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ImagesRepository @Inject constructor(
	storageReference: StorageReference,
	@Dispatcher.IO private val dispatcher: CoroutineDispatcher,
) {

	private val imageReference =
		storageReference.child("image")

	suspend fun fetchImages() =
		withContext(dispatcher) {
			imageReference.listAll().await().items
				.map { it.downloadUrl.await() }
		}
}
