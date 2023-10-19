package com.intive.picover.profile.view

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.intive.picover.R
import com.intive.picover.common.error.PicoverGenericError
import com.intive.picover.common.result.TakePictureOrPickImageContract
import com.intive.picover.common.result.launch
import com.intive.picover.common.viewmodel.state.ViewModelState.Error
import com.intive.picover.common.viewmodel.state.ViewModelState.Loaded
import com.intive.picover.common.viewmodel.state.ViewModelState.Loading
import com.intive.picover.profile.model.Profile
import com.intive.picover.profile.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
	viewModel: ProfileViewModel,
	navController: NavHostController,
) {
	val state by viewModel.state
	val takePictureOrPickImageLauncher = rememberLauncherForActivityResult(TakePictureOrPickImageContract()) { uri ->
		uri?.let(viewModel::updateAvatar)
	}
	var showProfileUpdateBottomSheet by rememberSaveable { mutableStateOf(false) }
	var userName by remember { mutableStateOf("") }
	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(top = 10.dp),
	) {
		when (state) {
			is Loaded -> {
				UserInfo(
					profile = state.data(),
					onEditPhotoClick = takePictureOrPickImageLauncher::launch,
					onEditNameClick = {
						showProfileUpdateBottomSheet = true
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
					onEditPhotoClick = takePictureOrPickImageLauncher::launch,
					onEditNameClick = {
						showProfileUpdateBottomSheet = true
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
		ProfileActions(
			onLogoutClick = viewModel::onLogoutClick,
			onDeleteAccountCLick = {
				navController.navigate("profile/deleteAccount")
			},
		)
	}
	if (showProfileUpdateBottomSheet) {
		userName = viewModel.userName.value
		ProfileUpdateBottomSheet(
			userName = userName,
			updateUserName = { userName = it },
			updateName = { viewModel.updateName(it) },
			updateVisibility = { showProfileUpdateBottomSheet = it },
			validateName = { viewModel.validatingName(it).isValid() },
			getInvalidNameErrorMessageId = { viewModel.validatingName(it).errorMessageId },
		)
	}
}
