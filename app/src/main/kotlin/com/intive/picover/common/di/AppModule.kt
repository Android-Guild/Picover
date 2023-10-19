package com.intive.picover.common.di

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.work.WorkManager
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.intive.picover.common.validator.TextValidator
import com.intive.picover.common.validator.qualifier.Validator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

	@Provides
	fun provideFirebaseStorageReference() =
		Firebase.storage.reference

	@Provides
	fun provideFirebaseDatabase() =
		Firebase.database

	@Provides
	@Validator.ShortText
	fun provideShortTextValidator() =
		TextValidator.build {
			allowEmpty = false
			allowBlank = false
			maxLength = 20
		}

	@Provides
	@Validator.LongText
	fun provideLongTextValidator() =
		TextValidator.build {
			allowEmpty = false
			allowBlank = false
			maxLength = 80
		}

	@Provides
	fun provideWorkManager(@ApplicationContext context: Context) =
		WorkManager.getInstance(context)

	@Provides
	@Singleton
	fun provideSnackbarHost() =
		SnackbarHostState()
}
