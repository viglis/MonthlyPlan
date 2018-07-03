package com.mobile.vigli.monthlyoneplan.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.content.Context
import android.view.View
import com.mobile.vigli.monthlyoneplan.R
import com.mobile.vigli.monthlyoneplan.database.PlanMaker
import com.mobile.vigli.monthlyoneplan.database.Repository
import com.mobile.vigli.monthlyoneplan.model.Plan
import com.mobile.vigli.monthlyoneplan.model.PlanCalender
import java.util.*

/**
 * Created by jtiger on 2018. 5. 28..
 */

class PlanViewModel(application: Application): AndroidViewModel(application) {
    private val mRepository: Repository =  Repository(application)
    private val mPlanCalender = PlanCalender()
    lateinit var mPlanListener: PlanListener

    private var mPlanLiveData: LiveData<Plan>

    init {
        mPlanLiveData = getNowMonthPlan()
    }

    //모든 월 목표를 가져온다.
    private fun getAllMonthPlans() = mRepository.getAllMonthPlans()

    //이번 달 목표를 가져온다.
    private fun getNowMonthPlan(): LiveData<Plan> {
        val yPlan = mPlanCalender.getNowYearMonth().
                let { mRepository.getMonthPlan(it.first, it.second) }

        return PlanMaker().roomDataToPlan(yPlan.value)
    }

    fun getNowPlan(): LiveData<Plan> = mPlanLiveData

    //월 목표를 추가한다.
    fun insertMonthPlan(plan: Plan) {
        PlanMaker().planToRoomData(plan).let {
            mRepository.insertYearPlan(it)
        }
    }

    fun getDate(context: Context, year: Int, month: Int): String {
        //언어별 설정
        return when(Locale.getDefault().language) {
            Locale.KOREA.language, Locale.KOREAN.language -> {
                "$year${context.getString(R.string.dialog_add_plan_date_year)}" +
                        " " +
                        "$month${context.getString(R.string.dialog_add_plan_date_month)}"
            }
            else -> {
                "${mPlanCalender.monthToMaxString(month)}.$year"
            }
        }
    }

    //btns click event
    fun onClick(v: View) {
        when (v.id) {
            R.id.create_btn -> {
                // 일정 생성
                mPlanListener.onCreateClick()
            }
            R.id.info_btn -> {
                // 세부 정보 클릭

                mPlanListener.onInfoClick()
            }
            R.id.run_btn -> {
                // 오늘 실행
                var plan:Plan = mPlanLiveData.value!!
                plan.mRunDate.put(mPlanCalender.getNowYearMonth().second, "")
//                mPlanListener.onRunClick()
            }
        }
    }

    fun getNowDayOfMonth() = mPlanCalender.getNowDayOfMonth()


    interface PlanListener {
        fun onCreateClick()
        fun onInfoClick()
        fun onRunClick()
    }
}