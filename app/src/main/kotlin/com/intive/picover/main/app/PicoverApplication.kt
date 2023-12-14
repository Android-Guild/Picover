package com.intive.picover.main.app

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class PicoverApplication : Application(), Configuration.Provider {

	@Inject
	lateinit var workerFactory: HiltWorkerFactory
	override val workManagerConfiguration: Configuration by lazy {
		Configuration.Builder()
			.setWorkerFactory(workerFactory)
			.build()
	}
}
