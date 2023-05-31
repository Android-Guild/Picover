package com.intive.picover.camera.repository

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import java.io.File

class ImageFileRepositoryTest : ShouldSpec(
	{
		val context: Context = mockk()
		val tested = ImageFileRepository(context)

		beforeSpec {
			mockkStatic(File::class, FileProvider::class)
		}

		afterSpec {
			unmockkAll()
		}

		should("return image file uri WHEN createTempFileAndGetUri") {
			val packageName = "com.intive.picover"
			val externalFilesDir: File = mockk()
			val tempFile: File = mockk()
			val imageUri: Uri = mockk()
			every { context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) } returns externalFilesDir
			every { context.packageName } returns packageName
			every { File.createTempFile("IMG_", ".jpg", externalFilesDir) } returns tempFile
			every { FileProvider.getUriForFile(context, "$packageName.provider", tempFile) } returns imageUri

			val result = tested.createTempFileAndGetUri()

			result shouldBe imageUri
		}
	},
)
