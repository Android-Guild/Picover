package com.intive.picover.profile.delete.viewmodel

import androidx.lifecycle.viewModelScope
import com.intive.picover.auth.model.AccountDeletionResult
import com.intive.picover.auth.repository.AuthRepository
import com.intive.picover.common.viewmodel.MVIViewModel
import com.intive.picover.profile.delete.model.DeleteAccountEvent
import com.intive.picover.profile.delete.model.DeleteAccountSideEffect
import com.intive.picover.profile.delete.model.DeleteAccountState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class DeleteAccountViewModel @Inject constructor(
	private val authRepository: AuthRepository,
) : MVIViewModel<DeleteAccountState, DeleteAccountEvent, DeleteAccountSideEffect>(initialState = DeleteAccountState()) {

	override fun handleEvent(event: DeleteAccountEvent) {
		when (event) {
			is DeleteAccountEvent.DeleteAccount -> deleteAccount()
		}
	}

	private fun deleteAccount() {
		viewModelScope.launch {
			val accountDeletionResult = authRepository.deleteAccount()
			when (accountDeletionResult) {
				is AccountDeletionResult.Success -> DeleteAccountSideEffect.ShowSuccessMessage
				is AccountDeletionResult.ReAuthenticationNeeded -> DeleteAccountSideEffect.ShowReAuthenticationNeededMessage
			}.let {
				emitEffect(it)
			}
		}
	}
}
