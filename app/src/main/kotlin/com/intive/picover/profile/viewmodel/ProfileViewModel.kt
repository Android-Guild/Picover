package com.intive.picover.profile.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intive.picover.auth.repository.AuthRepository
import com.intive.picover.common.viewmodel.state.ViewModelState
import com.intive.picover.common.viewmodel.state.ViewModelState.Loaded
import com.intive.picover.common.viewmodel.state.ViewModelState.Loading
import com.intive.picover.profile.model.Profile
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
	private val authRepository: AuthRepository,
) : ViewModel() {

	private val _profile = mutableStateOf<ViewModelState<Profile>>(Loading)
	val profile: State<ViewModelState<Profile>> = _profile

	init {
		fetchProfile()
	}

	fun onLogoutClick() {
		authRepository.logout()
	}

	fun onDeleteAccountClick() {
		viewModelScope.launch {
			authRepository.deleteAccount()
		}
	}

	private fun fetchProfile() {
		_profile.value = Loaded(authRepository.userProfile())
	}
}
