package com.mobile.vigli.monthlyoneplan.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.mobile.vigli.monthlyoneplan.R
import com.mobile.vigli.monthlyoneplan.model.Plan
import com.mobile.vigli.monthlyoneplan.model.PlanCalender
import java.util.*

/**
 * Created by jtiger on 2018. 5. 28..
 */

class PlanAdderViewModel(application: Application): AndroidViewModel(application), View.OnClickListener {
    lateinit var mInteractor: PlanAdderViewModel.PlanAdderDialogListener
    lateinit var mEventListener: PlanAdderViewModel.OnEventListener

    //목표 by edittext
    private var mGoal: String = ""
    private var mGoalWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            mGoal = p0.toString()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }

    //목표 by edittext 수정을 위한 텍스트 감시 리스너 반환
    fun getGoalWatcher() = mGoalWatcher

    //dialog positive button click
    override fun onClick(p0: View?) {
        //목표 유효성 체크
        if (mGoal.trim().isEmpty()) {
            mEventListener.error(ERROR_EMPTY_MESSAGE)
            return
        }

        //계획 생성 결과 전달
        Plan(mGoal, PlanCalender.getNowDate()).let {
            mInteractor.onDialogPositiveClick(it)
        }

        //다이얼로그 닫음
        mEventListener.close()
    }

    //현재 시간을 기준으로 layout date 형식의 문자열을 가져온다.
    fun getDate(context: Context): String {
        val datePair = PlanCalender().getYearMonth(PlanCalender.getNowDate(), PlanCalender.getDefaultTimeZone())
        return getDate(context, datePair.first, datePair.second)
    }

    //지정한 시간을 기준으로 layout date 형식의 문자열을 가져온다.
    fun getDate(context: Context, year: Int, month: Int): String {
        //언어별 설정
        return when(Locale.getDefault().language) {
            Locale.KOREA.language, Locale.KOREAN.language -> {
                "$year${context.getString(R.string.dialog_add_plan_date_year)}" +
                        " " +
                        "$month${context.getString(R.string.dialog_add_plan_date_month)}"
            }
            else -> {
                "${PlanCalender().monthToMaxString(month)}.$year"
            }
        }
    }

    //다이얼로그와 통신하기 위한 위한 리스너
    interface OnEventListener {
        fun close()
        fun error(code: Int)
    }

    //dialog btn listener
    interface PlanAdderDialogListener {
        fun onDialogPositiveClick(plan: Plan)
    }

    companion object {
        const val ERROR_EMPTY_MESSAGE = 10000
    }
}