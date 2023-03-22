package com.example.android.picover.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.intive.picover.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDrawerContent() {
	ModalDrawerSheet {
		Box(
			modifier = Modifier.fillMaxSize(),
		) {
			Text(
				text = stringResource(R.string.Profile),
				modifier = Modifier.align(Alignment.Center),
			)
		}
	}
}
