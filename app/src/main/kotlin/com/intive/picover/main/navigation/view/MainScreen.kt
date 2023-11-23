package com.intive.picover.main.navigation.view

import android.app.Activity
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.intive.picover.main.navigation.model.NavigationItem
import com.intive.picover.main.navigation.model.NavigationItem.Companion.Icon
import com.intive.picover.main.navigation.model.NavigationItem.Companion.Label

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MainScreen(activity: Activity, snackbarHostState: SnackbarHostState) {
	val windowSize = calculateWindowSizeClass(activity)
	NavigationBar(
		snackbarHostState = snackbarHostState,
		isRail = windowSize.widthSizeClass != WindowWidthSizeClass.Compact,
	)
}

@Composable
fun RowScope.NavigationItems(
	navController: NavHostController,
	isRail: Boolean,
) {
	val backStackEntry = navController.currentBackStackEntryAsState()
	NavigationItem.entries.forEach { item ->
		val selected = item.route == backStackEntry.value?.destination?.route
		val onClick = {
			navController.navigateWithSingleTop(item)
		}
		if (isRail) {
			NavigationRailItem(
				selected = selected,
				onClick = onClick,
				icon = { item.Icon() },
				label = { item.Label() },
			)
		} else {
			NavigationBarItem(
				selected = selected,
				onClick = onClick,
				icon = { item.Icon() },
				label = { item.Label() },
			)
		}
	}
}

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
private fun NavigationBar(
	snackbarHostState: SnackbarHostState,
	isRail: Boolean,
) {
	val bottomSheetNavigator = rememberBottomSheetNavigator()
	val navController = rememberNavController(bottomSheetNavigator)
	Scaffold(
		snackbarHost = {
			SnackbarHost(snackbarHostState)
		},
		bottomBar = {
			if (!isRail) {
				NavigationBar(modifier = Modifier.fillMaxWidth()) {
					NavigationItems(navController = navController, isRail = false)
				}
			}
		},
	) { innerPadding ->
		Row {
			if (isRail) {
				NavigationRail {
					this@Row.NavigationItems(navController = navController, isRail = true)
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
