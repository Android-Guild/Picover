package com.intive.picover.parties.model

import com.intive.picover.common.annotation.NoArgConstructor

@NoArgConstructor
data class PartyRemote(
	val id: String,
	val title: String,
	val description: String,
)
