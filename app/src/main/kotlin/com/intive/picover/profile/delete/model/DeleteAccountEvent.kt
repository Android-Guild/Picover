package com.intive.picover.profile.delete.model

import com.intive.picover.common.viewmodel.event.Event

sealed class DeleteAccountEvent : Event {
	data object DeleteAccount : DeleteAccountEvent()
}
