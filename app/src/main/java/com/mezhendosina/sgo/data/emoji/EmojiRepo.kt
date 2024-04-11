package com.mezhendosina.sgo.data.emoji

import android.graphics.drawable.Drawable
import androidx.annotation.IdRes

interface EmojiRepo {
    suspend fun getEmojiId(lessonId: Int): Int

    suspend fun setEmojiId(lessonId: Int, @IdRes emoji: Int)

}