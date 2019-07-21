package id.co.mandiri.milenials_deposit.section.verification

import android.Manifest
import android.content.Intent
import android.hardware.Camera
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import id.co.mandiri.milenials_deposit.R
import id.co.mandiri.milenials_deposit.base.BaseActivity
import kotlinx.android.synthetic.main.activity_verification_ktp.*
import kotlinx.android.synthetic.main.default_toolbar.view.*
import java.io.IOException


class VerificationKtpActivity : BaseActivity(), SurfaceHolder.Callback, Camera.PictureCallback {
    private var surfaceHolder: SurfaceHolder? = null
    private var camera: Camera? = null

    private var surfaceView: SurfaceView? = null


    override fun onSetupLayout(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_verification_ktp)
        setupToolbarPropertiesWithBackButton(
            toolbar as Toolbar,
            toolbar.toolbar_title,
            R.string.empty_string
        )
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        cameraPermission()
        surfaceView = surface_view
        surfaceHolder = surfaceView!!.holder
        surfaceHolder!!.addCallback(this)

        btn_add_life_plan.setOnClickListener {
            startActivity(Intent(this@VerificationKtpActivity, VerificationSelfieActivity::class.java))
        }
    }

    private fun cameraPermission() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    captureImage()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    if (response.isPermanentlyDenied) {
                        showSettingsDialog()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                    token.continuePermissionRequest()
                }
            }).check()
    }

    private fun showSettingsDialog() {
        val builder = AlertDialog.Builder(this@VerificationKtpActivity)
        builder.setTitle("Membutuhkan akses kamera")
        builder.setMessage("Aplikasi ini membutuhkan akses kamera. Kamu dapat memberikan akses melalui pengaturan")
        builder.setPositiveButton("Buka Pengaturan") { dialog, which ->
            dialog.cancel()
            openSettings()
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, which -> dialog.cancel() }
        builder.show()
    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, 101)
    }

    private fun captureImage() {
        if (camera != null) {
            camera!!.takePicture(null, null, this)
        }
    }

    private fun startCamera() {
        camera = Camera.open()
        camera!!.setDisplayOrientation(90)
        try {
            camera!!.setPreviewDisplay(surfaceHolder)
            camera!!.startPreview()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun resetCamera() {
        if (surfaceHolder!!.surface == null) {
            // Return if preview surface does not exist
            return
        }

        // Stop if preview surface is already running.
        camera!!.stopPreview()
        try {
            // Set preview display
            camera!!.setPreviewDisplay(surfaceHolder)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // Start the camera preview...
        camera!!.startPreview()
    }


    private fun releaseCamera() {
        camera!!.stopPreview()
        camera!!.release()
        camera = null
    }


    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        resetCamera()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        releaseCamera()
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        startCamera()
    }

    override fun onPictureTaken(data: ByteArray?, camera: Camera?) {
        resetCamera()
    }
}
