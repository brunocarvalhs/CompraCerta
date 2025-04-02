package br.com.brunocarvalhs.compracerta.features.home.app.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.brunocarvalhs.compracerta.commons.analytics.AnalyticsParams
import br.com.brunocarvalhs.compracerta.commons.extensions.trackClick
import br.com.brunocarvalhs.compracerta.features.home.app.domain.model.Group

@Composable
fun GroupItem(
    modifier: Modifier = Modifier,
    item: Group,
    onSelectedChange: (Group) -> Unit,
    onDelete: (Group) -> Unit,
    onShare: (Group) -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .clickable { onSelectedChange(item) }.trackClick(
                mapOf(
                    AnalyticsParams.GROUP_ID to item.id.toString(),
                    AnalyticsParams.GROUP_NAME to item.name,
                )
            ),
        color = Color(0xFF1E1E1E),
        tonalElevation = 2.dp,
        shape = RoundedCornerShape(8.dp)
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
            }
            Row {
                IconButton(onClick = { onShare(item) }.trackClick(
                    mapOf(
                        AnalyticsParams.USER_ACTION to "share_group",
                        AnalyticsParams.GROUP_ID to item.id.toString(),
                        AnalyticsParams.GROUP_NAME to item.name,
                    )
                )) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "Share",
                        tint = Color.White
                    )
                }
                IconButton(
                    onClick = { onDelete(item) }.trackClick(
                        mapOf(
                            AnalyticsParams.USER_ACTION to "delete_group",
                            AnalyticsParams.GROUP_ID to item.id.toString(),
                            AnalyticsParams.GROUP_NAME to item.name,
                        )
                    )) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun GroupItemPreview() {
    val item = object : Group {
        override val id: Long = 1L
        override val name: String = "Group Name"
    }
    GroupItem(
        item = item,
        onSelectedChange = {},
        onDelete = {},
        onShare = {}
    )
}