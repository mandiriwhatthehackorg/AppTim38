package id.co.mandiri.corelibrary.commons

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import id.co.mandiri.corelibrary.R
/**
 * Created by pertadima on 19,July,2019
 */

fun ImageView.loadImage(url: String?) {
    Glide.with(this)
        .load(url)
        .apply(
            RequestOptions()
                .centerCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.color.lineColor)
                .error(R.color.lineColor)
        )
        .into(this)
}


fun Window.blockTouchScreen() {
    this.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )
}

fun Window.unblockTouchScreen() {
    this.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}


fun ImageView.setDrawableVectorCompat(@DrawableRes drawableId: Int) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        this.setImageDrawable(
            VectorDrawableCompat.create(this.context.resources, drawableId, this.context.theme)
        )
    } else {
        this.setImageResource(drawableId)
    }
}

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Activity.showDialog(
    message: String, cancelable: Boolean = false,
    positiveButton: String, action: () -> Unit = {}
) {

    val dialogBuilder = AlertDialog.Builder(this).apply {
        setMessage(message)
        setCancelable(cancelable)
        setPositiveButton(positiveButton) { dialog, _ ->
            action()
            dialog.dismiss()
        }
    }
    dialogBuilder.create().show()
}

fun Activity.showDialog(
    message: String, cancelable: Boolean = false,
    positiveButton: String, positiveAction: () -> Unit = {},
    negativeButton: String, negativeAction: () -> Unit = {}
) {

    val dialogBuilder = AlertDialog.Builder(this).apply {
        setMessage(message)
        setCancelable(cancelable)
        setPositiveButton(positiveButton) { dialog, _ ->
            positiveAction()
            dialog.dismiss()
        }
        setNegativeButton(negativeButton) { dialog, _ ->
            negativeAction()
            dialog.dismiss()
        }
    }
    dialogBuilder.create().show()
}

fun Context.color(@ColorRes colorRes: Int): Int = ContextCompat.getColor(this, colorRes)

fun Fragment.color(@ColorRes colorRes: Int): Int = requireContext().color(colorRes)

fun toRupiah(currency: Double): String {
    return String.format("Rp. %,.0f", currency).replace(",", ".") + ",00"
}
