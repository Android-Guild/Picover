package com.intive.picover.profile.delete.model

import com.intive.picover.common.viewmodel.sideeffect.SideEffect

sealed class DeleteAccountSideEffect : SideEffect {
	data object ShowSuccessMessage : DeleteAccountSideEffect()
	data object ShowReAuthenticationNeededMessage : DeleteAccountSideEffect()
}
