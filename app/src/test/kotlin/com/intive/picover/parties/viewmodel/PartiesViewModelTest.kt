package com.intive.picover.parties.viewmodel

import com.intive.picover.common.coroutines.CoroutineTestExtension
import com.intive.picover.common.validator.TextValidator
import com.intive.picover.common.validator.ValidationStatus
import com.intive.picover.common.viewmodel.state.MVIState
import com.intive.picover.parties.model.PartiesEvent
import com.intive.picover.parties.model.PartiesSideEffect
import com.intive.picover.parties.model.PartiesState
import com.intive.picover.parties.model.Party
import com.intive.picover.parties.model.PartyRemote
import com.intive.picover.parties.model.toUI
import com.intive.picover.parties.repository.PartiesRepository
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
class PartiesViewModelTest : ShouldSpec(
	{
		extension(CoroutineTestExtension())

		val dispatcher = UnconfinedTestDispatcher()
		val partiesRemote: List<PartyRemote> = mockk()
		val parties: List<Party> = listOf(mockk())
		val repository: PartiesRepository = mockk()
		val textValidator: TextValidator = mockk()
		lateinit var tested: PartiesViewModel

		beforeSpec {
			mockkStatic(List<PartyRemote>::toUI)
			every { partiesRemote.toUI() } returns parties
		}

		afterSpec {
			unmockkAll()
		}

		should("set state WHEN initialized according to fetch parties result") {
			listOf(
				emptyFlow<List<PartyRemote>>() to PartiesState(type = MVIState.Type.LOADING),
				flowOf(partiesRemote) to PartiesState(parties = parties, type = MVIState.Type.LOADED),
				flow<List<PartyRemote>> { throw Exception() } to PartiesState(type = MVIState.Type.ERROR),
			).forAll { (result, state) ->
				every { repository.parties() } returns result

				tested = PartiesViewModel(
					partiesRepository = repository,
					shortTextValidator = textValidator,
					longTextValidator = textValidator,
				)

				tested.state.value shouldBe state
			}
		}

		should("start loading parties WHEN load parties event is emitted") {
			tested.handleEvent(PartiesEvent.Load)

			coVerify { repository.parties() }
		}

		should("set navigate to party details side effect WHEN onPartyClick called") {
			runBlocking(dispatcher) {
				tested.onPartyClick(1)

				tested.sideEffects.first() shouldBe PartiesSideEffect.NavigateToPartyDetails(1)
			}
		}

		should("set navigate to add party side effect WHEN onAddPartyClick called") {
			runBlocking(dispatcher) {
				tested.onAddPartyClick()

				tested.sideEffects.first() shouldBe PartiesSideEffect.NavigateToAddParty
			}
		}

		should("call validator WHEN validateShortInputText runs") {
			val text = "MyUserName1234"
			every { textValidator.validate(text) } returns ValidationStatus.ValidText

			tested.validateShortText(text)

			verify { textValidator.validate(text) }
		}

		should("call validator WHEN validateLongText runs") {
			val text = "MyUserName1234"
			every { textValidator.validate(text) } returns ValidationStatus.ValidText

			tested.validateLongText(text)

			verify { textValidator.validate(text) }
		}

		should("update the title WHEN newTitle is provided") {
			val title = "new title"

			tested.updateTitle(title)

			tested.state.value.title shouldBe title
		}

		should("update the description WHEN newDescription is provided") {
			val description = "new description"

			tested.updateDescription(description)

			tested.state.value.description shouldBe description
		}
	},
)
