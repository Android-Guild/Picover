package com.intive.picover.common.coroutines.module

import com.intive.picover.common.coroutines.dispatcher.Dispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
object DispatcherModule {

	@Provides
	@Dispatcher.IO
	fun provideIoDispatcher() =
		Dispatchers.IO
}
