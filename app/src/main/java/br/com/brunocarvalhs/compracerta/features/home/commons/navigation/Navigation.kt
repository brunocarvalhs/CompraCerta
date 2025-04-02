package br.com.brunocarvalhs.compracerta.features.home.commons.navigation

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import br.com.brunocarvalhs.compracerta.features.home.app.presentation.HomeScreen
import br.com.brunocarvalhs.compracerta.features.home.app.presentation.HomeViewModel

fun NavGraphBuilder.homeGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    route: String,
) {
    navigation(startDestination = "list", route = route) {
        composable("list") {
            val viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
            HomeScreen(
                modifier = modifier,
                navController = navController,
                viewModel = viewModel,
            )
        }
    }
}