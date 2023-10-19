package com.intive.picover.photos.usecase

import android.net.Uri
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.await
import androidx.work.workDataOf
import com.intive.picover.photos.work.UploadPhotoWork
import com.intive.picover.photos.work.UploadPhotoWorker
import javax.inject.Inject

class ScheduleUploadPhotoUseCase @Inject constructor(
	private val workManager: WorkManager,
) {

	suspend operator fun invoke(uri: Uri) {
		val constraints = Constraints.Builder()
			.setRequiredNetworkType(NetworkType.CONNECTED)
			.build()
		val uploadWork = OneTimeWorkRequestBuilder<UploadPhotoWorker>()
			.setInputData(workDataOf(UploadPhotoWork.URI_KEY to uri.toString()))
			.setConstraints(constraints)
			.build()
		workManager.enqueue(uploadWork)
			.await()
	}
}
