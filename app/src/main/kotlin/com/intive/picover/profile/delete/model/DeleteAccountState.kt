package com.intive.picover.profile.delete.model

import com.intive.picover.common.viewmodel.state.MVIState

data class DeleteAccountState(
	override val type: Type = Type.LOADED,
) : MVIState()
