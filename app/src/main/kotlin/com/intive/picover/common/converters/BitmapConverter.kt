package com.intive.picover.common.converters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.provider.MediaStore
import com.intive.picover.common.sdk.AndroidSdkVersion
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class BitmapConverter @Inject constructor(
	@ApplicationContext private val context: Context,
) {

	fun convertBitmapToBytes(uri: Uri, qualityPercent: Int = 50): ByteArrayOutputStream {
		val byteArrayOutputStream = ByteArrayOutputStream()
		uri.toBitmap(context).compress(Bitmap.CompressFormat.JPEG, qualityPercent, byteArrayOutputStream)
		return byteArrayOutputStream
	}

	private fun Uri.toBitmap(context: Context) =
		if (AndroidSdkVersion.isVersionUnderPieSdk()) {
			MediaStore.Images.Media.getBitmap(context.contentResolver, this)
		} else {
			val source = ImageDecoder.createSource(context.contentResolver, this)
			ImageDecoder.decodeBitmap(source)
		}
}
