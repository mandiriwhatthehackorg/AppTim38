package id.co.mandiri.milenials_deposit.section.verification

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import id.co.mandiri.corelibrary.commons.toRupiah
import id.co.mandiri.milenials_deposit.R
import kotlinx.android.synthetic.main.dialog_fragment_verification.*

/**
 * Created by pertadima on 20,July,2019
 */

class VerificationDialogFragment : DialogFragment() {
    companion object {
        const val PLAN_NAME_TAG = "planName"
        const val COST_TAG = "cost"
        const val IS_NEW = "isNew"
    }

    fun newInstance(name: String, cost: Double, isNew: Boolean): VerificationDialogFragment {
        return VerificationDialogFragment().apply {
            val args = Bundle().apply {
                putString(PLAN_NAME_TAG, name)
                putDouble(COST_TAG, cost)
                putBoolean(IS_NEW, isNew)
            }
            arguments = args
        }
    }

    private var isNew = false
    override fun onActivityCreated(arg0: Bundle?) {
        super.onActivityCreated(arg0)
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_fragment_verification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            tv_verification_text.text = getString(R.string.text_verification_title, it.getString(PLAN_NAME_TAG))
            tv_amount.text = toRupiah(it.getDouble(COST_TAG))
            isNew = it.getBoolean(IS_NEW)
        }

        btn_yes.setOnClickListener {
            if(isNew) {
                startActivity(Intent(requireContext(), VerificationIntroductionActivity::class.java))
            } else {

            }
        }

        btn_no.setOnClickListener {
            dialog?.dismiss()
        }
    }
}