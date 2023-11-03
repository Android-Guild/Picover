package com.intive.picover.parties.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.snapshots
import com.intive.picover.common.coroutines.CoroutineTestExtension
import com.intive.picover.parties.model.PartyRemote
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.result.shouldBeFailure
import io.kotest.matchers.result.shouldBeSuccess
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.tasks.await

class PartiesRepositoryTest : ShouldSpec(
	{
		extension(CoroutineTestExtension())
		isolationMode = IsolationMode.InstancePerTest

		val databaseReference: DatabaseReference = mockk()
		val firebaseDatabase: FirebaseDatabase = mockk {
			every { getReference("Parties") } returns databaseReference
		}
		val tested = PartiesRepository(firebaseDatabase)
		val newKey = "newKey"
		val partyRemote: PartyRemote = mockk()
		val newPartyRemote: PartyRemote = mockk()

		beforeSpec {
			mockkStatic(Query::snapshots)
			mockkStatic("kotlinx.coroutines.tasks.TasksKt")
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
				every { id } returns "1"
			}
			val dataSnapshot: DataSnapshot = mockk {
				every { children } returns listOf(
					mockk {
						every { getValue(PartyRemote::class.java) } returns party
					},
				)
			}
			every { databaseReference.snapshots } returns flowOf(dataSnapshot)

			tested.partyById("1").first() shouldBe party
		}

		should("throw error WHEN there is no party for given id") {
			val party: PartyRemote = mockk {
				every { id } returns "2"
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
				tested.partyById("1").first()
			}
		}

		should("add party with correct id") {
			val childDatabaseReference: DatabaseReference = mockk {
				every { key } returns newKey
				coEvery { setValue(newPartyRemote).await() } returns mockk()
			}
			every { databaseReference.push() } returns childDatabaseReference
			every { partyRemote.copy(newKey) } returns newPartyRemote

			val result = tested.addParty(partyRemote)

			coVerify {
				childDatabaseReference.setValue(newPartyRemote).await()
			}
			result.shouldBeSuccess()
		}

		should("throw an exception when setValue fails") {
			val errorMessage = "An exception"
			every { databaseReference.push() } throws Exception(errorMessage)

			val result = tested.addParty(partyRemote)

			assertSoftly(result) {
				shouldBeFailure()
				exceptionOrNull()?.message shouldBe errorMessage
			}
		}
	},
)
