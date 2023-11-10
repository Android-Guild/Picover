package com.intive.picover.parties.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.intive.picover.common.viewmodel.StatefulViewModel
import com.intive.picover.common.viewmodel.state.ViewModelState.Error
import com.intive.picover.common.viewmodel.state.ViewModelState.Loaded
import com.intive.picover.common.viewmodel.state.ViewModelState.Loading
import com.intive.picover.parties.model.Party
import com.intive.picover.parties.model.toUI
import com.intive.picover.parties.repository.PartiesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

@HiltViewModel
class PartyDetailsViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle,
	private val partiesRepository: PartiesRepository,
) : StatefulViewModel<Party>() {

	private val partyId: String = savedStateHandle["partyId"]!!

	init {
		loadParty()
	}

	fun loadParty() {
		viewModelScope.launch {
			_state.value = Loading
			partiesRepository.partyById(partyId)
				.catch {
					_state.value = Error
				}.collect {
					_state.value = Loaded(it.toUI())
				}
		}
	}
}
