package com.mezhendosina.sgo.data.netschoolEsia.entities.common


import com.google.gson.annotations.SerializedName

data class School(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)