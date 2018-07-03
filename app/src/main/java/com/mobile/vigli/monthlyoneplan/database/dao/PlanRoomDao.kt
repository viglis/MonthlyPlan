package com.mobile.vigli.monthlyoneplan.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.mobile.vigli.monthlyoneplan.database.entity.MonthPlanData
import com.mobile.vigli.monthlyoneplan.database.entity.MonthPlanDetailData

/**
 * Created by jtiger on 2018. 5. 28..
 */

@Dao
interface PlanRoomDao {
    /*
        년간 plan에 대한 인터페이스 정의
     */
    @Query("SELECT * FROM month_plan_table")
    fun getAllMonthPlans(): LiveData<List<MonthPlanData>>

    @Query("SELECT * FROM month_plan_table WHERE year = :year and month = :month")
    fun getMonthPlan(year: Int, month: Int): LiveData<MonthPlanData>

    @Insert
    fun insertMonthPlan(monthPlan: MonthPlanData)

    @Update
    fun updateMonthPlan(monthPlan: MonthPlanData)


    /*
        plan의 정보(MonthPlan : 실행 날짜, Comment : 메모) 대한 인터페이스
     */
    @Query("SELECT * FROM month_plan_detail_table WHERE planId = :monthPlanId")
    fun getMonthPlanInformation(monthPlanId: Long): LiveData<List<MonthPlanDetailData>>

    @Insert
    fun insertMonthPlanInformation(monthPlan: MonthPlanDetailData)
}