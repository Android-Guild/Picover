package com.intive.picover.profile.view

import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.intive.picover.R
import com.intive.picover.common.annotation.LightDarkPreview
import com.intive.picover.main.theme.PicoverTheme
import com.intive.picover.profile.delete.model.DeleteAccountEvent
import com.intive.picover.profile.delete.model.DeleteAccountSideEffect
import com.intive.picover.profile.delete.viewmodel.DeleteAccountViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DeleteAccountDialog(
	viewModel: DeleteAccountViewModel,
	navController: NavHostController,
) {
	val context = LocalContext.current
	LaunchedEffect(true) {
		viewModel.sideEffects.collectLatest { effect ->
			when (effect) {
				DeleteAccountSideEffect.ShowSuccessMessage -> R.string.DeleteAccountSuccessToastText
				DeleteAccountSideEffect.ShowReAuthenticationNeededMessage -> R.string.DeleteAccountReAuthenticationToastText
			}.let {
				Toast.makeText(context, it, Toast.LENGTH_LONG).show()
			}
		}
	}
	Content(
		onConfirm = {
			viewModel.emitEvent(DeleteAccountEvent.DeleteAccount)
			navController.popBackStack()
		},
		onDismiss = {
			navController.popBackStack()
		},
	)
}

@Composable
private fun Content(
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
		Content({}, {})
	}
}
