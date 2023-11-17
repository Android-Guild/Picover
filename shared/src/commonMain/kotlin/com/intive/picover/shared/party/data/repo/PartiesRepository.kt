package com.intive.picover.shared.party.data.repo

import com.intive.picover.shared.party.data.model.PartyRemote
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.map

class PartiesRepository(
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
			get("title"),
			get("description"),
		)
}
