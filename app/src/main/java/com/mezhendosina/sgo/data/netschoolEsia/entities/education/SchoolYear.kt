package com.mezhendosina.sgo.data.netschoolEsia.entities.education


import com.google.gson.annotations.SerializedName
import com.mezhendosina.sgo.app.uiEntities.FilterUiEntity

data class SchoolYear(
    @SerializedName("endDate")
    val endDate: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("startDate")
    val startDate: String
) {
    fun toUi(checked: Boolean): FilterUiEntity = FilterUiEntity(
        id,
        name,
        checked
    )
}