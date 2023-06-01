package com.intive.picover.common.di

import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
	@Provides
	fun provideFirebaseStoreInstance() = FirebaseStorage.getInstance()

	@Provides
	fun provideIoDispatcher() = Dispatchers.IO
}
