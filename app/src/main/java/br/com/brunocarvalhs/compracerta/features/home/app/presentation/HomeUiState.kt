package br.com.brunocarvalhs.compracerta.features.home.app.presentation

import br.com.brunocarvalhs.compracerta.features.home.app.domain.model.Group

internal sealed class HomeUiState {
    data object Loading : HomeUiState()
    data class Success(val groups: List<Group>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}