package com.intive.picover.main.viewmodel.state

import com.intive.picover.auth.model.AuthEvent

sealed class MainState {

	companion object {
		fun fromAuthEvent(authEvent: AuthEvent) =
			when (authEvent) {
				is AuthEvent.Logged -> UserAuthorized
				is AuthEvent.NotLogged -> UserUnauthorized
			}
	}

	object Loading : MainState()
	object UserAuthorized : MainState()
	object UserUnauthorized : MainState()
}
