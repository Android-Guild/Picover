package com.intive.picover.parties.repository

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.snapshots
import com.intive.picover.parties.model.PartyRemote
import javax.inject.Inject
import kotlinx.coroutines.flow.map

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

	fun partyById(id: String) =
		parties().map { parties ->
			parties.first { it.id == id }
		}
}
