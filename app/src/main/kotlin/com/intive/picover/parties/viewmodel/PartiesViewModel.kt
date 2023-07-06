package com.intive.picover.parties.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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
class PartiesViewModel @Inject constructor(
	private val partiesRepository: MockedPartiesRepository,
) : ViewModel() {

	val state: MutableState<ViewModelState<List<Party>>> = mutableStateOf(Loading)

	init {
		loadParties()
	}

	private fun loadParties() {
		viewModelScope.launch {
			partiesRepository.parties()
				.catch {
					state.value = Error
				}.collect {
					state.value = Loaded(it.toUI())
				}
		}
	}
}
