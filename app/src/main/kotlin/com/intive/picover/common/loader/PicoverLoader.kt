package com.intive.picover.common.loader

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun PicoverLoader(modifier: Modifier = Modifier) {
	Box(
		modifier = modifier,
		contentAlignment = Alignment.Center,
	) {
		CircularProgressIndicator()
	}
}
