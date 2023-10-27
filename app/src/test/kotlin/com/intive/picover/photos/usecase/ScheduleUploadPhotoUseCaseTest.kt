package com.intive.picover.photos.usecase

import android.net.Uri
import androidx.work.Operation
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.google.common.util.concurrent.Futures
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot

class ScheduleUploadPhotoUseCaseTest : ShouldSpec(
	{
		val workManager: WorkManager = mockk()
		val tested = ScheduleUploadPhotoUseCase(workManager)

		should("enqueue work with photo uri as a input data") {
			val workRequestSlot = slot<WorkRequest>()
			val uri: Uri = mockk {
				every { this@mockk.toString() } returns "images/picture1.jpg"
			}
			coEvery { workManager.enqueue(capture(workRequestSlot)).result } returns Futures.immediateFuture(Operation.SUCCESS)

			tested(uri)

			workRequestSlot.captured.workSpec.input.getString("PHOTO_URI") shouldBe "images/picture1.jpg"
		}
	},
)
