package com.intive.picover.parties.viewmodel

import androidx.lifecycle.viewModelScope
import com.intive.picover.common.viewmodel.MVIViewModel
import com.intive.picover.common.viewmodel.sideeffect.SideEffectEmitter
import com.intive.picover.common.viewmodel.sideeffect.SideEffectEmitterImplementation
import com.intive.picover.common.viewmodel.state.MVIState
import com.intive.picover.parties.model.PartiesEvent
import com.intive.picover.parties.model.PartiesSideEffect
import com.intive.picover.parties.model.PartiesState
import com.intive.picover.parties.model.toUI
import com.intive.picover.parties.repository.PartiesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

@HiltViewModel
class PartiesViewModel @Inject constructor(
	private val partiesRepository: PartiesRepository,
) : MVIViewModel<PartiesState, PartiesEvent>(initialState = PartiesState()),
	SideEffectEmitter<PartiesSideEffect> by SideEffectEmitterImplementation() {

	init {
		loadParties()
	}

	override fun handleEvent(event: PartiesEvent) {
		when (event) {
			is PartiesEvent.Load -> loadParties()
		}
	}

	fun onPartyClick(partyId: String) {
		viewModelScope.launch {
			setEffect(PartiesSideEffect.NavigateToPartyDetails(partyId))
		}
	}

	fun onAddPartyClick() {
		viewModelScope.launch {
			setEffect(PartiesSideEffect.NavigateToAddParty)
		}
	}

	fun updateTitle(newTitle: String) {
		_state.update {
			copy(title = newTitle)
		}
	}

	fun updateDescription(newDescription: String) {
		_state.update {
			copy(description = newDescription)
		}
	}

	private fun loadParties() {
		viewModelScope.launch {
			_state.update {
				copy(type = MVIState.Type.LOADING)
			}
			partiesRepository.parties()
				.catch {
					_state.update {
						copy(type = MVIState.Type.ERROR)
					}
				}.collect {
					_state.update {
						copy(
							parties = it.toUI(),
							type = MVIState.Type.LOADED,
						)
					}
				}
		}
	}
}
