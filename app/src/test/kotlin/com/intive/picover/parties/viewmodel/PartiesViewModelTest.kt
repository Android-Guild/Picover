package com.intive.picover.parties.viewmodel

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

class PartiesViewModelTest : ShouldSpec(
	{
		extension(CoroutineTestExtension())

		should("set state WHEN initialized according to fetch parties result") {
			val partiesRemote: List<PartyRemote> = mockk()
			val parties: List<Party> = mockk()
			mockkStatic(List<PartyRemote>::toUI)
			every { partiesRemote.toUI() } returns parties
			testState<Flow<List<PartyRemote>>, List<Party>>(
				loadingAnswer = { returns(emptyFlow()) },
				errorAnswer = { returns(flow { throw Exception() }) },
				loadedAnswer = { returns(flowOf(partiesRemote)) },
				loadedData = parties,
			) { (state, answers) ->
				val repository: PartiesRepository = mockk {
					every { parties() }.answers()
				}

				val tested = PartiesViewModel(repository)

				tested.state.value shouldBe state
			}
		}
	},
)
