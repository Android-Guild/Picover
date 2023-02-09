package com.example.android.picover.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun PicoverNavigationBar(
	items: List<NavigationItem>,
	navController: NavHostController,
	modifier: Modifier = Modifier,
	onItemClick: (NavigationItem) -> Unit
) {
	val backStackEntry = navController.currentBackStackEntryAsState()

	Column {
		PicoverNavHost(
			modifier = modifier.weight(1f),
			navController = navController,
			startDestination = NavigationItem.Home.route
		)
		NavigationBar(modifier = Modifier.fillMaxWidth()) {
			items.forEach { item ->
				NavigationBarItem(
					selected = item.route == backStackEntry.value?.destination?.route,
					onClick = { onItemClick(item) },
					icon = {
						Icon(
							imageVector = item.icon,
							contentDescription = stringResource(id = item.labelResId)
						)
					},
					label = {
						Text(text = stringResource(id = item.labelResId))
					}
				)
			}
		}
	}
}
