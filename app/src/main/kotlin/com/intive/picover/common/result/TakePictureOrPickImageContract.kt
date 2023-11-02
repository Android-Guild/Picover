package com.intive.picover.common.result

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import java.io.File

class TakePictureOrPickImageContract : ActivityResultContract<Unit, Uri?>() {

	private val takePictureContract by lazy { ActivityResultContracts.TakePicture() }
	private val pickVisualMediaContract by lazy { ActivityResultContracts.PickVisualMedia() }
	private lateinit var takenPictureUri: Uri

	override fun createIntent(context: Context, input: Unit): Intent {
		takenPictureUri = createTempFileAndGetUri(context)
		val intent = arrayOf(
			takePictureContract.createIntent(context, takenPictureUri),
			pickVisualMediaContract.createIntent(context, PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)),
		)
		return Intent.createChooser(Intent(Intent.ACTION_MEDIA_BUTTON), null)
			.putExtra(Intent.EXTRA_INITIAL_INTENTS, intent)
	}

	private fun createTempFileAndGetUri(context: Context): Uri =
		File.createTempFile("IMG_", ".jpg", context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)).let {
			FileProvider.getUriForFile(context, "${context.packageName}.provider", it)
		}

	override fun parseResult(resultCode: Int, intent: Intent?) =
		pickVisualMediaContract.parseResult(resultCode, intent)
			?: parseTakenPictureResult(resultCode, intent)

	private fun parseTakenPictureResult(resultCode: Int, intent: Intent?) =
		if (takePictureContract.parseResult(resultCode, intent)) {
			takenPictureUri
		} else {
			null
		}
}
