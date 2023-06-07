package com.intive.picover.parties.state

import com.intive.picover.parties.model.Party

sealed class PartyDetailsState {
	object Loading : PartyDetailsState()
	data class Loaded(val party: Party) : PartyDetailsState()
}
