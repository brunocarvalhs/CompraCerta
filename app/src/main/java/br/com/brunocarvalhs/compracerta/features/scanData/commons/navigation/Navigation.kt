package br.com.brunocarvalhs.compracerta.features.scanData.commons.navigation

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import br.com.brunocarvalhs.compracerta.features.scanData.app.presentation.photo_capture.CameraScreen
import br.com.brunocarvalhs.compracerta.features.scanData.app.presentation.photo_capture.CameraViewModel

fun NavGraphBuilder.scanDataGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    route: String,
) {
    navigation(startDestination = "photo_capture", route = route) {
        composable("photo_capture") {
            val viewModel: CameraViewModel = viewModel(
                factory = CameraViewModel.Factory
            )
            CameraScreen(
                modifier = modifier,
                viewModel = viewModel,
                navController = navController
            )
        }
    }
}