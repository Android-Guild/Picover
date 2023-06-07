package com.intive.picover.parties.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.intive.picover.parties.model.Party
import com.intive.picover.parties.repository.MockedPartiesRepository
import com.intive.picover.parties.state.PartyDetailsState
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class PartyDetailsViewModelTest : ShouldSpec(
	{

		val partyId = 2
		val party: Party = mockk()
		val repository: MockedPartiesRepository = mockk()
		val savedStateHandle: SavedStateHandle = mockk {
			every { get<Int>(any()) } returns partyId
		}
		val tested by lazy { PartyDetailsViewModel(savedStateHandle, repository) }

		should("set Loaded state with party WHEN onFirstCreate called") {
			every { repository.getPartyById(partyId) } returns party

			tested.partyDetails.value shouldBe PartyDetailsState.Loaded(party)
		}
	},
)
