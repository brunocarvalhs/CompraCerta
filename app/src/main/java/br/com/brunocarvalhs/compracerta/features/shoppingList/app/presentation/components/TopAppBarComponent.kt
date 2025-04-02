package br.com.brunocarvalhs.compracerta.features.shoppingList.app.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import br.com.brunocarvalhs.compracerta.features.shoppingList.app.domain.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopAppBarComponent(
    items: List<Product>,
    onBackButtonClick: () -> Unit,
) {
    val totalPurchase = items.sumOf { it.price }
    val totalQuantity = items.sumOf { it.quantity }

    TopAppBar(
        title = {
            Column {
                Text(
                    text = "Shopping list",
                    color = Color.White
                )
                Text(
                    text = "Items (${totalQuantity})",
                    color = Color(0xFFFFC107),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color(0xFF121212)
        ),
        navigationIcon = {
            IconButton(
                onClick = onBackButtonClick,
                content = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            )
        }
    )
}