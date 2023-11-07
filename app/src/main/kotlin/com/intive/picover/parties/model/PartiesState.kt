package com.intive.picover.parties.model

import com.intive.picover.common.viewmodel.state.MVIState

data class PartiesState(
	val parties: List<Party> = emptyList(),
	val title: String = "",
	val description: String = "",
	override val type: Type = Type.LOADING,
) : MVIState()
