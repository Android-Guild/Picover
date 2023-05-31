package com.intive.picover.auth.di

import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.intive.picover.auth.intent.builder.SignInIntentBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

	@Provides
	fun provideFirebaseAuth() =
		Firebase.auth

	@Provides
	fun provideSignInIntent() =
		SignInIntentBuilder.build(AuthUI.getInstance())
}
