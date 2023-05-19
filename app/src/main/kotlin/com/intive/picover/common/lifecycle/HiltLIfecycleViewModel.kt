package com.intive.picover.common.lifecycle

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import com.intive.picover.common.viewmodel.LifecycleViewModel

@Composable
inline fun <reified VM : LifecycleViewModel> hiltLifecycleViewModel() =
	hiltViewModel<VM>().also {
		LocalLifecycleOwner.current.lifecycle.addObserver(it)
	}
