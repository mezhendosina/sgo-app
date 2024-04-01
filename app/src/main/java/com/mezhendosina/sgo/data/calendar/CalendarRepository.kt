package com.mezhendosina.sgo.data.calendar

import com.mezhendosina.sgo.data.WeekStartEndEntity

interface CalendarRepository {

    fun dateToRussianWithTime(date: String): String
    fun dateFormat(date: String): String
    fun dateToRussian(date: String): String
    fun isNowBetween(dateStart: String, dateEnd: String): Boolean
    fun dateToTime(date: String): String
    fun getWeeksList(): List<WeekStartEndEntity>

    fun currentWeekStart(): String
}