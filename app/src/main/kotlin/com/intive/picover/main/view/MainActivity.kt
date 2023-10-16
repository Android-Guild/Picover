package com.intive.picover.main.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.intive.picover.auth.intent.SignInIntent
import com.intive.picover.common.loader.PicoverLoader
import com.intive.picover.main.navigation.model.NavigationItem
import com.intive.picover.main.navigation.model.NavigationType
import com.intive.picover.main.navigation.view.PicoverNavigationBar
import com.intive.picover.main.navigation.view.PicoverNavigationRail
import com.intive.picover.main.theme.PicoverTheme
import com.intive.picover.main.viewmodel.MainViewModel
import com.intive.picover.main.viewmodel.state.MainState.Loading
import com.intive.picover.main.viewmodel.state.MainState.UserAuthorized
import com.intive.picover.main.viewmodel.state.MainState.UserUnauthorized
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

	@Inject
	lateinit var signInIntent: SignInIntent
	private val viewModel: MainViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			val signInLauncher = rememberLauncherForActivityResult(FirebaseAuthUIActivityResultContract()) {
				if (it.resultCode == RESULT_CANCELED) {
					finish()
				}
			}
			PicoverTheme {
				val state by viewModel.state.collectAsState(initial = Loading)
				when (state) {
					Loading -> PicoverLoader(Modifier.fillMaxSize())
					UserAuthorized -> Content()
					UserUnauthorized -> LaunchedEffect(Unit) { signInLauncher.launch(signInIntent.intent) }
				}
			}
		}
	}

	@Composable
	private fun Content() {
		val windowSize = calculateWindowSizeClass(this)
		val navController = rememberNavController()
		Scaffold { paddingValues ->
			when (NavigationType.fromWindowSize(windowSize)) {
				NavigationType.BOTTOM_BAR -> ContentWithNavigationBar(navController, paddingValues)
				NavigationType.RAIL -> ContentWithNavigationRail(navController, paddingValues)
			}
		}
	}

	@Composable
	private fun ContentWithNavigationBar(
		navController: NavHostController,
		paddingValues: PaddingValues,
	) {
		PicoverNavigationBar(
			navController = navController,
			modifier = Modifier.padding(paddingValues),
			onItemClick = navController::navigateWithSingleTop,
		)
	}

	@Composable
	private fun ContentWithNavigationRail(
		navController: NavHostController,
		paddingValues: PaddingValues,
	) {
		PicoverNavigationRail(
			navController = navController,
			modifier = Modifier.padding(paddingValues),
			onItemClick = navController::navigateWithSingleTop,
		)
	}
}

private fun NavHostController.navigateWithSingleTop(item: NavigationItem) {
	navigate(item.route) {
		popUpTo(graph.findStartDestination().id)
		launchSingleTop = true
	}
}
