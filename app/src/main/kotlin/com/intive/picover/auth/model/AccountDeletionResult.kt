package com.intive.picover.auth.model

sealed class AccountDeletionResult {
	object Success : AccountDeletionResult()
	object ReAuthenticationNeeded : AccountDeletionResult()
}
