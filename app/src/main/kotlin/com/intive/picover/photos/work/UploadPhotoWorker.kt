package com.intive.picover.photos.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.google.firebase.storage.StorageReference
import com.intive.picover.R
import com.intive.picover.common.notification.provider.PicoverNotificationProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDateTime
import kotlin.random.Random
import kotlin.random.nextInt
import kotlinx.coroutines.tasks.await

@HiltWorker
@RequiresApi(Build.VERSION_CODES.S)
class UploadPhotoWorker @AssistedInject constructor(
	@Assisted appContext: Context,
	@Assisted workerParams: WorkerParameters,
	private val storageReference: StorageReference,
	private val notificationProvider: PicoverNotificationProvider,
) : CoroutineWorker(appContext, workerParams) {

	private val notificationManager by lazy { applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
	private val uploadOngoingNotificationId = Random.nextInt(1..10)
	private val uploadFinishedNotificationId = 11

	override suspend fun doWork(): Result {
		createNotificationChannel()
		setForeground(ForegroundInfo(uploadOngoingNotificationId, notificationProvider.provideUploadOngoing(id)))
		val photoUri = inputData.getString(UploadPhotoWork.URI_KEY)!!.toUri()
		uploadPhoto(photoUri)
		notificationManager.notify(uploadFinishedNotificationId, notificationProvider.provideUploadFinished(photoUri))
		return Result.success()
	}

	private suspend fun uploadPhoto(photoUri: Uri) {
		storageReference.child("image")
			.child("photo_${LocalDateTime.now()}")
			.putFile(photoUri)
			.await()
	}

	private fun createNotificationChannel() {
		// TODO extract and update on configuration change - locale
		val channelName = applicationContext.getString(R.string.UploadChannelName)
		val channel = NotificationChannel(Channel.ID, channelName, NotificationManager.IMPORTANCE_HIGH)
		notificationManager.createNotificationChannel(channel)
	}

	private object Channel {
		const val ID = "10"
	}
}
