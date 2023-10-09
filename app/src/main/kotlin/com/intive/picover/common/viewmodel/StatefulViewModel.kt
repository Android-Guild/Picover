package com.intive.picover.common.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.intive.picover.common.viewmodel.state.ViewModelState

abstract class StatefulViewModel<T> constructor(initialState: ViewModelState<T> = ViewModelState.Loading) : ViewModel() {

	@Suppress("ktlint:standard:property-naming")
	protected val _state = mutableStateOf(initialState)
	val state: State<ViewModelState<T>> = _state
}
