package com.intive.picover.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class MVIViewModel<S : Any, E : Any, SE : Any>(initialState: S) : ViewModel() {

	@Suppress("ktlint:standard:property-naming")
	protected val _state = MutableStateFlow(initialState)
	val state = _state.asStateFlow()
	private val _sideEffects = MutableSharedFlow<SE>()
	val sideEffects: Flow<SE> = _sideEffects

	abstract fun event(event: E)

	fun effect(sideEffect: SE) {
		viewModelScope.launch {
			_sideEffects.emit(sideEffect)
		}
	}
}
