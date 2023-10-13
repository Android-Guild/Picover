package com.intive.picover.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intive.picover.common.viewmodel.event.Event
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

abstract class MVIViewModel<T : Event> : ViewModel() {

	private val events: MutableSharedFlow<T> = MutableSharedFlow()

	init {
		collectEvents()
	}

	open fun handleEvent(event: T) {}

	fun emitEvent(newEvent: T) {
		viewModelScope.launch {
			events.emit(newEvent)
		}
	}

	private fun collectEvents() {
		viewModelScope.launch {
			events.collect {
				handleEvent(it)
			}
		}
	}
}
