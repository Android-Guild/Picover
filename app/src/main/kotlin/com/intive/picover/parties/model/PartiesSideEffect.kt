package com.intive.picover.parties.model

import com.intive.picover.common.viewmodel.sideeffect.SideEffect

sealed class PartiesSideEffect : SideEffect {
	data class NavigateToPartyDetails(val partyId: Int) : PartiesSideEffect()
}
