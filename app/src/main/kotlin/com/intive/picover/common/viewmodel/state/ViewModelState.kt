package com.intive.picover.common.viewmodel.state

@Deprecated("Please use MVIState.Type instead", replaceWith = ReplaceWith("MVIState.Type"))
sealed class ViewModelState<out T> {
	@Deprecated("Please use MVIState.Type.LOADING instead", replaceWith = ReplaceWith("MVIState.Type.LOADING"))
	data object Loading : ViewModelState<Nothing>()

	@Deprecated("Please use MVIState.Type.ERROR instead", replaceWith = ReplaceWith("MVIState.Type.ERROR"))
	data object Error : ViewModelState<Nothing>()

	@Deprecated("Please use MVIState.Type.LOADED instead", replaceWith = ReplaceWith("MVIState.Type.LOADED"))
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
