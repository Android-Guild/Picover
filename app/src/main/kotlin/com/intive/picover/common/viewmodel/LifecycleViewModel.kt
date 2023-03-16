package com.intive.picover.common.viewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel

abstract class LifecycleViewModel : ViewModel(), LifecycleEventObserver {

	private var firstCreate = true

	override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
		when (event) {
			Lifecycle.Event.ON_CREATE -> onCreate()
			Lifecycle.Event.ON_START -> onStart()
			Lifecycle.Event.ON_RESUME -> onResume()
			Lifecycle.Event.ON_PAUSE -> onPause()
			Lifecycle.Event.ON_STOP -> onStop()
			Lifecycle.Event.ON_DESTROY -> onDestroy()
			Lifecycle.Event.ON_ANY -> throw IllegalArgumentException()
		}
	}

	open fun onFirstCreate() {}

	@CallSuper
	open fun onCreate() {
		if (firstCreate) {
			onFirstCreate()
			firstCreate = false
		}
	}

	open fun onStart() {}

	open fun onResume() {}

	open fun onPause() {}

	open fun onStop() {}

	open fun onDestroy() {}
}
