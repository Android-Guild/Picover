package com.intive.picover.parties.model

sealed class PartiesEvent {
	data object Load : PartiesEvent()
}
