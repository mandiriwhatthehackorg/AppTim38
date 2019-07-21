package id.co.mandiri.milenials_deposit.section.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.co.mandiri.milenials_deposit.R
import kotlinx.android.synthetic.main.fragment_onboarding.*

/**
 * Created by pertadima on 20,July,2019
 */

class OnboardingFragment : Fragment() {
    companion object {
        const val IMAGE_CONTENT = "img_content"
        const val TITLE_CONTENT = "title_content"
        const val CONTENT = "content"

        fun newInstance(imgContent: Int, title: String, content: String) = OnboardingFragment().apply {
            arguments = Bundle().apply {
                putInt(IMAGE_CONTENT, imgContent)
                putString(TITLE_CONTENT, title)
                putString(CONTENT, content)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_onboarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            title_onboarding.text = it.getString(TITLE_CONTENT)
            desc_onboarding.text = it.getString(CONTENT)
            image_onboarding.setImageResource(it.getInt(IMAGE_CONTENT))
        }
    }

}