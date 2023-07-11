package com.intive.picover.profile.viewmodel

import com.intive.picover.auth.repository.AuthRepository
import com.intive.picover.common.coroutines.CoroutineTestExtension
import com.intive.picover.common.viewmodel.state.ViewModelState.Loaded
import com.intive.picover.profile.model.Profile
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class ProfileViewModelTest : ShouldSpec(
	{
		extension(CoroutineTestExtension())
		isolationMode = IsolationMode.InstancePerTest

		val authRepository: AuthRepository = mockk(relaxed = true)
		val tested by lazy { ProfileViewModel(authRepository) }

		should("call logout on AuthRepository WHEN onLogoutClick") {
			tested.onLogoutClick()

			verify { authRepository.logout() }
		}

		should("call deleteAccount on AuthRepository WHEN onDeleteAccountClick") {
			tested.onDeleteAccountClick()

			coVerify { authRepository.deleteAccount() }
		}

		should("profile return Profile data WHEN ProfileViewModelTest was initialized") {
			val profile = mockk<Profile>()
			every { authRepository.userProfile() } returns profile

			tested.profile.value shouldBe Loaded(profile)
		}
	},
)
