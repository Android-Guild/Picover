package com.intive.picover.parties.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import com.intive.picover.parties.model.PartyRemote
import javax.inject.Inject
import kotlinx.coroutines.flow.map

class PartiesRepository @Inject constructor(
	private val firestore: FirebaseFirestore,
) {

	fun parties() =
		firestore.collection("parties")
			.snapshots()
			.map { snapshot ->
				snapshot.documents.map { it.toParty() }
			}

	fun partyById(id: String) =
		firestore.document("parties/$id")
			.snapshots()
			.map { it.toParty() }

	private fun DocumentSnapshot.toParty() =
		PartyRemote(
			id,
			getString("title")!!,
			getString("description")!!,
		)
}
