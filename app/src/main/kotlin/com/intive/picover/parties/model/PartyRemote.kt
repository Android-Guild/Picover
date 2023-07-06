package com.intive.picover.parties.model

data class PartyRemote(
	val id: Int,
	val title: String,
	val description: String,
) {
	constructor() : this(0, "", "")
}
