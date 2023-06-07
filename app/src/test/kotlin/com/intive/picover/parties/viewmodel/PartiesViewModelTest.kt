package com.intive.picover.parties.viewmodel

import com.intive.picover.parties.model.Party
import com.intive.picover.parties.repository.MockedPartiesRepository
import com.intive.picover.parties.state.PartiesState
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class PartiesViewModelTest : ShouldSpec(
	{

		val parties: List<Party> = listOf(mockk(), mockk(), mockk())
		val repository: MockedPartiesRepository = mockk()
		val tested by lazy { PartiesViewModel(repository) }

		should("set Loaded state with parties WHEN init called") {
			every { repository.getParties() } returns parties

			tested.parties.value shouldBe PartiesState.Loaded(parties)
		}
	},
)
