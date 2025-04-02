package br.com.brunocarvalhs.compracerta.features.home.app.presentation

import android.content.Context
import br.com.brunocarvalhs.compracerta.features.home.app.domain.model.Group

internal sealed class HomeIntent {
    data object FetchData : HomeIntent()
    data class CreateGroup(val callback: (Group) -> Unit = {}) : HomeIntent()
    data class DeleteGroup(val group: Group) : HomeIntent()
    data class ShareGroup(val context: Context, val group: Group) : HomeIntent()
}