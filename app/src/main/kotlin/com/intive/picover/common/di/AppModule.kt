package com.intive.picover.common.di

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
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
	fun provideFirebaseStorageReference() = FirebaseStorage.getInstance().reference

	@Provides
	fun provideFirebaseDatabase() = Firebase.database

	@Provides
	fun provideIoDispatcher() = Dispatchers.IO
}
