package com.intive.picover.parties.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.intive.picover.common.coroutines.CoroutineTestExtension
import com.intive.picover.common.viewmodel.state.ViewModelState.Error
import com.intive.picover.common.viewmodel.state.ViewModelState.Loaded
import com.intive.picover.common.viewmodel.state.ViewModelState.Loading
import com.intive.picover.parties.model.Party
import com.intive.picover.parties.model.toUI
import com.intive.picover.shared.party.data.model.PartyRemote
import com.intive.picover.shared.party.data.repo.PartiesRepository
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class PartyDetailsViewModelTest : ShouldSpec(
	{
		extension(CoroutineTestExtension())

		val partyId = "2"
		val partyRemote: PartyRemote = mockk()
		val party: Party = mockk()
		val repository: PartiesRepository = mockk()
		val savedStateHandle = SavedStateHandle(mapOf("partyId" to partyId))
		lateinit var tested: PartyDetailsViewModel

		beforeSpec {
			mockkStatic(PartyRemote::toUI)
			every { partyRemote.toUI() } returns party
		}

		afterSpec {
			unmockkAll()
		}

		should("set state WHEN initialized according to fetch party by id result") {
			listOf(
				emptyFlow<PartyRemote>() to Loading,
				flowOf(partyRemote) to Loaded(party),
				flow<PartyRemote> { throw Exception() } to Error,
			).forAll { (result, state) ->
				every { repository.partyById(partyId) } returns result

				tested = PartyDetailsViewModel(savedStateHandle, repository)

				tested.state.value shouldBe state
			}
		}
	},
)
