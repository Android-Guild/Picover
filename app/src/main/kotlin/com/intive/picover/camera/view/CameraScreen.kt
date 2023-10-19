package com.intive.picover.camera.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.intive.picover.R
import com.intive.picover.common.result.TakePictureContract
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun CameraScreen() {
	var pictureUri by remember { mutableStateOf<Uri?>(null) }
	val cameraLauncher = rememberLauncherForActivityResult(TakePictureContract()) {
		it?.let { pictureUri = it }
	}
	Screen(pictureUri) { cameraLauncher.launch(Unit) }
}

@Composable
private fun Screen(
	imageUri: Uri?,
	takeImage: () -> Unit,
) {
	Box(
		modifier = Modifier.fillMaxSize(),
		contentAlignment = Alignment.Center,
	) {
		if (imageUri != null) {
			TakenImage(imageUri)
		} else {
			TakePictureButton(onClick = takeImage)
		}
	}
}

@Composable
private fun TakenImage(imageUri: Uri) {
	CoilImage(
		imageModel = { imageUri },
		imageOptions = ImageOptions(
			contentScale = ContentScale.Crop,
			alignment = Alignment.Center,
		),
		previewPlaceholder = R.drawable.ic_launcher_foreground,
	)
}

@Composable
private fun TakePictureButton(onClick: () -> Unit) {
	Button(onClick = onClick) {
		Text(stringResource(R.string.TakePictureButton))
	}
}

@Preview(showBackground = true)
@Composable
private fun ImageNotTakenPreview() {
	Screen(imageUri = null) {}
}

@Preview(showBackground = true)
@Composable
private fun ImageTakenPreview() {
	Screen(imageUri = Uri.parse("")) {}
}
