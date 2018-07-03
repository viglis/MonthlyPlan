package com.mobile.vigli.monthlyoneplan.model

import java.util.*
import kotlin.collections.HashMap

/*
    Plan 클래스
 */
class Plan {
    val mName: String
    var mComment: String? = null
    val mYear: Int
    val mMonth: Int
    var mRunDate: MutableMap<Int, String> //key{day} : data{comment}

    constructor(name: String, year: Int, month: Int) {
        mName = name
        mYear = year
        mMonth = month

        mRunDate = HashMap()
    }

    constructor(name:String, date: Date): this(name, date.time)
    constructor(name:String, date: Long) : this(name,
            PlanCalender().getYearMonth(date, PlanCalender.getDefaultTimeZone()).first,
            PlanCalender().getYearMonth(date, PlanCalender.getDefaultTimeZone()).second)
}
