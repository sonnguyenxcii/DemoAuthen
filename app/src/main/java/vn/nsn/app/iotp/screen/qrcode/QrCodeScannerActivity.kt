package vn.nsn.app.iotp.screen.qrcode

import android.Manifest
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import vn.nsn.app.iotp.mvp.MvpActivity
import vn.nsn.app.iotp.screen.authen.pincode.CreatePinCodeContract
import vn.nsn.app.iotp.screen.authen.pincode.CreatePinCodePresenter
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.widget.Toast
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.NotificationCompat
import android.support.v4.app.TaskStackBuilder
import android.support.v4.content.ContextCompat
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import vn.nsn.app.iotp.R
import android.util.Log
import android.widget.RemoteViews
import butterknife.BindView
import org.greenrobot.eventbus.EventBus
import vn.nsn.app.iotp.helper.PreferenceHelper
import vn.nsn.app.iotp.screen.LoginFragment
import vn.nsn.app.iotp.screen.main.PushEvent
import vn.nsn.app.iotp.screen.transaction.TransactionDetailActivity
import vn.nsn.app.iotp.util.MyIntentService.createNotificationChannel


class QrCodeScannerActivity : MvpActivity<CreatePinCodePresenter>(), CreatePinCodeContract, ZXingScannerView.ResultHandler {

    private val REQUEST_CAMERA = 1

    @BindView(R.id.scannerView)
    lateinit var mScannerView: ZXingScannerView

    override fun initPresenter() {
    }

    override fun attachViewToPresenter() {
    }



    override val layoutResId: Int

        get() = R.layout.qrcode_scanner_activity

    override fun initViews() {
        val apiVersion = android.os.Build.VERSION.SDK_INT
        if (apiVersion >= android.os.Build.VERSION_CODES.M) {
            if (!checkPermission()) {
                requestPermission()
            }
        }
    }

    override fun setupViews() {

    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CAMERA -> if (grantResults.size > 0) {

                val cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                if (cameraAccepted) {
                    Toast.makeText(applicationContext, "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext, "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                            showMessage("You need to allow access to both the permissions",
                                    DialogInterface.OnClickListener { dialog, which ->
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermissions(arrayOf(Manifest.permission.CAMERA),
                                                    REQUEST_CAMERA)
                                        }
                                    })
                            return
                        }
                    }
                }
            }
        }
    }

    private fun showMessage(message: String, okListener: DialogInterface.OnClickListener) {
        android.support.v7.app.AlertDialog.Builder(this@QrCodeScannerActivity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show()
    }

    public override fun onResume() {
        super.onResume()
        mScannerView.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView.startCamera()          // Start camera on resume
    }

    override fun onPause() {
        super.onPause()
        mScannerView.stopCamera()           // Stop camera on pause
    }

    override fun handleResult(rawResult: Result) {

        // Do something with the result here
//        Log.v("TAG", rawResult.text) // Prints scan results
//        Log.v("TAG", rawResult.barcodeFormat.toString()) // Prints the scan format (qrcode, pdf417 etc.)

        // If you would like to resume scanning, call this method below:
//        mScannerView.resumeCameraPreview(this)
        makeNotification()
        finish()
    }
    fun makeNotification() {

        val preferenceHelper = PreferenceHelper(applicationContext)
        preferenceHelper.setIsNotification(true)

        EventBus.getDefault().post(PushEvent())

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel_id = createNotificationChannel(applicationContext)

        val mBuilder = NotificationCompat.Builder(this, channel_id)
                .setSmallIcon(R.drawable.ic_ocb_logo_rounded)
                .setContentTitle(getString(R.string.app_name))
                .setAutoCancel(true)

        val notificationIntent = LoginFragment.newIntent(applicationContext, "transaction_detail")
        notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val intent = PendingIntent.getActivity(applicationContext, 0,
                notificationIntent, 0)
        mBuilder.priority = NotificationCompat.PRIORITY_MAX
        mBuilder.setContentIntent(intent)
        notificationManager.notify(1, mBuilder.build())
    }
}