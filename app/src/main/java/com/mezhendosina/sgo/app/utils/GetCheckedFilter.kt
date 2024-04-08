package com.mezhendosina.sgo.app.utils

import com.mezhendosina.sgo.app.uiEntities.FilterUiEntity

fun List<FilterUiEntity>.getChecked(): FilterUiEntity? {
    return find { it.checked } ?: getOrNull(0)
}