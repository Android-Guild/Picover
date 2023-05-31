package com.intive.picover.camera.repository

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class ImageFileRepository @Inject constructor(
	@ApplicationContext private val context: Context,
) {

	fun createTempFileAndGetUri(): Uri? =
		File.createTempFile("IMG_", ".jpg", context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)).let {
			FileProvider.getUriForFile(context, "${context.packageName}.provider", it)
		}
}
