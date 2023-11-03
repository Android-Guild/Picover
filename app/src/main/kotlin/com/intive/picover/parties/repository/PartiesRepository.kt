package com.intive.picover.parties.repository

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.snapshots
import com.intive.picover.parties.model.PartyRemote
import javax.inject.Inject
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class PartiesRepository @Inject constructor(
	firebaseDatabase: FirebaseDatabase,
) {

	private val partiesReference =
		firebaseDatabase.getReference("Parties")

	fun parties() =
		partiesReference.snapshots.map { snapshot ->
			snapshot.children.map { childSnapshot ->
				childSnapshot.getValue(PartyRemote::class.java)!!
			}
		}

	suspend fun addParty(partyRemote: PartyRemote) =
		runCatching {
			partiesReference.push().let { databaseReference ->
				databaseReference.key?.let { key ->
					databaseReference.setValue(partyRemote.copy(id = key)).await()
				} ?: throw Exception("Firebase returns key as null")
			}
		}

	fun partyById(id: String) =
		parties().map { parties ->
			parties.first { it.id == id }
		}
}
