package com.intive.picover.common.viewmodel.state

@Deprecated("Please use MVIStateType instead", replaceWith = ReplaceWith("MVIState.Type"))
sealed class ViewModelState<out T> {
	@Deprecated("Please use MVIStateType.LOADING instead", replaceWith = ReplaceWith("MVIStateType.LOADING"))
	data object Loading : ViewModelState<Nothing>()

	@Deprecated("Please use MVIStateType.ERROR instead", replaceWith = ReplaceWith("MVIStateType.ERROR"))
	data object Error : ViewModelState<Nothing>()

	@Deprecated("Please use MVIStateType.LOADED instead", replaceWith = ReplaceWith("MVIStateType.LOADED"))
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
