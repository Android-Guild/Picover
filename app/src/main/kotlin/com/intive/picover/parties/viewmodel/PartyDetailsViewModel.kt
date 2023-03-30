package com.intive.picover.parties.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.intive.picover.parties.repository.MockedPartiesRepository
import com.intive.picover.parties.state.PartyDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PartyDetailsViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle,
	private val partiesRepository: MockedPartiesRepository,
) : ViewModel() {

	val partyDetails: MutableState<PartyDetailsState> = mutableStateOf(PartyDetailsState.Loading)
	private val partyId: Int = savedStateHandle["partyId"]!!

	init {
		loadParty(partyId)
	}

	private fun loadParty(id: Int) {
		partyDetails.value = PartyDetailsState.Loaded(partiesRepository.getPartyById(id))
	}
}
