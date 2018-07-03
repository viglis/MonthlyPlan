package com.mobile.vigli.monthlyoneplan.database

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.mobile.vigli.monthlyoneplan.database.dao.PlanRoomDao
import com.mobile.vigli.monthlyoneplan.database.entity.MonthPlanData
import com.mobile.vigli.monthlyoneplan.database.entity.MonthPlanDetailData
import com.mobile.vigli.monthlyoneplan.database.entity.parent.PlanData

/**
 * Created by jtiger on 2018. 5. 28..
 */

class Repository(application: Application) {
    private val mPlanDao : PlanRoomDao

    init {
        val db = MyRoomDatabase.getInstance(application)
        mPlanDao = db.planDao
    }

    //db에서 모든 계획을 가져온다.
    fun getAllMonthPlans() = mPlanDao.getAllMonthPlans()

    //db에서 year, month에 해당하는 계획을 가져온다.
    fun getMonthPlan(year: Int, month: Int): LiveData<MonthPlanData> {
        return mPlanDao.getMonthPlan(year, month)
    }

    //db에 월 목표를 추가한다.
    fun insertYearPlan(yearPlan: PlanData) {
        InsertTask(mPlanDao).execute(yearPlan)
    }

    //Plan 데이터를 디비에 추가한다.
    private class InsertTask(private val mPlanDao: PlanRoomDao) : AsyncTask<PlanData, Void, Void>() {
        override fun doInBackground(vararg plans: PlanData): Void? {
            for (plan in plans) {
                when (plan) {
                    is MonthPlanData -> mPlanDao.insertMonthPlan(plan)
                    is MonthPlanDetailData -> mPlanDao.insertMonthPlanInformation(plan)
                }
            }
            return null
        }
    }
}