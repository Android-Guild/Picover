package com.intive.picover.auth.model

sealed class AuthEvent {
	object NotLogged : AuthEvent()
	object Logged : AuthEvent()
}
