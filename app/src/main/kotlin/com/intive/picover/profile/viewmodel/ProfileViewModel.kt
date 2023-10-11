package com.intive.picover.profile.viewmodel

import android.net.Uri
import androidx.compose.runtime.derivedStateOf
import androidx.lifecycle.viewModelScope
import com.intive.picover.R
import com.intive.picover.auth.model.AccountDeletionResult
import com.intive.picover.auth.repository.AuthRepository
import com.intive.picover.common.toast.ToastPublisher
import com.intive.picover.common.validator.TextValidator
import com.intive.picover.common.validator.qualifier.Validator
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
	private val toastPublisher: ToastPublisher,
	@Validator.ShortText private val textValidator: TextValidator,
) : StatefulViewModel<Profile>() {

	val userName = derivedStateOf {
		_state.value.let { state ->
			if (state is Loaded) {
				state.data.name
			} else {
				""
			}
		}
	}

	init {
		fetchProfile()
	}

	fun onLogoutClick() {
		authRepository.logout()
	}

	fun onDeleteAccountClick() {
		viewModelScope.launch {
			val accountDeletionResult = authRepository.deleteAccount()
			when (accountDeletionResult) {
				is AccountDeletionResult.Success -> R.string.DeleteAccountSuccessToastText
				is AccountDeletionResult.ReAuthenticationNeeded -> R.string.DeleteAccountReAuthenticationToastText
			}.let {
				toastPublisher.show(it)
			}
		}
	}

	fun updateAvatar(uri: Uri) {
		executeAndUpdateProfile {
			authRepository.updateUserAvatar(uri)
		}
	}

	fun updateName(name: String) {
		executeAndUpdateProfile {
			authRepository.updateUserName(name)
		}
	}

	fun fetchProfile() {
		executeAndUpdateProfile {
			authRepository.userProfile()
		}
	}

	fun validatingName(text: String) =
		textValidator.validate(text)

	private fun executeAndUpdateProfile(action: suspend () -> Result<Profile>) {
		viewModelScope.launch {
			_state.value = Loading
			action()
				.onSuccess {
					_state.value = Loaded(it)
				}.onFailure {
					_state.value = Error
				}
		}
	}
}
