package com.intive.picover.common.viewmodel.sideeffect

import kotlinx.coroutines.flow.MutableSharedFlow

class SideEffectEmitterImplementation<T : SideEffect> : SideEffectEmitter<T> {

	override val sideEffects: MutableSharedFlow<T> = MutableSharedFlow()

	override suspend fun setEffect(newSideEffect: T) {
		sideEffects.emit(newSideEffect)
	}
}
