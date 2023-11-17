package com.intive.picover.shared.party.data.repo

import com.intive.picover.shared.party.data.model.PartyRemote
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.QuerySnapshot
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class PartiesRepositoryTest : ShouldSpec(
	{
		val firestore: FirebaseFirestore = mockk()
		val tested = PartiesRepository(firestore)

		should("fetch parties") {
			val query: QuerySnapshot = mockk {
				every { documents } returns listOf(
					mockk {
						every { id } returns "ABC"
						every { get<String>("title") } returns "Party title"
						every { get<String>("description") } returns "Party description"
					},
				)
			}
			every { firestore.collection("parties").snapshots() } returns flowOf(query)

			val result = tested.parties()

			result.first() shouldBe listOf(
				PartyRemote(id = "ABC", title = "Party title", description = "Party description"),
			)
		}

		should("fetch party by id") {
			val document: DocumentSnapshot = mockk {
				every { id } returns "ABC"
				every { get<String>("title") } returns "Party title"
				every { get<String>("description") } returns "Party description"
			}
			every { firestore.document("parties/ABC").snapshots() } returns flowOf(document)

			val result = tested.partyById("ABC")

			result.first() shouldBe PartyRemote(id = "ABC", title = "Party title", description = "Party description")
		}
	},
)
