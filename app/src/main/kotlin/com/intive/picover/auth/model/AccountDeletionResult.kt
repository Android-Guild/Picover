package com.intive.picover.auth.model

sealed class AccountDeletionResult {
	data object Success : AccountDeletionResult()
	data object ReAuthenticationNeeded : AccountDeletionResult()
}
