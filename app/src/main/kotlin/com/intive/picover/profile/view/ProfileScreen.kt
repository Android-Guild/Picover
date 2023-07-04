package com.intive.picover.profile.view

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.intive.picover.R
import com.intive.picover.profile.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
	val context = LocalContext.current
	var isDeleteAccountDialogVisible by remember { mutableStateOf(false) }
	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Button(
			onClick = {
				context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
			},
		) {
			Text(stringResource(R.string.OpenLicenses))
		}
		Button(onClick = viewModel::onLogoutClick) {
			Text(stringResource(R.string.LogoutButton))
		}
		Button(
			onClick = {
				isDeleteAccountDialogVisible = true
			},
		) {
			Text(stringResource(R.string.DeleteAccountButton))
		}
	}
	if (isDeleteAccountDialogVisible) {
		DeleteAccountDialog(
			onConfirm = {
				viewModel.onDeleteAccountClick()
				isDeleteAccountDialogVisible = false
			},
			onDismiss = { isDeleteAccountDialogVisible = false },
		)
	}
}

@Composable
private fun DeleteAccountDialog(
	onConfirm: () -> Unit,
	onDismiss: () -> Unit,
) {
	AlertDialog(
		onDismissRequest = onDismiss,
		confirmButton = {
			TextButton(onClick = onConfirm) {
				Text(stringResource(R.string.ConfirmButton).uppercase())
			}
		},
		dismissButton = {
			TextButton(onClick = onDismiss) {
				Text(stringResource(R.string.CancelButton).uppercase())
			}
		},
		title = { Text(stringResource(R.string.DeleteAccountConfirmationDialogTitle)) },
		text = { Text(stringResource(R.string.DeleteAccountConfirmationDialogDescription)) },
	)
}
