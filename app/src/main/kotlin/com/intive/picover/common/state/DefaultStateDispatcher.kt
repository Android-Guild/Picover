package com.intive.picover.common.state

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.intive.picover.common.error.PicoverGenericError
import com.intive.picover.common.loader.PicoverLoader
import com.intive.picover.common.viewmodel.state.ViewModelState

// TODO add retry handler
@Composable
fun <T> DefaultStateDispatcher(
	state: ViewModelState<T>,
	content: @Composable (T) -> Unit,
) {
	when {
		state.isLoaded() -> content(state.data())
		state.isLoading() -> PicoverLoader(Modifier.fillMaxSize())
		state.isError() -> PicoverGenericError()
	}
}
