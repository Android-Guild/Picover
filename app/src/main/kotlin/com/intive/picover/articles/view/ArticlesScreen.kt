package com.intive.picover.articles.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.intive.picover.articles.viewmodel.ArticlesViewModel
import com.intive.picover.common.state.DefaultStateDispatcher

@Composable
fun ArticlesScreen(viewModel: ArticlesViewModel) {
	val state by viewModel.state
	DefaultStateDispatcher(state) {
		ArticleThumbNails(it)
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
