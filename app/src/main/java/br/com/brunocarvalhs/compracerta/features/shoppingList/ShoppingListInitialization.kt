package br.com.brunocarvalhs.compracerta.features.shoppingList

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import br.com.brunocarvalhs.compracerta.features.shoppingList.commons.navigation.shoppingListGraph

class ShoppingListInitialization(private val builder: Builder) {

    fun initialize() {
        builder.navGraphBuilder?.shoppingListGraph(
            modifier = builder.modifier,
            navController = builder.navController ?: return,
            route = builder.route
        )
    }

    class Builder {
        internal var modifier: Modifier = Modifier
        internal var navGraphBuilder: NavGraphBuilder? = null
        internal var navController: NavHostController? = null
        internal var route: String = "home"

        fun modifier(modifier: Modifier) = apply {
            this.modifier = modifier
        }

        fun navGraphBuilder(navGraphBuilder: NavGraphBuilder, route: String) = apply {
            this.navGraphBuilder = navGraphBuilder
            this.route = route
        }

        fun navController( navController: NavHostController) = apply {
            this.navController = navController
        }

        fun build() = ShoppingListInitialization(this).initialize()
    }
}