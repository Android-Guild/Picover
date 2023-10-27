package com.intive.picover.main.navigation.view

import android.app.Activity
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.intive.picover.main.navigation.model.NavigationItem

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalMaterialNavigationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(activity: Activity) {
	val windowSize = calculateWindowSizeClass(activity)
	val bottomSheetNavigator = rememberBottomSheetNavigator()
	val navController = rememberNavController(bottomSheetNavigator)
	if (windowSize.widthSizeClass == WindowWidthSizeClass.Compact) {
		BottomBarNavigation(
			navController = navController,
			bottomSheetNavigator = bottomSheetNavigator,
		)
	} else {
		RailNavigation(
			navController = navController,
			bottomSheetNavigator = bottomSheetNavigator,
		)
	}
}

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
private fun BottomBarNavigation(
	navController: NavHostController,
	bottomSheetNavigator: BottomSheetNavigator,
) {
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
			bottomSheetNavigator = bottomSheetNavigator,
		)
	}
}

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
private fun RailNavigation(
	navController: NavHostController,
	bottomSheetNavigator: BottomSheetNavigator,
) {
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
				bottomSheetNavigator = bottomSheetNavigator,
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
