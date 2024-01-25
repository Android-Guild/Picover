package com.intive.picover.common.bottomsheet

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PicoverModalBottomSheet(
	onDismissRequest: () -> Unit,

	content: @Composable (ColumnScope.(bottomSheetModifier: Modifier) -> Unit),

) {
	ModalBottomSheet(
		onDismissRequest = onDismissRequest,
	) {
		content(
			Modifier.padding(16.dp)
				.navigationBarsPadding(),
		)
	}
}
