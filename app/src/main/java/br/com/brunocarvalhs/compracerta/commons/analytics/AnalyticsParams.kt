package br.com.brunocarvalhs.compracerta.commons.analytics

import com.google.firebase.analytics.FirebaseAnalytics

enum class AnalyticsParams(val value: String) {
    SCREEN_NAME(FirebaseAnalytics.Param.SCREEN_NAME),
    ITEM_ID(FirebaseAnalytics.Param.ITEM_ID),
    ITEM_NAME(FirebaseAnalytics.Param.ITEM_NAME),
    CONTENT_TYPE(FirebaseAnalytics.Param.CONTENT_TYPE),
    VALUE(FirebaseAnalytics.Param.VALUE),

    // Parâmetros personalizados
    USER_ACTION("user_action"),
    GROUP_ID("group_id"),
    MEMBER_ID("member_id"),
    ERROR_MESSAGE("error_message"),
    FAQ_SECTION("faq_section"),
    USER_ID("user_id"), // ID do usuário, se aplicável
    GROUP_NAME("group_name"), // Nome do grupo
}