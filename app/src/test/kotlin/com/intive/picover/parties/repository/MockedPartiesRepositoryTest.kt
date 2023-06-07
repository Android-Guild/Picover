package com.intive.picover.parties.repository

import com.intive.picover.parties.model.Party
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class MockedPartiesRepositoryTest : ShouldSpec(
	{

		val parties = listOf(
			Party(id = 1, title = "title1", description = "description1"),
			Party(id = 2, title = "title2", description = "description2"),
			Party(id = 3, title = "title3", description = "description3"),
			Party(id = 4, title = "title4", description = "description4"),
			Party(id = 5, title = "title5", description = "description5"),
		)
		val tested = MockedPartiesRepository()

		should("return all parties WHEN getParties called") {
			tested.getParties() shouldBe parties
		}

		should("return specified party WHEN getPartyById called") {
			tested.getPartyById(3) shouldBe parties[2]
		}
	},
)
