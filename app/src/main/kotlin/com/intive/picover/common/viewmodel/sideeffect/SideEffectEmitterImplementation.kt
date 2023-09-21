package com.intive.picover.common.viewmodel.sideeffect

import kotlinx.coroutines.flow.MutableSharedFlow

class SideEffectEmitterImplementation<T : SideEffect> : SideEffectEmitter<T> {

	override val sideEffect: MutableSharedFlow<T> = MutableSharedFlow()

	override suspend fun setEffect(newSideEffect: T) {
		sideEffect.emit(newSideEffect)
	}
}
