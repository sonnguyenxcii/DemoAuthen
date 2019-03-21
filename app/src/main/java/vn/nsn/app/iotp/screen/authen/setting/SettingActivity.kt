package vn.nsn.app.iotp.screen.authen.setting

import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import android.widget.Switch
import butterknife.BindView
import butterknife.OnClick
import com.samsung.android.sdk.pass.Spass
import com.samsung.android.sdk.pass.SpassFingerprint
import vn.nsn.app.iotp.R
import vn.nsn.app.iotp.mvp.MvpActivity
import vn.nsn.app.iotp.screen.authen.pincode.CreatePinCodeContract
import vn.nsn.app.iotp.screen.authen.pincode.CreatePinCodePresenter
import vn.nsn.app.iotp.view.RegularTextView


class SettingActivity : MvpActivity<CreatePinCodePresenter>(), CreatePinCodeContract {

    @BindView(R.id.tv_tittle)
    lateinit var tvTittle: RegularTextView

    @BindView(R.id.sw_biometric)
    lateinit var swBiometric: Switch

    @BindView(R.id.sw_gps)
    lateinit var swGps: Switch

    private var mSpass: Spass? = null
    private var isFeatureEnabled_fingerprint = false
    private var mSpassFingerprint: SpassFingerprint? = null

    override fun initPresenter() {
    }

    override fun attachViewToPresenter() {
    }

    override val layoutResId: Int
        get() = R.layout.activity_setting //To change initializer of created properties use File | Settings | File Templates.

    @OnClick(R.id.img_btn_back)
    fun onBackClick() {
        finish()
    }

    override fun setupViews() {

        tvTittle.text = getString(R.string.setting_app)



        swBiometric.isChecked = preferenceHelper.getBiometricLogin()!!
        swGps.isChecked = preferenceHelper.getGpsLocation()!!

        if (!checkSamsung() && !checkOther()) {
            swBiometric.isChecked = false
            swBiometric.isEnabled = false
        }

        swBiometric.setOnCheckedChangeListener { buttonView, isChecked ->
            preferenceHelper.setBiometricLogin(isChecked)
        }

        swGps.setOnCheckedChangeListener { buttonView, isChecked ->
            preferenceHelper.setGpsLocation(isChecked)

        }
    }

    private fun checkSamsung(): Boolean {

        try {
            mSpass = Spass()

            mSpass!!.initialize(this)
            isFeatureEnabled_fingerprint = mSpass!!.isFeatureEnabled(Spass.DEVICE_FINGERPRINT)

            if (isFeatureEnabled_fingerprint) {
                mSpassFingerprint = SpassFingerprint(this)
//            log("Fingerprint Service is supported in the device.")
                return true

            } else {
                return false
//            logClear()
//            log("Fingerprint Service is not supported in the device.")
            }
        } catch (e: Exception) {
            return false

//            log("Fingerprint Service is not supported in the device")
        }


    }

    private fun checkOther(): Boolean {

        val fingerprintManagerCompat = FingerprintManagerCompat.from(this@SettingActivity)

        if (!fingerprintManagerCompat.isHardwareDetected) {
            return false
            // Device doesn't support fingerprint authentication
        } else if (!fingerprintManagerCompat.hasEnrolledFingerprints()) {
            return false

            // User hasn't enrolled any fingerprints to authenticate with
        } else {
            return true
            // Everything is ready for fingerprint authentication
        }

    }

}