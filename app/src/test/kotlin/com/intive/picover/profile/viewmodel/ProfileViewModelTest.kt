package com.intive.picover.profile.viewmodel

import android.net.Uri
import com.intive.picover.R
import com.intive.picover.auth.model.AccountDeletionResult
import com.intive.picover.auth.repository.AuthRepository
import com.intive.picover.common.coroutines.CoroutineTestExtension
import com.intive.picover.common.toast.ToastPublisher
import com.intive.picover.common.viewmodel.state.ViewModelState.Loaded
import com.intive.picover.common.viewmodel.state.ViewModelState.Loading
import com.intive.picover.profile.model.Profile
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe
import io.mockk.Awaits
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify

class ProfileViewModelTest : ShouldSpec(
	{
		extension(CoroutineTestExtension())
		isolationMode = IsolationMode.InstancePerTest

		val profile = mockk<Profile>()
		val uri = mockk<Uri>()
		val authRepository: AuthRepository = mockk(relaxed = true)
		val toastPublisher: ToastPublisher = mockk(relaxed = true)
		val tested by lazy { ProfileViewModel(authRepository, toastPublisher) }

		should("call logout on AuthRepository WHEN onLogoutClick") {
			tested.onLogoutClick()

			verify { authRepository.logout() }
		}

		should("call deleteAccount on AuthRepository AND show on ToastPublisher depending on result WHEN onDeleteAccountClick") {
			listOf(
				AccountDeletionResult.Success to R.string.DeleteAccountSuccessToastText,
				AccountDeletionResult.ReAuthenticationNeeded to R.string.DeleteAccountReAuthenticationToastText,
			).forAll { (result, textId) ->
				coEvery { authRepository.deleteAccount() } returns result

				tested.onDeleteAccountClick()

				coVerify {
					authRepository.deleteAccount()
					toastPublisher.show(textId)
				}
			}
		}

		should("profile return Profile data WHEN ProfileViewModelTest was initialized") {
			every { profile.name } returns "Marian"
			coEvery { authRepository.userProfile() } returns profile

			assertSoftly {
				tested.profile.value shouldBe Loaded(profile)
				tested.userName.value shouldBe "Marian"
			}
		}

		should("call updateUserAvatar WHEN updateAvatar called") {
			tested.updateAvatar(uri)

			coVerify { authRepository.updateUserAvatar(uri) }
		}

		should("load profile WHEN specific method called") {
			listOf(
				ManageProfileParam(
					{ authRepository.updateUserAvatar(uri) },
					{ tested.updateAvatar(uri) },
				),
				ManageProfileParam(
					{ authRepository.updateUserName("Marian K") },
					{ tested.updateName("Marian K") },
				),
			).forAll { param ->
				coEvery { param.profileMethod.invoke() } returns profile

				param.action.invoke()

				tested.profile.value shouldBe Loaded(profile)
			}
		}

		should("be loading WHEN specific method called and start executing") {
			listOf(
				ManageProfileParam(
					{ authRepository.updateUserAvatar(uri) },
					{ tested.updateAvatar(uri) },
				),
				ManageProfileParam(
					{ authRepository.updateUserName("Marian K") },
					{ tested.updateName("Marian K") },
				),
			).forAll { param ->
				coEvery { param.profileMethod.invoke() } just Awaits

				param.action.invoke()

				tested.profile.value shouldBe Loading
			}
		}

		should("set userName WHEN updateName called") {
			coEvery { authRepository.updateUserName("Marian K") } returns mockk {
				every { name } returns "Marian K"
			}

			tested.updateName("Marian K")

			tested.userName.value shouldBe "Marian K"
		}
	},
)

data class ManageProfileParam(
	val profileMethod: suspend () -> Profile,
	val action: () -> Unit,
)
