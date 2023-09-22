package com.intive.picover.common.di

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.intive.picover.common.validator.qualifier.Validator
import com.intive.picover.common.validator.textValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

	@Provides
	fun provideFirebaseStorageReference() =
		FirebaseStorage.getInstance().reference

	@Provides
	fun provideFirebaseDatabase() =
		Firebase.database

	@Provides
	@Validator.ShortText
	fun provideShortTextValidator() =
		textValidator {
			allowEmpty = false
			allowBlank = false
			maxLength = 20
		}

	@Provides
	@Validator.LongText
	fun provideLongTextValidator() =
		textValidator {
			allowEmpty = false
			allowBlank = false
			maxLength = 80
		}
}
