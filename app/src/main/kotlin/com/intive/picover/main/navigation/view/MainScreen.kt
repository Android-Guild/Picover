package com.intive.picover.main.navigation.view

import android.app.Activity
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.intive.picover.main.navigation.model.NavigationItem
import com.intive.picover.main.navigation.model.NavigationType

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MainScreen(activity: Activity) {
	val windowSize = calculateWindowSizeClass(activity)
	val navController = rememberNavController()
	when (NavigationType.fromWindowSize(windowSize)) {
		NavigationType.BOTTOM_BAR -> BottomBarNavigation(navController)
		NavigationType.RAIL -> RailNavigation(navController)
	}
}

@Composable
private fun BottomBarNavigation(navController: NavHostController) {
	Scaffold(
		bottomBar = {
			NavigationBar(modifier = Modifier.fillMaxWidth()) {
				val backStackEntry = navController.currentBackStackEntryAsState()
				NavigationItem.entries.forEach { item ->
					NavigationBarItem(
						selected = item.route == backStackEntry.value?.destination?.route,
						onClick = { navController.navigateWithSingleTop(item) },
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
		},
	) { innerPadding ->
		PicoverNavHost(
			modifier = Modifier.padding(innerPadding),
			navController = navController,
		)
	}
}

@Composable
private fun RailNavigation(navController: NavHostController) {
	Scaffold { innerPadding ->
		Row {
			NavigationRail {
				val backStackEntry = navController.currentBackStackEntryAsState()
				NavigationItem.entries.forEach { item ->
					NavigationRailItem(
						selected = item.route == backStackEntry.value?.destination?.route,
						onClick = { navController.navigateWithSingleTop(item) },
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
			PicoverNavHost(
				modifier = Modifier.padding(innerPadding),
				navController = navController,
			)
		}
	}
}

private fun NavHostController.navigateWithSingleTop(item: NavigationItem) {
	navigate(item.route) {
		popUpTo(graph.findStartDestination().id)
		launchSingleTop = true
	}
}
