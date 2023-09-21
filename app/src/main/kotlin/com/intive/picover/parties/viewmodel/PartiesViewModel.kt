package com.intive.picover.parties.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.intive.picover.common.viewmodel.MVIViewModel
import com.intive.picover.common.viewmodel.sideeffect.SideEffectEmitter
import com.intive.picover.common.viewmodel.sideeffect.SideEffectEmitterImplementation
import com.intive.picover.common.viewmodel.state.ViewModelState
import com.intive.picover.common.viewmodel.state.ViewModelState.Error
import com.intive.picover.common.viewmodel.state.ViewModelState.Loaded
import com.intive.picover.common.viewmodel.state.ViewModelState.Loading
import com.intive.picover.parties.model.PartiesEvent
import com.intive.picover.parties.model.PartiesSideEffect
import com.intive.picover.parties.model.Party
import com.intive.picover.parties.model.toUI
import com.intive.picover.parties.repository.PartiesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

@HiltViewModel
class PartiesViewModel @Inject constructor(
	private val partiesRepository: PartiesRepository,
) : MVIViewModel<PartiesEvent>(),
	SideEffectEmitter<PartiesSideEffect> by SideEffectEmitterImplementation() {

	val state: MutableState<ViewModelState<List<Party>>> = mutableStateOf(Loading)

	init {
		loadParties()
	}

	override fun handleEvents(event: PartiesEvent) {
		when (event) {
			is PartiesEvent.Load -> loadParties()
		}
	}

	fun onPartyClick(partyId: Int) {
		viewModelScope.launch {
			setEffect(PartiesSideEffect.NavigateToPartyDetails(partyId))
		}
	}

	private fun loadParties() {
		viewModelScope.launch {
			state.value = Loading
			partiesRepository.parties()
				.catch {
					state.value = Error
				}.collect {
					state.value = Loaded(it.toUI())
				}
		}
	}
}
