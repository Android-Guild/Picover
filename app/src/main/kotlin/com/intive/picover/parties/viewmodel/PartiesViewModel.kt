package com.intive.picover.parties.viewmodel

import androidx.lifecycle.viewModelScope
import com.intive.picover.common.viewmodel.MVIViewModel
import com.intive.picover.common.viewmodel.state.MVIStateType
import com.intive.picover.parties.model.PartiesEvent
import com.intive.picover.parties.model.PartiesSideEffect
import com.intive.picover.parties.model.PartiesState
import com.intive.picover.parties.model.toUI
import com.intive.picover.shared.party.data.repo.PartiesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class PartiesViewModel @Inject constructor(
	private val partiesRepository: PartiesRepository,
) : MVIViewModel<PartiesState, PartiesEvent, PartiesSideEffect>(initialState = PartiesState()) {

	init {
		loadParties()
	}

	override fun event(event: PartiesEvent) {
		when (event) {
			is PartiesEvent.Load -> loadParties()
		}
	}

	fun updateTitle(newTitle: String) {
		_state.update {
			it.copy(title = newTitle)
		}
	}

	fun updateDescription(newDescription: String) {
		_state.update {
			it.copy(description = newDescription)
		}
	}

	private fun loadParties() {
		viewModelScope.launch {
			_state.update {
				it.copy(type = MVIStateType.LOADING)
			}
			partiesRepository.parties()
				.catch {
					_state.update {
						it.copy(type = MVIStateType.ERROR)
					}
				}.collect { parties ->
					_state.update {
						it.copy(
							parties = parties.toUI(),
							type = MVIStateType.LOADED,
						)
					}
				}
		}
	}
}
