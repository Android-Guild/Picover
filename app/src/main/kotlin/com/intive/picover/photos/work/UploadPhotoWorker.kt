package com.intive.picover.photos.work

import android.content.Context
import androidx.core.net.toUri
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.firebase.storage.StorageReference
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDateTime
import kotlinx.coroutines.tasks.await

@HiltWorker
class UploadPhotoWorker @AssistedInject constructor(
	@Assisted appContext: Context,
	@Assisted workerParams: WorkerParameters,
	private val storageReference: StorageReference,
) : CoroutineWorker(appContext, workerParams) {

	override suspend fun doWork(): Result {
		val photoUri = inputData.getString(UploadPhotoWork.URI_KEY)!!.toUri()
		storageReference.child("image")
			.child("photo_${LocalDateTime.now()}")
			.putFile(photoUri)
			.await()
		return Result.success()
	}
}
