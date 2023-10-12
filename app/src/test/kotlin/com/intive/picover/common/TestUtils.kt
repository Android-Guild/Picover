package com.intive.picover.common

import io.mockk.MockKStubScope

fun <T> mockkAnswer(block: MockKStubScope<T, T>.() -> Unit) =
	fun MockKStubScope<T, T>.() {
		block()
	}
