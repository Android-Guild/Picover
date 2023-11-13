package com.intive.picover.common.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.intive.picover.common.viewmodel.event.Event
import com.intive.picover.common.viewmodel.sideeffect.SideEffect
import com.intive.picover.common.viewmodel.state.MVIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

abstract class MVIViewModel<S : MVIState, E : Event, SE : SideEffect>(initialState: S) : ViewModel() {

	@Suppress("ktlint:standard:property-naming")
	protected val _state = mutableStateOf(initialState)
	val state: State<S> = _state
	private val _sideEffects = MutableSharedFlow<SE>()
	val sideEffects: Flow<SE> = _sideEffects
	private val events = MutableSharedFlow<E>()

	init {
		collectEvents()
	}

	protected fun MutableState<S>.update(copyBlock: S.() -> S) {
		_state.value = value.copyBlock()
	}

	protected abstract fun handleEvent(event: E)

	fun emitEvent(newEvent: E) {
		viewModelScope.launch {
			events.emit(newEvent)
		}
	}

	fun emitEffect(sideEffect: SE) {
		viewModelScope.launch {
			_sideEffects.emit(sideEffect)
		}
	}

	private fun collectEvents() {
		viewModelScope.launch {
			events.collect(::handleEvent)
		}
	}
}
