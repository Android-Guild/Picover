package com.intive.picover.parties.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.intive.picover.parties.repository.MockedPartiesRepository
import com.intive.picover.parties.state.PartiesState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PartiesViewModel @Inject constructor(
	private val partiesRepository: MockedPartiesRepository,
) : ViewModel() {

	val parties: MutableState<PartiesState> = mutableStateOf(PartiesState.Loading)

	init {
		loadParties()
	}

	private fun loadParties() {
		parties.value = PartiesState.Loaded(partiesRepository.getParties())
	}
}
