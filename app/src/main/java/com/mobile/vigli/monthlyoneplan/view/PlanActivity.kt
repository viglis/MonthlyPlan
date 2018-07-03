package com.mobile.vigli.monthlyoneplan.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import com.mobile.vigli.monthlyoneplan.R
import com.mobile.vigli.monthlyoneplan.databinding.ActivityPlanBinding
import com.mobile.vigli.monthlyoneplan.model.Plan
import com.mobile.vigli.monthlyoneplan.model.PlanCalender
import com.mobile.vigli.monthlyoneplan.view.fragment.dialog.PlanAdderDialogFragment
import com.mobile.vigli.monthlyoneplan.viewmodel.PlanAdderViewModel
import com.mobile.vigli.monthlyoneplan.viewmodel.PlanViewModel
import kotlinx.android.synthetic.main.activity_plan.*

class PlanActivity : AppCompatActivity(), PlanViewModel.PlanListener, PlanAdderViewModel.PlanAdderDialogListener {
    private lateinit var mPlanVm: PlanViewModel

    private lateinit var mLayerPlan: ConstraintLayout
    private lateinit var mLayoutEmptyPlan: ConstraintLayout
    private lateinit var mPlanView: TextView
    private lateinit var mDateView: TextView
    private lateinit var mRunDetailView: TextView
    private lateinit var mRunBtn: Button
    private lateinit var mInfoBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set actionbar
        supportActionBar?.hide()

        //set view model
        mPlanVm = ViewModelProviders.of(this).get(PlanViewModel::class.java)
        mPlanVm.mPlanListener = this

        mPlanVm.getNowPlan().observe(this, Observer {
            if (it == null) {
                showAddLayout()
            } else {
                showPlanLayout(it)
            }
        })

        //data binding
        val binding: ActivityPlanBinding = DataBindingUtil.setContentView(this, R.layout.activity_plan)
        binding.planVm = mPlanVm


        mLayoutEmptyPlan = findViewById(R.id.empty_plan_layer)

        mPlanView = findViewById(R.id.goal_text)
        mDateView = findViewById(R.id.date_text)
        mLayerPlan = findViewById(R.id.plan_layer)
        mRunBtn = findViewById(R.id.run_btn)
        mRunDetailView = findViewById(R.id.run_detail_text)
    }

    //월 목표가 없는 경우 보여주는 레이아웃
    private fun showAddLayout() {
        mLayerPlan.visibility = View.GONE
        mLayoutEmptyPlan.visibility = View.VISIBLE
    }

    //월 목표가 있는 경우 보여주는 레이아웃
    private fun showPlanLayout(plan: Plan) {
        mLayerPlan.visibility = View.VISIBLE
        mLayoutEmptyPlan.visibility = View.GONE

        //initialize plan layout
        //이번달 목표
        mPlanView.text = plan.mName
        mDateView.text = mPlanVm.getDate(this, plan.mYear, plan.mMonth)

        //오늘 수행 기록 버튼
        mRunBtn.visibility =
                if (plan.mRunDate.containsKey(mPlanVm.getNowDayOfMonth())) View.GONE
                else View.VISIBLE

        //이번달 수행 요약 정보
        val runCount = plan.mRunDate.size
        mRunDetailView.text =
            if (runCount == 0)
                getString(R.string.activity_plan_run_empty)
            else
                getString(R.string.activity_plan_run, runCount)

    }

    //Plan 생성 버튼 클릭 이벤트
    override fun onCreateClick() {
        //Plan 생성 다이얼로그를 만든다.
        val addDialog = PlanAdderDialogFragment()
        addDialog.show(supportFragmentManager, PlanAdderDialogFragment.TAG)
    }

    //Plan 생성 다이얼로그 확인 클릭
    override fun onDialogPositiveClick(plan: Plan) {
        //db 추가
        mPlanVm.insertMonthPlan(plan)

        //show plan
        showPlanLayout(plan)
    }

    override fun onInfoClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRunClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
