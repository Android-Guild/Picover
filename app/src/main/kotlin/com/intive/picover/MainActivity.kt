package com.intive.picover

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.intive.picover.presentation.navigation.NavigationItem
import com.intive.picover.presentation.navigation.NavigationType
import com.intive.picover.presentation.navigation.PicoverNavigationBar
import com.intive.picover.presentation.navigation.PicoverNavigationDrawer
import com.intive.picover.presentation.navigation.PicoverNavigationRail
import com.intive.picover.presentation.ui.theme.PicoverTheme

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			PicoverTheme {
				val windowSize = calculateWindowSizeClass(this)
				val navigationType = NavigationType.fromWindowSize(windowSize)
				val navController = rememberNavController()
				val navigationItems = listOf(
					NavigationItem.Home,
					NavigationItem.Camera,
					NavigationItem.Profile
				)

				Scaffold {
					AnimatedVisibility(
						visible = navigationType == NavigationType.NAVIGATION_BAR
					) {
						PicoverNavigationBar(
							items = navigationItems,
							navController = navController,
							onItemClick = {
								navController.navigate(it.route)
							},
							modifier = Modifier.padding(it)
						)
					}
					AnimatedVisibility(
						visible = navigationType == NavigationType.NAVIGATION_RAIL
					) {
						PicoverNavigationRail(
							items = navigationItems,
							navController = navController,
							onItemClick = {
								navController.navigate(it.route)
							},
							modifier = Modifier.padding(it)
						)
					}
					AnimatedVisibility(
						visible = navigationType == NavigationType.NAVIGATION_DRAWER
					) {
						PicoverNavigationDrawer(
							items = navigationItems,
							navController = navController,
							onItemClick = {
								navController.navigate(it.route)
							},
							modifier = Modifier.padding(it)
						)
					}
				}
			}
		}
	}
}
