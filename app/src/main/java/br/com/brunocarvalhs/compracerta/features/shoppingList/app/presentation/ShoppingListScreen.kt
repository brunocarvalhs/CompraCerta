package br.com.brunocarvalhs.compracerta.features.shoppingList.app.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import br.com.brunocarvalhs.compracerta.commons.analytics.AnalyticsParams
import br.com.brunocarvalhs.compracerta.commons.extensions.trackClick
import br.com.brunocarvalhs.compracerta.commons.extensions.trackLayout
import br.com.brunocarvalhs.compracerta.features.shoppingList.app.data.model.ProductModel
import br.com.brunocarvalhs.compracerta.features.shoppingList.app.domain.model.Product
import br.com.brunocarvalhs.compracerta.features.shoppingList.app.presentation.components.ShoppingListItem
import br.com.brunocarvalhs.compracerta.features.shoppingList.app.presentation.components.TabRowComponent
import br.com.brunocarvalhs.compracerta.features.shoppingList.app.presentation.components.TopAppBarComponent
import br.com.brunocarvalhs.compracerta.features.shoppingList.commons.extensions.sumPrice

@Composable
internal fun ShoppingListScreen(
    groupId: Long,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: ShoppingListViewModel = viewModel(
        factory = ShoppingListViewModel.Factory
    )
) {
    val uiState by viewModel.uiState.collectAsState()

    val currentBackStackEntry = navController.currentBackStackEntry
    val savedStateHandle = currentBackStackEntry?.savedStateHandle
    val scannedPrice: String? = savedStateHandle?.get<String>("price")

    LaunchedEffect(scannedPrice) {
        scannedPrice?.let { price ->
            viewModel.handleIntent(
                ShoppingListIntent.AddItem(
                    groupId = groupId,
                    price = price,
                    quantity = 1
                )
            )
            savedStateHandle.remove<String>(key = "price")
        }
    }

    LaunchedEffect(Unit) {
        viewModel.handleIntent(ShoppingListIntent.FetchData(groupId))
    }

    ShoppingListContent(
        modifier = modifier.trackLayout(
            mapOf(
                AnalyticsParams.SCREEN_NAME to "ShoppingList",
                AnalyticsParams.SCREEN_CLASS to "ShoppingListScreen",
                AnalyticsParams.GROUP_ID to groupId.toString()
            )
        ),
        uiState = uiState,
        onIntent = viewModel::handleIntent,
        onAddItem = {
            navController.navigate("scan_data")
        },
        onBackButtonClick = {
            navController.popBackStack()
        }
    )
}

/**
 * Layout principal para exibir de acordo com o estado.
 */
@Composable
private fun ShoppingListContent(
    modifier: Modifier = Modifier,
    uiState: ShoppingListUiState = ShoppingListUiState.Loading,
    onIntent: (ShoppingListIntent) -> Unit = {},
    onAddItem: () -> Unit = {},
    onBackButtonClick: () -> Unit = {}
) {
    when (uiState) {
        ShoppingListUiState.Loading -> {
            Box(
                modifier = Modifier
                    .trackLayout(
                        mapOf(
                            AnalyticsParams.SCREEN_NAME to "ShoppingList",
                            AnalyticsParams.SCREEN_CLASS to "ShoppingListScreen",
                            AnalyticsParams.USER_ACTION to "loading"
                        )
                    )
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }

        is ShoppingListUiState.Error -> {
            Box(
                modifier = Modifier
                    .trackLayout(
                        mapOf(
                            AnalyticsParams.SCREEN_NAME to "ShoppingList",
                            AnalyticsParams.SCREEN_CLASS to "ShoppingListScreen",
                            AnalyticsParams.USER_ACTION to "error",
                            AnalyticsParams.ERROR_MESSAGE to uiState.message
                        )
                    )
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.message,
                    color = Color.White
                )
            }
        }

        is ShoppingListUiState.Success -> {
            ShoppingListSuccessLayout(
                modifier = modifier.trackLayout(
                    mapOf(
                        AnalyticsParams.SCREEN_NAME to "ShoppingList",
                        AnalyticsParams.SCREEN_CLASS to "ShoppingListScreen",
                        AnalyticsParams.GROUP_ID to uiState.items.toString(),
                        AnalyticsParams.USER_ACTION to "success"
                    )
                ),
                items = uiState.items,
                onIntent = onIntent,
                onAddItem = onAddItem,
                onBackButtonClick = onBackButtonClick
            )
        }
    }
}

