package com.intive.picover.main.viewmodel

import com.intive.picover.auth.model.AuthEvent
import com.intive.picover.auth.repository.AuthRepository
import com.intive.picover.main.viewmodel.state.MainState
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class MainViewModelTest : ShouldSpec(
	{
		val authRepository: AuthRepository = mockk()
		val tested by lazy { MainViewModel(authRepository) }

		beforeSpec {
			mockkObject(MainState)
		}

		afterSpec {
			unmockkAll()
		}

		should("set state according to AuthEvent returned from observeEvents WHEN created") {
			val authEvent = AuthEvent.Logged
			val mainState = MainState.UserAuthorized
			every { authRepository.observeEvents() } returns flowOf(authEvent)
			every { MainState.fromAuthEvent(authEvent) } returns mainState

			tested.state.first() shouldBe mainState
		}
	},
)
