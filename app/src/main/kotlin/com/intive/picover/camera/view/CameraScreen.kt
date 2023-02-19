package com.intive.picover.camera.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.intive.picover.R
import com.intive.picover.camera.viewmodel.CameraViewModel

@Composable
fun CameraScreen(cameraViewModel: CameraViewModel) {
	Box(
		modifier = Modifier.fillMaxSize()
	) {
		Text(
			text = stringResource(id = R.string.camera),
			modifier = Modifier.align(Alignment.Center)
		)
	}
}

