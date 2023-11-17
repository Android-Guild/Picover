package com.intive.picover.shared.party.data.repo

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.snapshots
import com.intive.picover.shared.party.data.model.PartyRemote
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class PartiesRepositoryTest : ShouldSpec(
	{
		val firestore: FirebaseFirestore = mockk()
		val tested = PartiesRepository(firestore)

		beforeSpec {
			mockkStatic(Query::snapshots)
		}

		afterSpec {
			unmockkAll()
		}

		should("fetch parties") {
			val query: QuerySnapshot = mockk {
				every { documents } returns listOf(
					mockk {
						every { id } returns "ABC"
						every { getString("title") } returns "Party title"
						every { getString("description") } returns "Party description"
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
				every { getString("title") } returns "Party title"
				every { getString("description") } returns "Party description"
			}
			every { firestore.document("parties/ABC").snapshots() } returns flowOf(document)

			val result = tested.partyById("ABC")

			result.first() shouldBe PartyRemote(id = "ABC", title = "Party title", description = "Party description")
		}
	},
)
