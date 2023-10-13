package com.intive.picover.parties.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.snapshots
import com.intive.picover.parties.model.PartyRemote
import io.kotest.assertions.throwables.shouldThrowExactly
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

		val databaseReference: DatabaseReference = mockk()
		val firebaseDatabase: FirebaseDatabase = mockk {
			every { getReference("Parties") } returns databaseReference
		}
		val tested = PartiesRepository(firebaseDatabase)

		beforeSpec {
			mockkStatic("com.google.firebase.database.ktx.DatabaseKt")
		}

		afterSpec {
			unmockkAll()
		}

		should("fetch parties") {
			val party: PartyRemote = mockk()
			val dataSnapshot: DataSnapshot = mockk {
				every { children } returns listOf(
					mockk {
						every { getValue(PartyRemote::class.java) } returns party
					},
				)
			}

			every { databaseReference.snapshots } returns flowOf(dataSnapshot)

			tested.parties().first() shouldBe listOf(party)
		}

		should("fetch party by id") {
			val party: PartyRemote = mockk {
				every { id } returns 1
			}
			val dataSnapshot: DataSnapshot = mockk {
				every { children } returns listOf(
					mockk {
						every { getValue(PartyRemote::class.java) } returns party
					},
				)
			}

			every { databaseReference.snapshots } returns flowOf(dataSnapshot)

			tested.partyById(1).first() shouldBe party
		}

		should("throw error WHEN there is no party for given id") {
			val party: PartyRemote = mockk {
				every { id } returns 2
			}
			val dataSnapshot: DataSnapshot = mockk {
				every { children } returns listOf(
					mockk {
						every { getValue(PartyRemote::class.java) } returns party
					},
				)
			}

			every { databaseReference.snapshots } returns flowOf(dataSnapshot)

			shouldThrowExactly<NoSuchElementException> {
				tested.partyById(1).first()
			}
		}
	},
)
