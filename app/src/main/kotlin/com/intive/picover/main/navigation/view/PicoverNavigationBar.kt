package com.intive.picover.main.navigation.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.intive.picover.main.navigation.model.NavigationItem
import com.intive.picover.profile.view.ProfileDrawerContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PicoverNavigationBar(
	items: List<NavigationItem>,
	navController: NavHostController,
	drawerState: DrawerState,
	modifier: Modifier = Modifier,
	onItemClick: (NavigationItem) -> Unit,
) {
	val backStackEntry = navController.currentBackStackEntryAsState()
	Column {
		ModalNavigationDrawer(
			drawerContent = { ProfileDrawerContent() },
			drawerState = drawerState,
			gesturesEnabled = drawerState.isOpen,
			modifier = modifier.weight(1f),
		) {
			val coroutineScope = rememberCoroutineScope()
			BackHandler(enabled = drawerState.isOpen) {
				coroutineScope.launch { drawerState.close() }
			}
			PicoverNavHost(
				modifier = modifier.weight(1f),
				navController = navController,
				startDestination = NavigationItem.Home.route,
			)
		}
		NavigationBar(modifier = Modifier.fillMaxWidth()) {
			items.forEach { item ->
				NavigationBarItem(
					selected = if (item is NavigationItem.Profile) {
						drawerState.isOpen
					} else {
						item.route == backStackEntry.value?.destination?.route && drawerState.isClosed
					},
					onClick = { onItemClick(item) },
					icon = {
						Icon(
							imageVector = item.icon,
							contentDescription = stringResource(id = item.labelResId),
						)
					},
					label = {
						Text(text = stringResource(id = item.labelResId))
					},
				)
			}
		}
	}
}
