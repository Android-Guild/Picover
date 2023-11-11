package com.intive.picover.photos.model

import android.net.Uri
import kotlin.random.Random

data class Photo(
	val height: Int,
	val width: Int,
	val uri: Uri,
) {
	companion object {
		fun withRandomSize(uri: Uri) =
			Photo(
				height = Random.nextInt(100, 200),
				width = Random.nextInt(200, 500),
				uri = uri,
			)
	}
}
