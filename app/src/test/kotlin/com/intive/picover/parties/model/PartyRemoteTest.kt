package com.intive.picover.parties.model

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

internal class PartyRemoteTest : ShouldSpec(
	{

		should("map party to remote party WHEN toRemote is called") {
			val tested = Party(
				id = "1",
				title = "title",
				description = "description",
			)

			tested.toRemote() shouldBe PartyRemote(
				id = "1",
				title = "title",
				description = "description",
			)
		}
	},
)
