package com.intive.picover.camera.viewmodel

import android.net.Uri
import com.intive.picover.camera.repository.ImageFileRepository
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class CameraViewModelTest : ShouldSpec(
	{
		val imageFileRepository: ImageFileRepository = mockk()
		val tested by lazy { CameraViewModel(imageFileRepository) }

		should("set takenImageUri from ImageFileRepository AND isImageTaken to false WHEN created") {
			val imageUri: Uri = mockk()
			every { imageFileRepository.createTempFileAndGetUri() } returns imageUri

			assertSoftly(tested) {
				takenImageUri.value shouldBe imageUri
				isImageTaken.value shouldBe false
			}
		}

		should("set isImageTaken to true WHEN onImageTaken called with true") {
			tested.onImageTaken(true)

			tested.isImageTaken.value shouldBe true
		}
	},
)
