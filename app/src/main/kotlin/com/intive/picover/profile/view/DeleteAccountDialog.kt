package com.intive.picover.profile.view

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.intive.picover.R
import com.intive.picover.common.annotation.LightDarkPreview
import com.intive.picover.main.theme.PicoverTheme

@Composable
fun DeleteAccountDialog(
	onConfirm: () -> Unit,
	onDismiss: () -> Unit,
) {
	AlertDialog(
		onDismissRequest = onDismiss,
		confirmButton = {
			TextButton(onClick = onConfirm) {
				Text(stringResource(R.string.DeleteButton).uppercase())
			}
		},
		dismissButton = {
			TextButton(onClick = onDismiss) {
				Text(stringResource(R.string.CancelButton).uppercase())
			}
		},
		title = {
			Text(stringResource(R.string.DeleteAccountConfirmationDialogTitle))
		},
		text = {
			Text(stringResource(R.string.DeleteAccountConfirmationDialogDescription))
		},
	)
}

@LightDarkPreview
@Composable
private fun DeleteAccountDialogPreview() {
	PicoverTheme {
		DeleteAccountDialog({}, {})
	}
}
