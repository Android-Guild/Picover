package com.intive.picover.main.navigation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.intive.picover.main.navigation.model.NavigationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PicoverNavigationDrawer(
	items: List<NavigationItem>,
	navController: NavHostController,
	modifier: Modifier = Modifier,
	onItemClick: (NavigationItem) -> Unit,
) {
	val backStackEntry = navController.currentBackStackEntryAsState()

	PermanentNavigationDrawer(
		drawerContent = {
			PermanentDrawerSheet(
				modifier = modifier.width(240.dp),
			) {
				Column(
					modifier = Modifier
						.wrapContentWidth()
						.fillMaxHeight()
						.background(MaterialTheme.colorScheme.inverseOnSurface)
						.padding(12.dp),
				) {
					items.forEach { item ->
						NavigationDrawerItem(
							label = {
								Text(
									text = stringResource(id = item.labelResId),
									modifier = Modifier.padding(horizontal = 16.dp),
								)
							},
							selected = item.route == backStackEntry.value?.destination?.route,
							onClick = { onItemClick(item) },
							icon = {
								Icon(
									imageVector = item.icon,
									contentDescription = stringResource(id = item.labelResId),
								)
							},
							colors = NavigationDrawerItemDefaults.colors(
								unselectedContainerColor = Color.Transparent,
							),
						)
					}
				}
			}
		},
	) {
		PicoverNavHost(
			modifier = modifier,
			navController = navController,
			startDestination = NavigationItem.Parties.route,
		)
	}
}
