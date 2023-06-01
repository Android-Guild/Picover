package com.intive.picover.images.repository

import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ImagesRepository @Inject constructor(
	firebaseStorage: FirebaseStorage,
	private val ioDispatcher: CoroutineDispatcher,
) {

	private val firebaseStorageReference =
		firebaseStorage.reference.child("image")

	suspend fun fetchImages() =
		withContext(ioDispatcher) {
			firebaseStorageReference.listAll().await().items
				.map { it.downloadUrl.await() }
		}
}
