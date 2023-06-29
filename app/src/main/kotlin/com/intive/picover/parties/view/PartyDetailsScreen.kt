package com.intive.picover.parties.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.intive.picover.common.loader.PicoverLoader
import com.intive.picover.main.theme.Typography
import com.intive.picover.parties.model.Party
import com.intive.picover.parties.viewmodel.PartyDetailsViewModel

@Composable
fun PartyDetailsScreen(
	viewModel: PartyDetailsViewModel,
) {
	val state by viewModel.state
	when {
		state.isLoading() -> PicoverLoader()
		state.isLoaded() -> LoadedContent(party = state.data())
		state.isError() -> {
			// TODO: Will be implemented in #133
		}
	}
}

@Composable
private fun LoadedContent(party: Party) {
	Column(modifier = Modifier.padding(16.dp)) {
		Text(
			text = party.title,
			style = Typography.titleMedium,
		)
		Text(
			modifier = Modifier.padding(top = 16.dp),
			text = party.description,
			maxLines = 3,
			overflow = TextOverflow.Ellipsis,
		)
	}
}

@Preview(showBackground = true)
@Composable
private fun PartyDetailsScreenLoadedPreview() {
	LoadedContent(party = Party(id = 1, title = "title1", description = "description1"))
}
