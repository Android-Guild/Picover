package com.intive.picover.main.viewmodel

import androidx.lifecycle.ViewModel
import com.intive.picover.auth.repository.AuthRepository
import com.intive.picover.main.viewmodel.state.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.map

@HiltViewModel
class MainViewModel @Inject constructor(
	authRepository: AuthRepository,
) : ViewModel() {

	val state = authRepository.observeEvents()
		.map { MainState.fromAuthEvent(it) }
}
