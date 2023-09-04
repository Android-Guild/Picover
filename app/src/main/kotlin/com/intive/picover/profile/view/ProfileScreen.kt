package com.intive.picover.profile.view

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.intive.picover.R
import com.intive.picover.common.animations.ShimmerBrush
import com.intive.picover.common.error.PicoverGenericError
import com.intive.picover.common.viewmodel.state.ViewModelState.Error
import com.intive.picover.common.viewmodel.state.ViewModelState.Loaded
import com.intive.picover.common.viewmodel.state.ViewModelState.Loading
import com.intive.picover.profile.model.Profile
import com.intive.picover.profile.viewmodel.ProfileViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
	val profile by viewModel.profile
	val context = LocalContext.current
	val keyboardController = LocalSoftwareKeyboardController.current
	var isDeleteAccountDialogVisible by remember { mutableStateOf(false) }
	val photoLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
		if (uri != null) {
			viewModel.updateAvatar(uri)
		}
	}
	val coroutineScope = rememberCoroutineScope()
	val sheetState = rememberModalBottomSheetState()
	var userName by remember { mutableStateOf("") }
	Column(
		modifier = Modifier
			.verticalScroll(rememberScrollState())
			.fillMaxSize()
			.padding(top = 10.dp),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		when (profile) {
			is Loaded -> {
				ProfileSegment(profile.data(), photoLauncher, sheetState, coroutineScope)
			}

			is Loading -> {
				val loadingProfile = Profile(null, stringResource(id = R.string.Loading), stringResource(id = R.string.Loading))
				LinearProgressIndicator(
					modifier = Modifier
						.fillMaxWidth(),
				)
				ProfileSegment(loadingProfile, photoLauncher, sheetState, coroutineScope, editButtonsEnabled = false, showShimmer = true)
			}

			is Error -> {
				PicoverGenericError(
					message = stringResource(R.string.ProfileError),
					onRetryClick = viewModel::fetchProfile,
				)
				Divider(
					modifier = Modifier
						.fillMaxWidth()
						.padding(start = 14.dp, end = 14.dp, bottom = 14.dp),
					thickness = 1.dp,
				)
			}
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
	if (sheetState.isVisible) {
		userName = viewModel.userName.value
		ModalBottomSheet(
			modifier = Modifier
				.padding(bottom = 56.dp),
			sheetState = sheetState,
			onDismissRequest = {
				coroutineScope.launch {
					sheetState.hide()
					keyboardController?.hide()
				}
			},
		) {
			Row(horizontalArrangement = Arrangement.SpaceAround) {
				CenterAlignedTopAppBar(
					navigationIcon = {
						IconButton(
							onClick = {
								coroutineScope.launch {
									sheetState.hide()
									keyboardController?.hide()
								}
							},
						) {
							Icon(Icons.Rounded.Close, contentDescription = null)
						}
					},
					title = { Text(stringResource(id = R.string.EditUserData)) },
					actions = {
						IconButton(
							onClick = {
								viewModel.updateName(userName)
								coroutineScope.launch {
									sheetState.hide()
									keyboardController?.hide()
								}
							},
						) {
							Icon(Icons.Rounded.Check, contentDescription = null)
						}
					},
				)
			}
			// TODO NAN add validation for text field
			TextField(
				modifier = Modifier
					.padding(start = 20.dp, end = 20.dp, bottom = 42.dp)
					.fillMaxWidth(),
				value = userName,
				onValueChange = {
					userName = it
				},
				label = { Text(stringResource(id = R.string.UserName)) },
			)
		}
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileSegment(
	profile: Profile,
	launcher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
	sheetState: SheetState,
	coroutineScope: CoroutineScope,
	editButtonsEnabled: Boolean = true,
	showShimmer: Boolean = false,
) {
	IconButton(
		modifier = Modifier
			.size(120.dp),
		onClick = {
			launcher.launch(
				PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
			)
		},
		enabled = editButtonsEnabled,
	) {
		if (!showShimmer) {
			UserAvatar(profile.photo)
		} else {
			val brush = ShimmerBrush(targetValue = 1300f, showShimmer = true)
			Canvas(
				modifier = Modifier.size(120.dp),
				onDraw = {
					drawRect(
						brush = brush,
						size = size,
					)
				},
			)
		}
	}
	TextButton(
		onClick = {
			launcher.launch(
				PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
			)
		},
		enabled = editButtonsEnabled,
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
	Row(
		modifier = Modifier
			.fillMaxWidth(),
		verticalAlignment = Alignment.CenterVertically,
	) {
		Spacer(modifier = Modifier.width(48.dp))
		Text(
			modifier = Modifier
				.weight(1f),
			text = profile.name,
			textAlign = TextAlign.Center,
		)
		IconButton(
			onClick = {
				coroutineScope.launch {
					sheetState.show()
				}
			},
			enabled = editButtonsEnabled,
		) {
			Icon(
				modifier = Modifier.size(24.dp),
				imageVector = Icons.Rounded.Edit,
				contentDescription = null,
			)
		}
	}
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
