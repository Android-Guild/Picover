package com.intive.picover.camera.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.intive.picover.R
import com.intive.picover.camera.viewmodel.CameraViewModel
import com.intive.picover.common.loader.PicoverLoader
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun CameraScreen(viewModel: CameraViewModel) {
	val takenImageUri by viewModel.takenImageUri
	val isImageTaken by viewModel.isImageTaken
	val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
		viewModel.onImageTaken(wasSaved = it)
	}
	Screen(takenImageUri, isImageTaken) { cameraLauncher.launch(takenImageUri) }
}

@Composable
private fun Screen(
	imageUri: Uri?,
	isImageTaken: Boolean,
	takeImage: () -> Unit,
) {
	Box(
		modifier = Modifier.fillMaxSize(),
		contentAlignment = Alignment.Center,
	) {
		if (imageUri == null) {
			PicoverLoader()
		} else {
			Content(imageUri, isImageTaken, takeImage)
		}
	}
}

@Composable
private fun Content(
	imageUri: Uri,
	isImageTaken: Boolean,
	takeImage: () -> Unit,
) {
	if (isImageTaken) {
		TakenImage(imageUri)
	} else {
		TakePictureButton(onClick = takeImage)
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
		previewPlaceholder = R.drawable.ic_launcher_background,
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
private fun LoadingPreview() {
	Screen(imageUri = null, isImageTaken = false) {}
}

@Preview(showBackground = true)
@Composable
private fun ImageNotTakenPreview() {
	Screen(imageUri = Uri.parse(""), isImageTaken = false) {}
}

@Preview(showBackground = true)
@Composable
private fun ImageTakenPreview() {
	Screen(imageUri = Uri.parse(""), isImageTaken = true) {}
}
