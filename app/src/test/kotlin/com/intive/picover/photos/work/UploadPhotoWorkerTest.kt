package com.intive.picover.photos.work

import android.net.Uri
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.google.firebase.storage.StorageReference
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll

class UploadPhotoWorkerTest : ShouldSpec(
	{
		val workerParams: WorkerParameters = mockk(relaxed = true)
		val storageReference: StorageReference = mockk()
		val tested = UploadPhotoWorker(mockk(), workerParams, storageReference)

		beforeSpec {
			mockkStatic(Uri::parse)
		}

		afterSpec {
			unmockkAll()
		}

		should("return success WHEN photo uri uploaded successfully") {
			val photoUri: Uri = mockk()
			every { workerParams.inputData } returns workDataOf("PHOTO_URI" to "photo1.jpg")
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
