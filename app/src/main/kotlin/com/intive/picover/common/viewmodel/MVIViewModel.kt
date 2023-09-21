package com.intive.picover.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intive.picover.common.viewmodel.event.Event
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

abstract class MVIViewModel<T : Event> : ViewModel() {

	private val event: MutableSharedFlow<T> = MutableSharedFlow()

	init {
		subscribeToEvents()
	}

	open fun handleEvents(event: T) {}

	fun emitEvent(newEvent: T) {
		viewModelScope.launch {
			event.emit(newEvent)
		}
	}

	private fun subscribeToEvents() {
		viewModelScope.launch {
			event.collect {
				handleEvents(it)
			}
		}
	}
}
