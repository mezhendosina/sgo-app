/*
 * Copyright 2024 Eugene Menshenin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.mezhendosina.sgo.data.calendar

import android.annotation.SuppressLint
import com.mezhendosina.sgo.data.WeekStartEndEntity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalendarRepositoryImpl @Inject constructor() : CalendarRepository {
    private val locale = Locale("ru", "RU")
    private val baseDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", locale)

    override fun dateToRussianWithTime(date: String): String = date.toDate("dd.MM.yyyy hh:mm")
    override fun dateFormat(date: String): String = date.toDate("dd.MM.yyyy")

    override fun dateToRussian(date: String): String =
        date.toDate("EEEE, d MMMM").replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }

    override fun isNowBetween(dateStart: String, dateEnd: String): Boolean {
        val calendarStartDate = dateStart.dateToCalendar()
        val calendarEndDate = dateEnd.dateToCalendar()
        val afterStart = Calendar.getInstance().after(calendarStartDate)
        val beforeEnd = Calendar.getInstance().before(calendarEndDate)
        return afterStart && beforeEnd
    }

    @SuppressLint("SimpleDateFormat")
    override fun dateToTime(date: String): String {
        val parse = baseDateFormat.parse(date)
        return SimpleDateFormat("hh:mm").format(parse!!)
    }

    override fun getWeeksList(): List<WeekStartEndEntity> {
        val outList = mutableListOf<WeekStartEndEntity>()
        val currentYearTime = currentYearTime()

        val minusWeekCalendar = Calendar.getInstance()
        minusWeekCalendar.timeInMillis = currentYearTime
        val plusWeekCalendar = Calendar.getInstance()
        plusWeekCalendar.timeInMillis = currentYearTime
        val currentCalendar = Calendar.getInstance()
        currentCalendar.timeInMillis = currentYearTime

        minusWeekCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        plusWeekCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)

        while (minusWeekCalendar[Calendar.MONTH] != Calendar.AUGUST) {
            minusWeekCalendar.add(Calendar.WEEK_OF_YEAR, -1)
            val weekStart = minusWeekCalendar.dateToSting()
            val weekEnd = minusWeekCalendar.getWeekEnd()
            outList.add(
                WeekStartEndEntity(
                    weekStart,
                    weekEnd,
                    tabDate(weekStart),
                    tabDate(weekEnd)
                )
            )
        }

        while (plusWeekCalendar[Calendar.YEAR] != currentCalendar[Calendar.YEAR] + 1 || plusWeekCalendar[Calendar.MONTH] != Calendar.SEPTEMBER) {
            val weekStart = plusWeekCalendar.dateToSting()
            val weekEnd = plusWeekCalendar.getWeekEnd()
            outList.add(
                WeekStartEndEntity(
                    weekStart,
                    weekEnd,
                    tabDate(weekStart),
                    tabDate(weekEnd)
                )
            )
            plusWeekCalendar.add(Calendar.WEEK_OF_YEAR, 1)
        }

        return outList.sortedBy { it.weekStart }
    }

    override fun currentWeekStart(): String {
        TODO("Not yet implemented")
    }

    private fun String.dateToCalendar(): Calendar {
        val parseDate = baseDateFormat.parse(this)
        val year = SimpleDateFormat("yyyy", locale).format(parseDate!!).toInt()
        val month = SimpleDateFormat("MM", locale).format(parseDate).toInt()
        val day = SimpleDateFormat("dd", locale).format(parseDate).toInt()
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return calendar
    }

    private fun String.toDate(format: String): String {
        val parseDate = baseDateFormat.parse(this)
        return SimpleDateFormat(format, locale).format(parseDate!!)

    }

    private fun Calendar.dateToSting(): String = this.time.getDateByTime()

    private fun Calendar.getWeekEnd(): String {
        this.add(Calendar.DAY_OF_YEAR, +6)
        val weekEnd = this.time.getDateByTime()
        this.add(Calendar.DAY_OF_YEAR, -6)
        return weekEnd
    }

    private fun Date.getDateByTime(): String {
        return SimpleDateFormat("yyyy-MM-dd", locale).format(this)
    }

    private fun tabDate(date: String): String {
        val s = SimpleDateFormat("yyyy-MM-dd", locale).parse(date)
        return SimpleDateFormat("d MMM", locale).format(s!!)
    }

    private fun currentYearTime(): Long {
        val s = SimpleDateFormat("MM.yyyy", locale).format(Date().time)
        val a = SimpleDateFormat("MM.yyyy", locale).parse(s)!!
        return a.time
    }

}