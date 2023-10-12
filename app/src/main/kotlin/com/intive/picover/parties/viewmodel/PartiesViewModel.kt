package com.intive.picover.parties.viewmodel

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
class PartiesViewModel @Inject constructor(
	private val partiesRepository: PartiesRepository,
) : StatefulViewModel<List<Party>>() {

	init {
		loadParties()
	}

	fun loadParties() {
		viewModelScope.launch {
			_state.value = Loading
			partiesRepository.parties()
				.catch {
					_state.value = Error
				}.collect {
					_state.value = Loaded(it.toUI())
				}
		}
	}
}
