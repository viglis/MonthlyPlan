package com.mobile.vigli.monthlyoneplan.view.fragment.dialog

import android.app.Dialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
import android.widget.EditText
import com.mobile.vigli.monthlyoneplan.R
import com.mobile.vigli.monthlyoneplan.databinding.DialogAddPlanBinding
import com.mobile.vigli.monthlyoneplan.viewmodel.PlanAdderViewModel

/**
 * Created by jtiger on 2018. 5. 28..
 */

class PlanAdderDialogFragment: DialogFragment(), PlanAdderViewModel.OnEventListener {
    //호출자와 통신
    private lateinit var mListener: PlanAdderViewModel.PlanAdderDialogListener
    //This DialogFragment's View Model
    private lateinit var mPlanAdderVm: PlanAdderViewModel

    private lateinit var mGoalEdit: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mPlanAdderVm = ViewModelProviders.of(this).get(PlanAdderViewModel::class.java)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        when(context) {
            is PlanAdderViewModel.PlanAdderDialogListener -> mListener = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //make binding
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_add_plan, null, false)
        val binding: DialogAddPlanBinding = DataBindingUtil.bind(view)!!

        //set view
        mGoalEdit = view.findViewById<EditText>(R.id.goal_edit).apply { addTextChangedListener(mPlanAdderVm.getGoalWatcher()) }

        //binding with ViewModel
        binding.planAdderVm = mPlanAdderVm
        mPlanAdderVm.mInteractor = mListener
        mPlanAdderVm.mEventListener = this

        //create dialog
        val builder = AlertDialog.Builder(activity).apply {
            setView(view)
            setPositiveButton(R.string.btn_create, {_, _ -> })
            setNegativeButton(R.string.btn_cancel, {_, _ -> })
            setCancelable(false)
        }

        return builder.create()
    }

    override fun onStart() {
        super.onStart()
        var dialog = dialog as AlertDialog
        dialog.getButton(Dialog.BUTTON_POSITIVE).apply { setOnClickListener(mPlanAdderVm) }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //키보드 활성화
        mGoalEdit.requestFocus()
        dialog.window.setSoftInputMode(SOFT_INPUT_STATE_VISIBLE);
    }

    //dialog dismiss
    override fun close() {
        dismiss()
    }

    override fun error(code: Int) {
        when (code) {
            PlanAdderViewModel.ERROR_EMPTY_MESSAGE -> mGoalEdit.error = getString(R.string.error_empty_goal)
        }
    }

    companion object {
        const val TAG = "ADD_PLAN_DIALOG"
    }
}