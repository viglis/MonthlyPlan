package com.mobile.vigli.monthlyoneplan.database

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.mobile.vigli.monthlyoneplan.database.entity.MonthPlanData
import com.mobile.vigli.monthlyoneplan.model.Plan

class PlanMaker {

    //Plan을 DB 타입 월 목표 데이터로 변환한다.
    fun planToRoomData(plan:Plan): MonthPlanData {
        return MonthPlanData(plan.mName).apply {
            year = plan.mYear
            month = plan.mMonth
            comment = plan.mComment
        }
    }

    //DB 타입 월 목표 데이터를 Plan으로 변환한다.
    fun roomDataToPlan(monthPlanData: MonthPlanData?): LiveData<Plan> {
        return MutableLiveData<Plan>().apply {
            if (monthPlanData != null) {
                var yPlan = Plan(monthPlanData.name, monthPlanData.year, monthPlanData.month).apply {
                    mComment = monthPlanData.comment
                }
                value = yPlan
            }
        }
    }
}