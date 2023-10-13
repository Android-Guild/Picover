package com.intive.picover.parties.model

import com.intive.picover.common.viewmodel.event.Event

sealed class PartiesEvent : Event {
	data object Load : PartiesEvent()
}
