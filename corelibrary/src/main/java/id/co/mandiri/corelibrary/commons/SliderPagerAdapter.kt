package id.co.mandiri.corelibrary.commons

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Created by pertadima on 20,July,2019
 */

class SliderPagerAdapter(
    fragmentManager: FragmentManager,
    private val fragmentList: List<Fragment>
) : androidx.fragment.app.FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }
}