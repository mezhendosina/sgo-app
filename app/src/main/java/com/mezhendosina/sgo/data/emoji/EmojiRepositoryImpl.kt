package com.mezhendosina.sgo.data.emoji

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mezhendosina.sgo.app.uiEntities.LessonNameUiEntity
import com.mezhendosina.sgo.data.netschoolEsia.utils.UtilsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EmojiRepositoryImpl
@Inject
constructor(
    private val emojiDao: EmojiDao,
    private val utilsRepository: UtilsRepository,
    @ApplicationContext val context: Context,
) : EmojiRepo {
    private val _emojiList = MutableLiveData<List<EmojiEntity>>()
    val emojiList: LiveData<List<EmojiEntity>> = _emojiList

    init {
        CoroutineScope(Dispatchers.IO).launch {
            utilsRepository.subjects.collect {
                it.forEach { subject ->
                    if (emojiDao.getEmoji(subject.id) == null) {
                        emojiDao.insertEmoji(

                        )
                    }
                }
            }
        }
    }

    suspend fun getEmojiLessonByName(
        lessonName: String,
        lessonId: Int,
    ): LessonNameUiEntity? {
        val findInDb = _emojiList.value?.find { it.name.contains(lessonName) }
        return if (findInDb == null) {
            val defaultEmoji =
                DefaultLessons.list.firstOrNull { lessonName.contains(it.nameId, true) }
            defaultEmoji?.let {
                emojiDao.insertEmoji(
                    EmojiEntity(
                        lessonId,
                        context.resources.getResourceName(defaultEmoji.emoji),
                        lessonName,
                    ),
                )
            }
            defaultEmoji
        } else {
            LessonNameUiEntity(
                findInDb.name,
                context.resources.getIdentifier(findInDb.emoji, "drawable", context.packageName),
                findInDb.name,
            )
        }
    }

    override suspend fun getEmojiId(lessonId: Int): Int {
        TODO("Not yet implemented")
    }

    override suspend fun setEmojiId(lessonId: Int, emoji: Int) {
        TODO("Not yet implemented")
    }
}
