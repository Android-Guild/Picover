package com.intive.picover.main.viewmodel.state

import com.intive.picover.auth.model.AuthEvent
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class MainStateTest : ShouldSpec(
	{
		listOf(
			AuthEvent.Logged to MainState.UserAuthorized,
			AuthEvent.NotLogged to MainState.UserUnauthorized,
		).forEach { (authEvent, state) ->
			should("return $state WHEN fromAuthEvent called with $authEvent") {
				val result = MainState.fromAuthEvent(authEvent)

				result shouldBe state
			}
		}
	},
)
