package com.intive.picover.images.view

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.intive.picover.common.loader.PicoverLoader
import com.intive.picover.common.viewmodel.state.ViewModelState
import com.intive.picover.images.viewmodel.ImagesViewModel
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun ImagesScreen(viewModel: ImagesViewModel) {
	val state by viewModel.state
	ValidateFirebaseStorageState(state)
}

@Composable
private fun ValidateFirebaseStorageState(state: ViewModelState<List<Uri>>) {
	when {
		state.isLoaded() -> LazyColumn(
			content = {
				items(state.data()) {
					ListItem(uri = it)
				}
			},
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center,
		)

		state.isError() -> {
			// TODO-KMA Handle the error
		}

		state.isLoading() -> PicoverLoader(modifier = Modifier.fillMaxSize())
	}
}

@Composable
private fun ListItem(uri: Uri) {
	Card(
		modifier = Modifier
			.padding(8.dp)
			.height(250.dp),
		colors = CardDefaults.cardColors(containerColor = Color.White),
		shape = RoundedCornerShape(15.dp),
		elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
	) {
		CoilImage(
			modifier = Modifier.fillMaxWidth(),
			imageModel = { uri },
			loading = {
				PicoverLoader(
					modifier = Modifier.fillMaxSize(),
				)
			},
		)
	}
}
