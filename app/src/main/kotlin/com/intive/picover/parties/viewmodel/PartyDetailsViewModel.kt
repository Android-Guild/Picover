package com.intive.picover.parties.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intive.picover.common.viewmodel.state.ViewModelState
import com.intive.picover.common.viewmodel.state.ViewModelState.Error
import com.intive.picover.common.viewmodel.state.ViewModelState.Loaded
import com.intive.picover.common.viewmodel.state.ViewModelState.Loading
import com.intive.picover.parties.model.Party
import com.intive.picover.parties.model.toUI
import com.intive.picover.parties.repository.MockedPartiesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

@HiltViewModel
class PartyDetailsViewModel @Inject constructor(
	savedStateHandle: SavedStateHandle,
	private val partiesRepository: MockedPartiesRepository,
) : ViewModel() {

	val state: MutableState<ViewModelState<Party>> = mutableStateOf(Loading)
	private val partyId: Int = savedStateHandle["partyId"]!!

	init {
		loadParty(partyId)
	}

	private fun loadParty(id: Int) {
		viewModelScope.launch {
			partiesRepository.partyById(id)
				.catch {
					state.value = Error
				}.collect {
					state.value = Loaded(it.toUI())
				}
		}
	}
}
