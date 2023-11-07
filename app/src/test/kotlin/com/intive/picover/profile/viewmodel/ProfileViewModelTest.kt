package com.intive.picover.profile.viewmodel

import android.net.Uri
import com.intive.picover.R
import com.intive.picover.auth.model.AccountDeletionResult
import com.intive.picover.auth.repository.AuthRepository
import com.intive.picover.common.coroutines.CoroutineTestExtension
import com.intive.picover.common.toast.ToastPublisher
import com.intive.picover.common.validator.TextValidator
import com.intive.picover.common.validator.ValidationStatus
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
import io.mockk.coVerify
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
		val toastPublisher: ToastPublisher = mockk(relaxed = true)
		// TODO: Implement separate TextValidator based on screen purpose
		val textValidator: TextValidator = TextValidator.build {
			allowEmpty = false
			allowBlank = false
			maxLength = 20
		}
		val tested by lazy { ProfileViewModel(authRepository, toastPublisher, textValidator) }

		beforeSpec {
			coEvery { authRepository.userProfile() } just awaits
		}

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

		should("validate text WHEN username changed") {
			tested.onUsernameChange("MyUserName1234")

			tested.usernameErrorMessageId shouldBe ValidationStatus.ValidText.errorMessageId
		}
	},
)

data class ManageProfileParam(
	val profileMethod: suspend () -> Result<Profile>,
	val action: () -> Unit,
)
