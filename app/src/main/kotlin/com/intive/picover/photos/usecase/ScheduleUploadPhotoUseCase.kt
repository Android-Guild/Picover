package com.intive.picover.photos.usecase

import android.net.Uri
import androidx.work.WorkManager
import javax.inject.Inject

class ScheduleUploadPhotoUseCase @Inject constructor(
	private val workManager: WorkManager,
) {

	operator fun invoke(uri: Uri) {
		// TODO schedule upload
	}
}
