package id.co.mandiri.milenials_deposit.utils

import android.content.Context
import android.widget.Button
import androidx.core.content.ContextCompat
import id.co.mandiri.milenials_deposit.R

/**
 * Created by pertadima on 20,July,2019
 */


fun Button.setAvailable(enable: Boolean, context: Context) {
    with(this) {
        isEnabled = enable
        setBackgroundResource(if (enable) R.drawable.rounded_button_yellow_active else R.drawable.rounded_button_inactive)
        setTextColor(ContextCompat.getColor(context, if (enable) R.color.colorWhite else R.color.colorWhite))
    }
}