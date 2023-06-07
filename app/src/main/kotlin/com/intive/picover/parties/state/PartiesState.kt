package com.intive.picover.parties.state

import com.intive.picover.parties.model.Party

sealed class PartiesState {
	object Loading : PartiesState()
	data class Loaded(val parties: List<Party>) : PartiesState()
}
