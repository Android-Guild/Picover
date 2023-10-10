package com.intive.picover.articles.repository

import com.google.firebase.storage.StorageReference
import javax.inject.Inject
import kotlinx.coroutines.tasks.await

class ArticlesRepository @Inject constructor(
	storageReference: StorageReference,
) {
	private val articles by lazy { storageReference.child("article") }

	suspend fun names() =
		runCatching {
			articles.listAll()
				.await()
				.items
				.map { it.name }
		}
}
