package com.intive.picover.parties.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.intive.picover.common.coroutines.CoroutineTestExtension
import com.intive.picover.common.testState
import com.intive.picover.parties.model.Party
import com.intive.picover.parties.model.PartyRemote
import com.intive.picover.parties.model.toUI
import com.intive.picover.parties.repository.PartiesRepository
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class PartyDetailsViewModelTest : ShouldSpec(
	{
		extension(CoroutineTestExtension())

		should("set state WHEN initialized according to fetch party by id result") {
			val partyId = 2
			val savedStateHandle = SavedStateHandle(mapOf("partyId" to partyId))
			val partyRemote: PartyRemote = mockk()
			val party: Party = mockk()
			mockkStatic(PartyRemote::toUI)
			every { partyRemote.toUI() } returns party
			testState<Flow<PartyRemote>, Party>(
				loadingAnswer = { returns(emptyFlow()) },
				errorAnswer = { returns(flow { throw Exception() }) },
				loadedAnswer = { returns(flowOf(partyRemote)) },
				loadedData = party,
			) { (state, answers) ->
				val repository: PartiesRepository = mockk {
					every { partyById(partyId) }.answers()
				}

				val tested = PartyDetailsViewModel(savedStateHandle, repository)

				tested.state.value shouldBe state
			}
		}
	},
)
