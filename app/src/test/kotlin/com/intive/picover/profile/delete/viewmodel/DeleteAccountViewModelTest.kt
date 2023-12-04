package com.intive.picover.profile.delete.viewmodel

import com.intive.picover.auth.model.AccountDeletionResult
import com.intive.picover.auth.repository.AuthRepository
import com.intive.picover.common.coroutines.CoroutineTestExtension
import com.intive.picover.profile.delete.model.DeleteAccountEvent
import com.intive.picover.profile.delete.model.DeleteAccountSideEffect
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteAccountViewModelTest : ShouldSpec(
	{
		extension(CoroutineTestExtension())

		val authRepository: AuthRepository = mockk()
		val tested by lazy { DeleteAccountViewModel(authRepository) }

		should("emit effect depending on result WHEN DeleteAccount event emitted") {

			listOf(
				AccountDeletionResult.Success to DeleteAccountSideEffect.ShowSuccessMessage,
				AccountDeletionResult.ReAuthenticationNeeded to DeleteAccountSideEffect.ShowReAuthenticationNeededMessage,
			).forAll { (deletionResult, sideEffect) ->
				runTest(UnconfinedTestDispatcher()) {
					lateinit var result: DeleteAccountSideEffect
					coEvery { authRepository.deleteAccount() } returns deletionResult
					backgroundScope.launch {
						result = tested.sideEffects.first()
					}

					tested.emitEvent(DeleteAccountEvent.DeleteAccount)

					result shouldBe sideEffect
				}
			}
		}
	},
)
