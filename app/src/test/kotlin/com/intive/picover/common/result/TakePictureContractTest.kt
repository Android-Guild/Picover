package com.intive.picover.common.result

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import java.io.File

class TakePictureContractTest : ShouldSpec(
	{
		val context: Context = mockk()
		val tested = TakePictureContract()

		beforeSpec {
			mockkStatic(File::class, FileProvider::class)
			mockkConstructor(ActivityResultContracts.TakePicture::class)
		}

		afterSpec {
			unmockkAll()
		}

		context("parse result WHEN created temp file uri passed to take picture delegate") {
			val packageName = "com.intive.picover"
			val externalFilesDir: File = mockk()
			val tempFile: File = mockk()
			val pictureUri: Uri = mockk()
			val intent: Intent = mockk()
			every { context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) } returns externalFilesDir
			every { context.packageName } returns packageName
			every { File.createTempFile("IMG_", ".jpg", externalFilesDir) } returns tempFile
			every { FileProvider.getUriForFile(context, "$packageName.provider", tempFile) } returns pictureUri
			every { anyConstructed<ActivityResultContracts.TakePicture>().createIntent(context, pictureUri) } returns intent

			tested.createIntent(context, Unit) shouldBe intent

			withData(
				Activity.RESULT_OK to pictureUri,
				Activity.RESULT_CANCELED to null,
			) { (resultCode, uri) ->
				tested.parseResult(resultCode, null) shouldBe uri
			}
		}
	},
)
