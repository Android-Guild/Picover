package com.intive.picover.parties.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.intive.picover.common.loader.PicoverLoader
import com.intive.picover.main.theme.Typography
import com.intive.picover.parties.model.Party
import com.intive.picover.parties.viewmodel.PartiesViewModel

@Composable
fun PartiesScreen(
	viewModel: PartiesViewModel,
	navController: NavHostController,
) {
	val state by viewModel.state
	when {
		state.isLoading() -> PicoverLoader()
		state.isLoaded() -> LoadedContent(
			parties = state.data(),
			openDetails = {
				navController.navigate("partyDetails/$it")
			},
		)

		state.isError() -> {
			// TODO: Will be implemented in #133
		}
	}
}

@Composable
private fun LoadedContent(parties: List<Party>, openDetails: (Int) -> Unit) {
	LazyColumn(
		modifier = Modifier.padding(16.dp),
		verticalArrangement = Arrangement.spacedBy(16.dp),
	) {
		items(parties) {
			PartyTile(
				party = it,
				openDetails = openDetails,
			)
		}
	}
}

@Composable
private fun PartyTile(
	party: Party,
	openDetails: (Int) -> Unit,
) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.clickable {
				openDetails(party.id)
			},
	) {
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
}

@Preview(showBackground = true)
@Composable
private fun PartyScreenLoadedPreview() {
	val parties = (1..5).map {
		Party(id = it, title = "title$it", description = "description$it")
	}
	LoadedContent(parties = parties, openDetails = {})
}
