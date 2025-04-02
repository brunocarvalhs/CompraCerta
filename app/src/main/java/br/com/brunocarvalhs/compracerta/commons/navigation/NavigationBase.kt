package br.com.brunocarvalhs.compracerta.commons.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink

interface NavigationBase {
    val route: String
    val arguments: List<NamedNavArgument>
    val deepLinks: List<NavDeepLink>
}