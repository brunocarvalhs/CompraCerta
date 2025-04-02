package br.com.brunocarvalhs.compracerta.features.shoppingList.app.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.brunocarvalhs.compracerta.features.shoppingList.app.data.model.ProductModel
import br.com.brunocarvalhs.compracerta.features.shoppingList.app.domain.model.Product
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import br.com.brunocarvalhs.compracerta.features.shoppingList.commons.extensions.sumPrice

/**
 * Um item da lista, contendo nome, preço, checkbox e botões para modificar a quantidade.
 */
@Composable
internal fun ShoppingListItem(
    item: Product,
    onCheckedChange: (Boolean) -> Unit,
    onQuantityIncrease: () -> Unit,
    onQuantityDecrease: () -> Unit,
    onDelete: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp),
        color = Color(0xFF1E1E1E), // Card escuro
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = item.name,
                    color = Color.White
                )
                Text(
                    text = "$${"%.2f".format(item.sumPrice())}",
                    color = Color(0xFFFFC107),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    if (item.quantity > 1) {
                        onQuantityDecrease()
                    } else {
                        onDelete()
                    }
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Diminuir quantidade",
                        tint = Color.White
                    )
                }
                Text(
                    text = "${item.quantity}",
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                IconButton(onClick = onQuantityIncrease) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Adicionar quantidade",
                        tint = Color.White
                    )
                }
                Checkbox(
                    checked = item.isCompleted,
                    onCheckedChange = onCheckedChange,
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFFFFC107),
                        uncheckedColor = Color.White,
                        checkmarkColor = Color.Black
                    )
                )
            }
        }
    }
}

@Composable
@Preview
private fun ShoppingListItemPreview() {
    ShoppingListItem(
        item = ProductModel(
            id = 1L,
            name = "Produto de Teste",
            price = 10.0,
            quantity = 2,
            isCompleted = false,
            groupId = 1L,
        ),
        onCheckedChange = {},
        onQuantityIncrease = {},
        onQuantityDecrease = {},
        onDelete = {}
    )
}
