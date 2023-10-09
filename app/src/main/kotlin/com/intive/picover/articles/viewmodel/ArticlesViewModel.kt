package com.intive.picover.articles.viewmodel

import androidx.lifecycle.viewModelScope
import com.intive.picover.articles.repository.ArticlesRepository
import com.intive.picover.common.viewmodel.StatefulViewModel
import com.intive.picover.common.viewmodel.state.ViewModelState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ArticlesViewModel @Inject constructor(
	private val articlesRepository: ArticlesRepository,
) : StatefulViewModel<List<String>>() {

	init {
		viewModelScope.launch {
			articlesRepository.names()
				.onSuccess { _state.value = ViewModelState.Loaded(it) }
				.onFailure { _state.value = ViewModelState.Error }
		}
	}
}
