package com.intive.picover.common

import com.intive.picover.common.viewmodel.state.ViewModelState
import io.kotest.inspectors.forAll
import io.mockk.MockKStubScope

fun <T, D> testState(
	loadingAnswer: MockKStubScope<T, T>.() -> Unit,
	errorAnswer: MockKStubScope<T, T>.() -> Unit,
	loadedAnswer: MockKStubScope<T, T>.() -> Unit,
	loadedData: D,
	testBlock: (Pair<ViewModelState<D>, MockKStubScope<T, T>.() -> Unit>) -> Unit,
) {
	listOf(
		ViewModelState.Loading to loadingAnswer,
		ViewModelState.Error to errorAnswer,
		ViewModelState.Loaded(loadedData) to loadedAnswer,
	).forAll {
		testBlock(it)
	}
}
