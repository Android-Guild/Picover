package com.intive.picover.common.validator.qualifier

import javax.inject.Qualifier

interface Validator {

	@Qualifier
	@Retention(AnnotationRetention.RUNTIME)
	annotation class Profile
}
