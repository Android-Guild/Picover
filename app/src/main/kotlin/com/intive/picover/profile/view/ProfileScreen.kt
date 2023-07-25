package com.intive.picover.profile.view

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.intive.picover.R
import com.intive.picover.profile.model.Profile
import com.intive.picover.profile.viewmodel.ProfileViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
	val profile by viewModel.profile
	val context = LocalContext.current
	var isDeleteAccountDialogVisible by remember { mutableStateOf(false) }
	val photoLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
		if (uri != null) {
			viewModel.updateAvatar(uri)
		}
	}
	Column(
		modifier = Modifier
			.verticalScroll(rememberScrollState())
			.fillMaxSize()
			.padding(top = 10.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		if (profile.isLoaded()) {
			ProfileSegment(profile.data(), photoLauncher)
		} else {
			CircularProgressIndicator()
		}
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
		Button(
			onClick = {
				CustomTabsIntent
					.Builder()
					.build()
					.launchUrl(context, Urls.github)
			},
		) {
			Text(stringResource(R.string.GithubButton))
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

@Composable
private fun ProfileSegment(
	profile: Profile,
	launcher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
) {
	IconButton(
		modifier = Modifier
			.size(120.dp),
		onClick = {
			launcher.launch(
				PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
			)
		},
	) {
		UserAvatar(profile.photo)
	}
	TextButton(
		onClick = {
			launcher.launch(
				PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
			)
		},
	) {
		Text(stringResource(R.string.ClickToEdit))
	}
	Divider(
		modifier = Modifier
			.fillMaxWidth()
			.padding(all = 14.dp),
		thickness = 1.dp,
	)
	Text(
		modifier = Modifier
			.fillMaxWidth(),
		text = stringResource(id = R.string.UserName),
		fontSize = 20.sp,
		fontWeight = FontWeight.Bold,
		textAlign = TextAlign.Center,
	)
	Text(
		modifier = Modifier
			.padding(bottom = 14.dp),
		text = profile.name,
	)
	Text(
		modifier = Modifier
			.fillMaxWidth(),
		text = stringResource(id = R.string.UserEmail),
		fontSize = 20.sp,
		fontWeight = FontWeight.Bold,
		textAlign = TextAlign.Center,
	)
	Text(text = profile.email)
	Divider(
		modifier = Modifier
			.fillMaxWidth()
			.padding(all = 14.dp),
		thickness = 1.dp,
	)
}

@Composable
private fun UserAvatar(
	imageUri: Uri?,
) {
	CoilImage(
		imageModel = { imageUri },
		modifier = Modifier
			.size(120.dp)
			.clip(CircleShape),
		imageOptions = ImageOptions(
			alignment = Alignment.Center,
			contentDescription = null,
			contentScale = ContentScale.Crop,
		),
		loading = {
			CircularProgressIndicator()
		},
		failure = {
			Image(
				painter = painterResource(id = R.drawable.ic_avatar_placeholder),
				contentDescription = null,
				modifier = Modifier
					.size(120.dp)
					.clip(CircleShape),
				alignment = Alignment.Center,
				contentScale = ContentScale.Crop,
			)
		},
	)
}

private object Urls {
	val github = Uri.parse("https://github.com/intive/Picover")
}
