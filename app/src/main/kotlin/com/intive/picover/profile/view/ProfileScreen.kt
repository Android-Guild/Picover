package com.intive.picover.profile.view

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.intive.picover.R
import com.intive.picover.common.error.PicoverGenericError
import com.intive.picover.common.viewmodel.state.ViewModelState.Error
import com.intive.picover.common.viewmodel.state.ViewModelState.Loaded
import com.intive.picover.common.viewmodel.state.ViewModelState.Loading
import com.intive.picover.profile.model.Profile
import com.intive.picover.profile.viewmodel.ProfileViewModel
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
				UserInfo(
					profile = profile.data(),
					onEditPhotoClick = {
						photoLauncher.launch(
							PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
						)
					},
					onEditNameClick = {
						coroutineScope.launch {
							sheetState.show()
						}
					},
				)
			}

			is Loading -> {
				val loadingProfile = Profile(null, stringResource(id = R.string.Loading), stringResource(id = R.string.Loading))
				LinearProgressIndicator(
					modifier = Modifier
						.fillMaxWidth(),
				)
				UserInfo(
					profile = loadingProfile,
					onEditPhotoClick = {
						photoLauncher.launch(
							PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
						)
					},
					onEditNameClick = {
						coroutineScope.launch {
							sheetState.show()
						}
					},
					editButtonsEnabled = false,
					showShimmer = true,
				)
			}

			is Error -> {
				PicoverGenericError(
					message = stringResource(R.string.ProfileError),
					onRetryClick = viewModel::fetchProfile,
				)
				Divider(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp))
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
							enabled = viewModel.validatingName(userName).isValid(),
						) {
							Icon(Icons.Rounded.Check, contentDescription = null)
						}
					},
				)
			}
			Column(
				modifier = Modifier
					.padding(start = 20.dp, end = 20.dp, bottom = 42.dp)
					.fillMaxWidth(),
			) {
				TextField(
					modifier = Modifier
						.fillMaxWidth(),
					value = userName,
					onValueChange = {
						userName = it
					},
					supportingText = {
						if (!viewModel.validatingName(userName).isValid()) {
							Text(
								modifier = Modifier
									.fillMaxWidth(),
								text = stringResource(id = viewModel.validatingName(userName).errorMessageId),
							)
						}
						Text(
							text = "${userName.length} / 20",
							modifier = Modifier.fillMaxWidth(),
							textAlign = TextAlign.End,
						)
					},
					isError = !viewModel.validatingName(userName).isValid(),
					label = { Text(stringResource(id = R.string.UserName)) },
				)
			}
		}
	}
}

private object Urls {
	val github = Uri.parse("https://github.com/intive/Picover")
}
