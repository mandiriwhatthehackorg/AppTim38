package id.co.mandiri.milenials_deposit.section.verification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import id.co.mandiri.milenials_deposit.R


/**
 * Created by pertadima on 20,July,2019
 */

class VerificationDialogFragment : DialogFragment() {

    override fun onActivityCreated(arg0: Bundle?) {
        super.onActivityCreated(arg0)
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_fragment_verification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}