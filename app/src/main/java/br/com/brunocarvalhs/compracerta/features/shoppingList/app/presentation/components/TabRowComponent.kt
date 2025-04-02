package br.com.brunocarvalhs.compracerta.features.shoppingList.app.presentation.components

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import br.com.brunocarvalhs.compracerta.commons.analytics.AnalyticsParams
import br.com.brunocarvalhs.compracerta.commons.extensions.trackClick

@Composable
internal fun TabRowComponent(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
) {
    TabRow(selectedTabIndex = selectedTabIndex) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) }.trackClick(
                    mapOf(
                        AnalyticsParams.TAB_INDEX to index.toString(),
                        AnalyticsParams.TAB_NAME to title
                    )
                ),
                text = {
                    Text(
                        text = title,
                        color = if (selectedTabIndex == index) Color(0xFFFFC107) else Color.White,
                        fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                    )
                },
                selectedContentColor = Color(0xFFFFC107),
                unselectedContentColor = Color.White
            )
        }
    }
}

@Composable
@Preview
private fun TabRowComponentPreview() {
    TabRowComponent(
        tabs = listOf("All", "Completed", "Uncompleted"),
        selectedTabIndex = 0,
        onTabSelected = {}
    )
}