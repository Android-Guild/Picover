package com.intive.picover.common.result

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import java.io.File

class TakePictureContract : ActivityResultContract<Unit, Uri?>() {

	private val delegate by lazy { ActivityResultContracts.TakePicture() }
	private lateinit var pictureUri: Uri

	override fun createIntent(context: Context, input: Unit): Intent {
		pictureUri = createTempFileAndGetUri(context)
		return delegate.createIntent(context, pictureUri)
	}

	private fun createTempFileAndGetUri(context: Context): Uri =
		File.createTempFile("IMG_", ".jpg", context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)).let {
			FileProvider.getUriForFile(context, "${context.packageName}.provider", it)
		}

	override fun parseResult(resultCode: Int, intent: Intent?) =
		if (delegate.parseResult(resultCode, intent)) {
			pictureUri
		} else {
			null
		}
}
