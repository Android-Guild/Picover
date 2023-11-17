package com.intive.picover.parties.model

sealed class PartiesSideEffect {
	data class NavigateToPartyDetails(val partyId: String) : PartiesSideEffect()
	data object NavigateToAddParty : PartiesSideEffect()
}
