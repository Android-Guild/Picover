package com.intive.picover.profile.viewmodel

import com.intive.picover.auth.repository.AuthRepository
import com.intive.picover.common.coroutines.CoroutineTestExtension
import io.kotest.core.spec.style.ShouldSpec
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify

class ProfileViewModelTest : ShouldSpec(
	{
		extension(CoroutineTestExtension())

		val authRepository: AuthRepository = mockk(relaxed = true)
		val tested = ProfileViewModel(authRepository)

		should("call logout on AuthRepository WHEN onLogoutClick") {
			tested.onLogoutClick()

			verify { authRepository.logout() }
		}

		should("call deleteAccount on AuthRepository WHEN onDeleteAccountClick") {
			tested.onDeleteAccountClick()

			coVerify { authRepository.deleteAccount() }
		}
	},
)
