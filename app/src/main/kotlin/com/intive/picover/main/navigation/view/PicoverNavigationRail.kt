package com.intive.picover.main.navigation.view

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.intive.picover.main.navigation.model.NavigationItem

@Composable
fun PicoverNavigationRail(
    items: List<NavigationItem>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onItemClick: (NavigationItem) -> Unit
) {
	val backStackEntry = navController.currentBackStackEntryAsState()

	Row {
		NavigationRail {
			items.forEach { item ->
				NavigationRailItem(
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
		PicoverNavHost(
			modifier = modifier,
			navController = navController,
			startDestination = NavigationItem.Home.route
		)
	}
}
