package com.intive.picover.profile.viewmodel

import android.net.Uri
import com.intive.picover.auth.repository.AuthRepository
import com.intive.picover.common.coroutines.CoroutineTestExtension
import com.intive.picover.common.viewmodel.state.ViewModelState.Error
import com.intive.picover.common.viewmodel.state.ViewModelState.Loaded
import com.intive.picover.common.viewmodel.state.ViewModelState.Loading
import com.intive.picover.profile.model.Profile
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe
import io.mockk.Awaits
import io.mockk.awaits
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify

class ProfileViewModelTest : ShouldSpec(
	{
		extension(CoroutineTestExtension())
		isolationMode = IsolationMode.InstancePerTest

		val profile: Profile = mockk(relaxed = true)
		val uri: Uri = mockk()
		val authRepository: AuthRepository = mockk(relaxed = true)
		val tested by lazy { ProfileViewModel(authRepository) }

		beforeSpec {
			coEvery { authRepository.userProfile() } just awaits
		}

		should("call logout on AuthRepository WHEN onLogoutClick") {
			tested.onLogoutClick()

			verify { authRepository.logout() }
		}

		should("profile return Profile data WHEN ProfileViewModelTest was initialized") {
			every { profile.name } returns "Marian"
			coEvery { authRepository.userProfile() } returns Result.success(profile)

			assertSoftly(tested) {
				state.value shouldBe Loaded(profile)
				username.value shouldBe "Marian"
			}
		}

		should("load profile WHEN specific method called") {
			listOf(
				ManageProfileParam(
					{ authRepository.updateUserAvatar(uri) },
					{ tested.updateAvatar(uri) },
				),
				ManageProfileParam(
					{ authRepository.updateUserName("") },
					{ tested.saveUsername() },
				),
				ManageProfileParam(
					{ authRepository.userProfile() },
					{ tested.fetchProfile() },
				),
			).forAll { param ->
				coEvery { param.profileMethod.invoke() } returns Result.success(profile)

				param.action.invoke()

				tested.state.value shouldBe Loaded(profile)
			}
		}

		should("be loading WHEN specific method called and start executing") {
			listOf(
				ManageProfileParam(
					{ authRepository.updateUserAvatar(uri) },
					{ tested.updateAvatar(uri) },
				),
				ManageProfileParam(
					{ authRepository.updateUserName("") },
					{ tested.saveUsername() },
				),
				ManageProfileParam(
					{ authRepository.userProfile() },
					{ tested.fetchProfile() },
				),
			).forAll { param ->
				coEvery { param.profileMethod.invoke() } just Awaits

				param.action.invoke()

				tested.state.value shouldBe Loading
			}
		}

		should("be error WHEN specific method throws exception") {
			listOf(
				ManageProfileParam(
					{ authRepository.updateUserAvatar(uri) },
					{ tested.updateAvatar(uri) },
				),
				ManageProfileParam(
					{ authRepository.updateUserName("") },
					{ tested.saveUsername() },
				),
				ManageProfileParam(
					{ authRepository.userProfile() },
					{ tested.fetchProfile() },
				),
			).forAll { param ->
				coEvery { param.profileMethod.invoke() } returns Result.failure(Exception())

				param.action.invoke()

				tested.state.value shouldBe Error
			}
		}

		should("set userName WHEN updateName called") {
			coEvery { authRepository.updateUserName("") } returns Result.success(Profile(uri, "Marian K", "test@gmail.com"))

			tested.saveUsername()

			tested.username.value shouldBe "Marian K"
		}
	},
)

data class ManageProfileParam(
	val profileMethod: suspend () -> Result<Profile>,
	val action: () -> Unit,
)
