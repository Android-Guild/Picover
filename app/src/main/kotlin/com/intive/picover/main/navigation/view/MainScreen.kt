package com.intive.picover.main.navigation.view

import android.app.Activity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
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
			PicoverNavigationBar(navController)
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
		PicoverNavigationRail(
			navController = navController,
			modifier = Modifier.padding(innerPadding),
		)
	}
}
