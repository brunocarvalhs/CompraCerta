package br.com.brunocarvalhs.compracerta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import br.com.brunocarvalhs.compracerta.commons.analytics.AnalyticsEvents
import br.com.brunocarvalhs.compracerta.commons.analytics.AnalyticsParams
import br.com.brunocarvalhs.compracerta.commons.analytics.AnalyticsProvider
import br.com.brunocarvalhs.compracerta.commons.ui.theme.CompraCertaTheme
import br.com.brunocarvalhs.compracerta.features.home.HomeInitialization
import br.com.brunocarvalhs.compracerta.features.scanData.ScanDataInitialization
import br.com.brunocarvalhs.compracerta.features.shoppingList.ShoppingListInitialization

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController().apply {
                addOnDestinationChangedListener { _, destination, _ ->
                    AnalyticsProvider().track(
                        event = AnalyticsEvents.VISUALIZATION,
                        params = mapOf(
                            AnalyticsParams.SCREEN_NAME to destination.route.toString()
                        )
                    )
                }
            }

            CompraCertaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController = navController, startDestination = "home") {
                        HomeInitialization.Builder()
                            .modifier(modifier = Modifier.padding(innerPadding))
                            .navController(navController = navController)
                            .navGraphBuilder(navGraphBuilder = this, route = "home")
                            .build()

                        ShoppingListInitialization.Builder()
                            .modifier(Modifier.padding(innerPadding))
                            .navGraphBuilder(navGraphBuilder = this, route = "shopping_list")
                            .navController(navController = navController)
                            .build()

                        ScanDataInitialization.Builder()
                            .modifier(Modifier.padding(innerPadding))
                            .navGraphBuilder(navGraphBuilder = this, route = "scan_data")
                            .navController(navController = navController)
                            .build()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CompraCertaTheme {
        Greeting("Android")
    }
}