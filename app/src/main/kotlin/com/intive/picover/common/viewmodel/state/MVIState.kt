package com.intive.picover.common.viewmodel.state

abstract class MVIState {
	abstract val type: Type

	enum class Type {
		LOADING,
		ERROR,
		LOADED,
	}
}
