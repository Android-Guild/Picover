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
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.unmockkAll
import java.io.File

class TakePictureOrPickImageContractTest : ShouldSpec(
	{
		val tested = TakePictureOrPickImageContract()

		beforeSpec {
			mockkStatic(
				File::class,
				FileProvider::class,
				Intent::class,
			)
			mockkConstructor(
				ActivityResultContracts.TakePicture::class,
				ActivityResultContracts.PickVisualMedia::class,
			)
		}

		afterSpec {
			unmockkAll()
		}

		context("parse result WHEN created temp file uri passed to take picture delegate") {
			val context: Context = mockk(relaxed = true)
			val packageName = "com.intive.picover"
			val externalFilesDir: File = mockk()
			val tempFile: File = mockk()
			val takenPictureUri: Uri = mockk()
			val takePictureIntent: Intent = mockk()
			val pickMediaIntent: Intent = mockk()
			val intentsSlot = slot<Array<Intent>>()
			val intentChooser: Intent = mockk()
			every { context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) } returns externalFilesDir
			every { context.packageName } returns packageName
			every { File.createTempFile("IMG_", ".jpg", externalFilesDir) } returns tempFile
			every { FileProvider.getUriForFile(context, "$packageName.provider", tempFile) } returns takenPictureUri
			every { anyConstructed<ActivityResultContracts.TakePicture>().createIntent(context, takenPictureUri) } returns takePictureIntent
			every { anyConstructed<ActivityResultContracts.PickVisualMedia>().createIntent(context, any()) } returns pickMediaIntent
			every { Intent.createChooser(any(), null).putExtra(Intent.EXTRA_INITIAL_INTENTS, capture(intentsSlot)) } returns intentChooser

			tested.createIntent(context, Unit) shouldBe intentChooser
			intentsSlot.captured shouldContainExactly arrayOf(takePictureIntent, pickMediaIntent)

			val pickedPhotoUri: Uri = mockk()
			withData(
				Triple(pickedPhotoUri, false, pickedPhotoUri),
				Triple(null, true, takenPictureUri),
				Triple(null, false, null),
			) { (pickPictureResult, takePictureResult, resultUri) ->
				val intent: Intent = mockk()
				every { anyConstructed<ActivityResultContracts.PickVisualMedia>().parseResult(any(), intent) } returns pickPictureResult
				every { anyConstructed<ActivityResultContracts.TakePicture>().parseResult(any(), intent) } returns takePictureResult

				tested.parseResult(Activity.RESULT_OK, intent) shouldBe resultUri
			}
		}
	},
)
