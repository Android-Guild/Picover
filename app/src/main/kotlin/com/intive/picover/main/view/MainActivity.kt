package com.intive.picover.main.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.intive.picover.auth.intent.SignInIntent
import com.intive.picover.common.loader.PicoverLoader
import com.intive.picover.main.navigation.view.MainScreen
import com.intive.picover.main.theme.PicoverTheme
import com.intive.picover.main.viewmodel.MainViewModel
import com.intive.picover.main.viewmodel.state.MainState.Loading
import com.intive.picover.main.viewmodel.state.MainState.UserAuthorized
import com.intive.picover.main.viewmodel.state.MainState.UserUnauthorized
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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
					UserAuthorized -> MainScreen(this)
					UserUnauthorized -> LaunchedEffect(Unit) { signInLauncher.launch(signInIntent.intent) }
				}
			}
		}
	}
}
