package br.com.brunocarvalhs.compracerta.features.home.app.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import br.com.brunocarvalhs.compracerta.commons.analytics.AnalyticsParams
import br.com.brunocarvalhs.compracerta.commons.extensions.trackClick
import br.com.brunocarvalhs.compracerta.features.home.app.data.model.GroupModel
import br.com.brunocarvalhs.compracerta.features.home.app.domain.model.Group
import br.com.brunocarvalhs.compracerta.features.home.app.presentation.components.GroupItem

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = HomeViewModel.Factory
    ),
    navController: NavHostController?
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleIntent(HomeIntent.FetchData)
    }

    HomeContent(
        modifier = modifier,
        uiState = uiState,
        onIntent = viewModel::handleIntent,
        onNavigateToGroupDetails = { group ->
            navController?.navigate("shopping_list/${group.id}")
        }
    )
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    uiState: HomeUiState = HomeUiState.Loading,
    onIntent: (HomeIntent) -> Unit = {},
    onNavigateToGroupDetails: (Group) -> Unit = {}
) {
    val context = LocalContext.current

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onIntent(HomeIntent.CreateGroup(
                        callback = { group ->
                            onNavigateToGroupDetails(group)
                        }
                    ))
                },
                content = {
                    Icon(Icons.Filled.Add, contentDescription = "Adicionar")
                }
            )
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (uiState) {
                is HomeUiState.Loading -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is HomeUiState.Success -> {
                    val stateList = rememberLazyListState()

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        state = stateList,
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        items(uiState.groups) { item ->
                            GroupItem(
                                item = item,
                                onSelectedChange = onNavigateToGroupDetails,
                                onDelete = { group ->
                                    onIntent(HomeIntent.DeleteGroup(group))
                                },
                                onShare = { group ->
                                    onIntent(HomeIntent.ShareGroup(context, group))
                                }
                            )
                        }
                    }
                }
                is HomeUiState.Error -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {
                        Text(text = "Erro ao carregar dados")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenDarkPreview(
    @PreviewParameter(HomePreviewProvider::class) state: HomeUiState
) {
    HomeContent(uiState = state)
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview(
    @PreviewParameter(HomePreviewProvider::class) state: HomeUiState
) {
    HomeContent(uiState = state)
}

private class HomePreviewProvider : PreviewParameterProvider<HomeUiState> {
    override val values = sequenceOf(
        HomeUiState.Loading,
        HomeUiState.Success(
            listOf(
                GroupModel(
                    id = 1,
                    name = "Grupo 1",
                ),
                GroupModel(
                    id = 2,
                    name = "Grupo 2",
                )
            )
        ),
        HomeUiState.Error("Erro ao carregar dados")
    )
}
