package com.intive.picover.common.coroutines.dispatcher

import javax.inject.Qualifier

annotation class Dispatcher {

	@Qualifier
	@Retention(AnnotationRetention.SOURCE)
	@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
	annotation class IO
}