/**
 * Layout para o estado de sucesso, contendo:
 * - TopBar com título, contagem e total da compra
 * - Abas para All / Completed / Uncompleted
 * - Lista de itens
 * - Botão para adicionar novo item
 */
@Composable
private fun ShoppingListSuccessLayout(
    modifier: Modifier = Modifier,
    items: List<Product>,
    onIntent: (ShoppingListIntent) -> Unit = {},
    onAddItem: () -> Unit = {},
    onBackButtonClick: () -> Unit = {}
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Tudo", "Levando", "Não levar")

    Scaffold(
        modifier = modifier.background(Color(0xFF121212)),
        topBar = {
            TopAppBarComponent(
                items = items,
                onBackButtonClick = onBackButtonClick
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Add new item") },
                onClick = onAddItem.trackClick(
                    mapOf(
                        AnalyticsParams.USER_ACTION to "add_item",
                        AnalyticsParams.SCREEN_NAME to "ShoppingList",
                        AnalyticsParams.SCREEN_CLASS to "ShoppingListScreen"
                    )
                ),
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add item"
                    )
                },
                containerColor = Color(0xFFFFC107),
                contentColor = Color.Black
            )
        },
        containerColor = Color(0xFF121212)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TabRowComponent(
                selectedTabIndex = selectedTabIndex,
                tabs = tabs,
                onTabSelected = { selectedTabIndex = it }
            )

            val filteredItems = when (selectedTabIndex) {
                0 -> items // All
                1 -> items.filter { it.isCompleted } // Completed
                else -> items.filter { !it.isCompleted } // Uncompleted
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Total: R$${"%.2f".format(filteredItems.sumPrice())}",
                        color = Color(0xFFFFC107),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                items(filteredItems) { item ->
                    ShoppingListItem(
                        item = item,
                        onCheckedChange = { checked ->
                            onIntent(ShoppingListIntent.UpdateItem(item.toCopy(isCompleted = checked)))
                        },
                        onQuantityIncrease = {
                            onIntent(ShoppingListIntent.UpdateItem(item.toCopy(quantity = item.quantity + 1)))
                        },
                        onQuantityDecrease = {
                            onIntent(ShoppingListIntent.UpdateItem(item.toCopy(quantity = item.quantity - 1)))
                        },
                        onDelete = {
                            onIntent(ShoppingListIntent.DeleteItem(item))
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ShoppingListScreenDarkPreview() {
    val sampleItems = listOf(
        ProductModel(
            id = 1L,
            name = "Brown rice",
            price = 5.99,
            quantity = 2,
            isCompleted = false,
            groupId = 1L
        ),
        ProductModel(
            id = 1L,
            name = "Mustard",
            price = 2.49,
            quantity = 2,
            isCompleted = false,
            groupId = 1L
        ),
        ProductModel(
            id = 1L,
            name = "Bacon",
            price = 7.89,
            quantity = 2,
            isCompleted = true,
            groupId = 1L
        ),
        ProductModel(
            id = 1L,
            name = "Red-wine vinegar",
            price = 4.29,
            quantity = 2,
            isCompleted = false,
            groupId = 1L
        ),
        ProductModel(
            id = 1L,
            name = "Hot pepper sauce",
            price = 3.99,
            quantity = 2,
            isCompleted = true,
            groupId = 1L
        ),
    )

    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Color(0xFFFFC107), // Amarelo
            onPrimary = Color.Black,
            background = Color(0xFF121212),
            onBackground = Color.White
        )
    ) {
        ShoppingListSuccessLayout(
            items = sampleItems,
            onIntent = {},
        )
    }
}
