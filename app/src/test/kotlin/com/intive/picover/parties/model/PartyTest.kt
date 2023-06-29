package com.intive.picover.parties.model

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

internal class PartyTest : ShouldSpec(
	{

		should("map list of remote parties to parties WHEN toUI called") {
			val tested = listOf(
				PartyRemote(
					id = 1,
					title = "title",
					description = "description",
				),
			)

			tested.toUI() shouldBe listOf(
				Party(
					id = 1,
					title = "title",
					description = "description",
				),
			)
		}

		should("map remote party to party WHEN toUI called") {
			val tested = PartyRemote(
				id = 1,
				title = "title",
				description = "description",
			)

			tested.toUI() shouldBe Party(
				id = 1,
				title = "title",
				description = "description",
			)
		}
	},
)
