package com.mobile.vigli.monthlyoneplan.model

import java.util.*

/*
    날짜 관리 클래스
 */
class PlanCalender {
    fun getYearMonth(date: Date, timezone: TimeZone): Pair<Int, Int> {
        val calender = Calendar.getInstance(timezone)
        calender.time = date
        return Pair(calender.get(Calendar.YEAR), calender.get(Calendar.MONTH))

    }

    fun getYearMonth(date: Long, timezone: TimeZone): Pair<Int, Int> {
        return getYearMonth(Date(date), timezone)
    }

    fun monthToMaxString(month: Int): String {
        return if (month < 9) "0${month+1}" else "${month+1}"
    }

    fun getNowCalender(): Calendar = PlanCalender.getNowCalender()

    fun getNowYearMonth(): Pair<Int, Int> {
        val calender = PlanCalender.getNowCalender()
        return Pair(calender.get(Calendar.YEAR), calender.get(Calendar.MONTH))
    }

    fun getNowDayOfMonth() = PlanCalender.getNowCalender().get(Calendar.DAY_OF_MONTH)

    companion object {
        fun getDefaultTimeZone() = TimeZone.getDefault()
        fun getNowDate()= Calendar.getInstance().time
        fun getNowCalender()= Calendar.getInstance(getDefaultTimeZone())
    }
}