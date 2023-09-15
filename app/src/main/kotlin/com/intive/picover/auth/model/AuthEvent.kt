package com.intive.picover.auth.model

sealed class AuthEvent {
	data object NotLogged : AuthEvent()
	data object Logged : AuthEvent()
}
