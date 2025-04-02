package br.com.brunocarvalhs.compracerta.commons.analytics

import com.google.firebase.analytics.FirebaseAnalytics

enum class AnalyticsParams(val value: String) {
    SCREEN_NAME(FirebaseAnalytics.Param.SCREEN_NAME),
    USER_ACTION("user_action"),
    GROUP_ID("group_id"),
    ERROR_MESSAGE("error_message"),
    USER_ID("user_id"), // ID do usuário, se aplicável
    GROUP_NAME("group_name"), // Nome do grupo
    SCREEN_CLASS("screen_class"), // Classe da tela
    TAB_NAME("tab_name"), // Nome da aba
    TAB_INDEX("tab_index"), // Índice da aba
    PRODUCT_ID("product_id"), // ID do produto
    PRODUCT_NAME("product_name"), // Nome do produto
    PRODUCT_CATEGORY("product_category"), // Categoria do produto
    PRODUCT_PRICE("product_price"), // Preço do produto
    PRODUCT_QUANTITY("product_quantity"), // Quantidade do produto
}