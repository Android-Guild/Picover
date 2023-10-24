package com.intive.picover.images.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.intive.picover.common.error.PicoverGenericError
import com.intive.picover.common.loader.PicoverLoader
import com.intive.picover.common.result.TakePictureContract
import com.intive.picover.images.viewmodel.ImagesViewModel
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun ImagesScreen(viewModel: ImagesViewModel) {
	val takePictureResultLauncher = rememberLauncherForActivityResult(TakePictureContract()) {
		it?.let(viewModel::scheduleUploadPhoto)
	}
	val state by viewModel.state
	when {
		state.isLoaded() -> PhotosGrid(state.data()) {
			takePictureResultLauncher.launch(Unit)
		}

		state.isError() -> PicoverGenericError()
		state.isLoading() -> PicoverLoader(modifier = Modifier.fillMaxSize())
	}
}

@Composable
private fun PhotosGrid(uris: List<Uri>, onTakeImage: () -> Unit) {
	Box(Modifier.fillMaxSize()) {
		LazyVerticalStaggeredGrid(
			columns = StaggeredGridCells.Adaptive(minSize = 120.dp),
			verticalItemSpacing = 4.dp,
			horizontalArrangement = Arrangement.spacedBy(4.dp),
		) {
			items(uris) {
				Photo(uri = it)
			}
		}
		FloatingActionButton(
			modifier = Modifier
				.padding(12.dp)
				.align(Alignment.BottomEnd),
			onClick = onTakeImage,
			containerColor = MaterialTheme.colorScheme.secondaryContainer,
			contentColor = MaterialTheme.colorScheme.secondary,
		) {
			Icon(Icons.Filled.PhotoCamera, null)
		}
	}
}

@Composable
private fun Photo(uri: Uri) {
	CoilImage(
		modifier = Modifier
			.defaultMinSize(minHeight = 120.dp)
			.wrapContentHeight(),
		imageModel = { uri },
		loading = {
			PicoverLoader(
				modifier = Modifier.fillMaxSize(),
			)
		},
	)
}
