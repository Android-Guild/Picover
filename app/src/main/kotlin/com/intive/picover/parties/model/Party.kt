package com.intive.picover.parties.model

import com.intive.picover.shared.party.data.model.PartyRemote

data class Party(
	val id: String,
	val title: String,
	val description: String,
)

fun List<PartyRemote>.toUI() =
	map { it.toUI() }

fun PartyRemote.toUI() =
	Party(
		id = id,
		title = title,
		description = description,
	)
