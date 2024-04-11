package com.mezhendosina.sgo.data.netschoolEsia.entities.common


import android.content.Context
import com.google.gson.annotations.SerializedName
import com.mezhendosina.sgo.data.emoji.DefaultLessons
import com.mezhendosina.sgo.data.emoji.EmojiEntity

data class Subject(
    // here, in word 'Curriculum' (in annotation) it looks like that in JSON response from server
    // developers used non-ascii 'C' character
    @SerializedName("federalСurriculum")
    val federalCurriculum: Boolean,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("order")
    val order: Int
) {
//    fun toEmoji(context: Context): EmojiEntity {
//        val defaultEmoji = DefaultLessons.getEmojiLesson(name)
//        val a = context
//        EmojiEntity(
//            id,
//            ,
//            name
//        )
//    }
}