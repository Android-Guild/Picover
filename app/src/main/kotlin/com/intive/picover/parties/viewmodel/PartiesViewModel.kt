package com.intive.picover.parties.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.intive.picover.common.validator.TextValidator
import com.intive.picover.common.validator.qualifier.Validator
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
	@Validator.ShortText private val shortTextValidator: TextValidator,
	@Validator.LongText private val longTextValidator: TextValidator,
) : MVIViewModel<PartiesEvent>(),
	SideEffectEmitter<PartiesSideEffect> by SideEffectEmitterImplementation() {

	val state: MutableState<ViewModelState<List<Party>>> = mutableStateOf(Loading)
	private val _title = mutableStateOf("")
	val title: String by _title
	private val _description = mutableStateOf("")
	val description: String by _description

	init {
		loadParties()
	}

	override fun handleEvent(event: PartiesEvent) {
		when (event) {
			is PartiesEvent.Load -> loadParties()
		}
	}

	fun onPartyClick(partyId: Int) {
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
		_title.value = newTitle
	}

	fun updateDescription(newDescription: String) {
		_description.value = newDescription
	}

	fun validateShortText(text: String) = shortTextValidator.validate(text)

	fun validateLongText(text: String) = longTextValidator.validate(text)

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
