package com.intive.picover.common.viewmodel.state

sealed class ViewModelState<out T> {
	data object Loading : ViewModelState<Nothing>()
	data object Error : ViewModelState<Nothing>()
	data class Loaded<T>(val data: T) : ViewModelState<T>()

	fun isLoading() =
		this is Loading

	fun isError() =
		this is Error

	fun isLoaded() =
		this is Loaded

	fun data() =
		(this as Loaded).data
}
