package com.intive.picover.profile.viewmodel

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.intive.picover.auth.repository.AuthRepository
import com.intive.picover.common.viewmodel.StatefulViewModel
import com.intive.picover.common.viewmodel.state.ViewModelState.Error
import com.intive.picover.common.viewmodel.state.ViewModelState.Loaded
import com.intive.picover.common.viewmodel.state.ViewModelState.Loading
import com.intive.picover.profile.model.Profile
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
	private val authRepository: AuthRepository,
) : StatefulViewModel<Profile>() {

	private val _username = mutableStateOf("")
	val username: State<String> = _username

	init {
		fetchProfile()
	}

	fun onLogoutClick() {
		authRepository.logout()
	}

	fun updateAvatar(uri: Uri) {
		executeAndUpdateProfile {
			authRepository.updateUserAvatar(uri)
		}
	}

	fun saveUsername() {
		executeAndUpdateProfile {
			authRepository.updateUserName(username.value)
		}
	}

	fun fetchProfile() {
		executeAndUpdateProfile {
			authRepository.userProfile()
		}
	}

	fun onUsernameChange(username: String) {
		_username.value = username
	}

	fun initUsername() {
		_username.value = state.value.data().name
	}

	private fun executeAndUpdateProfile(action: suspend () -> Result<Profile>) {
		viewModelScope.launch {
			_state.value = Loading
			action()
				.onSuccess {
					_state.value = Loaded(it)
					initUsername()
				}.onFailure {
					_state.value = Error
				}
		}
	}
}
