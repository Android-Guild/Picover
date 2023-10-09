package com.intive.picover.articles.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.intive.picover.articles.viewmodel.ArticlesViewModel
import com.intive.picover.common.error.PicoverGenericError
import com.intive.picover.common.loader.PicoverLoader
import com.intive.picover.common.viewmodel.state.ViewModelState

@Composable
fun ArticlesScreen(viewModel: ArticlesViewModel) {
	val state by viewModel.state
	when (state) {
		is ViewModelState.Error -> PicoverGenericError()
		is ViewModelState.Loaded -> ArticleThumbNails(state.data())
		is ViewModelState.Loading -> PicoverLoader(Modifier.fillMaxSize())
	}
}

@Composable
private fun ArticleThumbNails(articles: List<String>) {
	LazyColumn {
		items(articles) {
			// TODO improve UI
			Text(text = it)
		}
	}
}
