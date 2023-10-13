package com.intive.picover.common.viewmodel.sideeffect

import kotlinx.coroutines.flow.MutableSharedFlow

interface SideEffectEmitter<T : SideEffect> {

	val sideEffects: MutableSharedFlow<T>

	suspend fun setEffect(newSideEffect: T)
}
