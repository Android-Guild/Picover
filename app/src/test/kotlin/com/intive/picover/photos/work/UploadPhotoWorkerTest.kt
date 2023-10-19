package com.intive.picover.photos.work

import android.app.NotificationManager
import android.content.Context
import android.net.Uri
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.google.firebase.storage.StorageReference
import com.intive.picover.common.notification.provider.PicoverNotificationProvider
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.runs
import io.mockk.spyk
import io.mockk.unmockkAll

class UploadPhotoWorkerTest : ShouldSpec(
	{
		val context: Context = mockk(relaxed = true)
		val workerParams: WorkerParameters = mockk(relaxed = true)
		val storageReference: StorageReference = mockk()
		val notificationProvider: PicoverNotificationProvider = mockk(relaxed = true)
		val tested = spyk(UploadPhotoWorker(context, workerParams, storageReference, notificationProvider))

		beforeSpec {
			mockkStatic(Uri::parse)
		}

		afterSpec {
			unmockkAll()
		}

		should("return success WHEN photo uri uploaded successfully") {
			// TODO migrate to https://developer.android.com/guide/background/testing/persistent/integration-testing
			val photoUri: Uri = mockk()
			every { context.getSystemService(Context.NOTIFICATION_SERVICE) } returns mockk<NotificationManager>(relaxed = true)
			every { workerParams.inputData } returns workDataOf("PHOTO_URI" to "photo1.jpg")
			coEvery { tested.setForeground(any()) } just runs
			every { Uri.parse("photo1.jpg") } returns photoUri
			coEvery { storageReference.child("image").child(any()).putFile(photoUri) } returns mockk {
				every { isComplete } returns true
				every { exception } returns null
				every { isCanceled } returns false
				every { result } returns mockk()
			}

			val result = tested.doWork()

			result shouldBe ListenableWorker.Result.success()
		}
	},
)
