package br.com.brunocarvalhs.compracerta.features.shoppingList.commons.navigation

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import br.com.brunocarvalhs.compracerta.features.shoppingList.app.presentation.ShoppingListScreen
import br.com.brunocarvalhs.compracerta.features.shoppingList.app.presentation.ShoppingListViewModel

fun NavGraphBuilder.shoppingListGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    route: String,
) {
    composable("${route}/{groupId}", arguments = listOf(
        navArgument("groupId") { type = NavType.LongType }
    )) {
        val viewModel: ShoppingListViewModel = viewModel(factory = ShoppingListViewModel.Factory)
        ShoppingListScreen(
            groupId = it.arguments?.getLong("groupId") ?: 0L,
            modifier = modifier,
            navController = navController,
            viewModel = viewModel,
        )
    }
}