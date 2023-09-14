package com.intive.picover.parties.view

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.intive.picover.common.error.PicoverGenericError
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
		state.isLoading() -> PicoverLoader(modifier = Modifier.fillMaxSize())
		state.isLoaded() -> LoadedContent(
			parties = state.data(),
			openDetails = {
				navController.navigate("partyDetails/$it")
			},
		)

		state.isError() -> PicoverGenericError(onRetryClick = { viewModel.loadParties() })
	}
}

@Composable
private fun LoadedContent(parties: List<Party>, openDetails: (Int) -> Unit) {
	val context = LocalContext.current
	Box(Modifier.fillMaxSize()) {
		LazyColumn(
			modifier = Modifier
				.fillMaxSize()
				.padding(16.dp),
			verticalArrangement = Arrangement.spacedBy(16.dp),
		) {
			items(parties) {
				PartyTile(
					party = it,
					openDetails = openDetails,
				)
			}
		}
		FloatingActionButton(
			modifier = Modifier
				.align(Alignment.BottomEnd)
				.padding(12.dp),
			onClick = { Toast.makeText(context, "To be implemented", Toast.LENGTH_SHORT).show() },
			containerColor = MaterialTheme.colorScheme.secondaryContainer,
			contentColor = MaterialTheme.colorScheme.secondary,
		) {
			Icon(Icons.Filled.Add, "null")
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
