package com.intive.picover.profile.di

import com.intive.picover.common.validator.TextValidator
import com.intive.picover.common.validator.qualifier.Validator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ProfileModule {

	@Provides
	@Validator.Profile
	fun provideProfileTextValidator() =
		TextValidator.Builder()
			.allowEmpty(false)
			.allowBlank(false)
			.maxLength(20)
			.build()
}
