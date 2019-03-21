package vn.nsn.app.ocb.screen.active

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.telephony.TelephonyManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import butterknife.OnClick
import com.centagate.module.account.Account
import com.centagate.module.account.AccountService
import com.centagate.module.common.BindInfo
import com.centagate.module.common.CompleteEntity
import com.centagate.module.common.Configuration
import com.centagate.module.common.ErrorDetails
import com.centagate.module.device.FileSystem
import com.centagate.module.device.PinAuthentication
import com.centagate.module.exception.CentagateException
import com.centagate.module.otp.OtpInfo
import com.centagate.module.otp.OtpOperation
import com.centagate.module.otp.OtpService
import com.google.android.gms.gcm.GoogleCloudMessaging
import com.google.android.gms.iid.InstanceID
import vn.nsn.app.ocb.R
import vn.nsn.app.ocb.extension.textColor
import vn.nsn.app.ocb.mvp.MvpActivity
import vn.nsn.app.ocb.screen.authen.pincode.CreatePinCodeContract
import vn.nsn.app.ocb.screen.authen.pincode.CreatePinCodePresenter
import vn.nsn.app.ocb.screen.main.MainActivity
import vn.nsn.app.ocb.util.LanguageUtils
import vn.nsn.app.ocb.view.RegularTextView
import java.util.*


class ActiveAppActivity : MvpActivity<CreatePinCodePresenter>(), CreatePinCodeContract {

    @BindView(R.id.tv_tittle)
    lateinit var tvTittle: RegularTextView

    @BindView(R.id.edt_username)
    lateinit var edtUsername: EditText

    @BindView(R.id.edt_active_code)
    lateinit var edtActiveCode: EditText

    @BindView(R.id.tv_code_1)
    lateinit var tvCode1: RegularTextView

    @BindView(R.id.tv_code_2)
    lateinit var tvCode2: RegularTextView

    @BindView(R.id.tv_code_3)
    lateinit var tvCode3: RegularTextView

    @BindView(R.id.tv_code_4)
    lateinit var tvCode4: RegularTextView

    @BindView(R.id.tv_code_5)
    lateinit var tvCode5: RegularTextView

    @BindView(R.id.tv_code_6)
    lateinit var tvCode6: RegularTextView

//    @BindView(R.id.tv_code_7)
//    lateinit var tvCode7: RegularTextView

//    @BindView(R.id.tv_code_8)
//    lateinit var tvCode8: RegularTextView

    @BindView(R.id.root)
    lateinit var root: ConstraintLayout
    lateinit var languageUtils: LanguageUtils

    @BindView(R.id.tv_vn)
    lateinit var tvVn: RegularTextView

    @BindView(R.id.tv_en)
    lateinit var tvEn: RegularTextView
    //    var phoneIMEI = ""
    var MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 0
    var MY_PERMISSIONS_READ_PHONE_STATE = 1
    var TOKEN = "NA"
    //    private val notificationId = "NA" //GCM notification register ID
    private var phoneIMEI = "12345678"
    private var model = "Samsung Note 4"

    override fun initPresenter() {
    }

    override fun attachViewToPresenter() {
    }

    override val layoutResId: Int
        get() = R.layout.activity_active_app

    override fun initViews() {

        tvTittle.text = getString(R.string.active_app)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);

            }
        }


        val completeEntity = getAllData(applicationContext)
        if (completeEntity != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getAllData(context: Context): CompleteEntity? {
        val fileName = getString(R.string.file_name)//"EXAMPLE_NAME"// make sure always remember the file name

        val fileSystem = FileSystem()
        try {
            val completeEntity = fileSystem.getAccountsFromFile(fileName, context)

//            for (account in completeEntity.accounts) {
//                account.accountInfo
//                account.otpInfo
//            }
//            val securityDevice = PinAuthentication()
//            securityDevice.setPin(preferenceHelper.getPincode()) //please remember the PIN you set
//            val otpOperation = OtpOperation()
//            val otpResult = otpOperation.generateTOtp(completeEntity.accounts[0].otpInfo, securityDevice)
//            completeEntity.deviceInfo

            return completeEntity
        } catch (e: Exception) {
//            Toast.makeText(this@ActiveAppActivity, e.message, Toast.LENGTH_SHORT).show()
//            e.printStackTrace()
            return null
        }

    }

    fun requestImei() {
        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE), MY_PERMISSIONS_READ_PHONE_STATE)
        } else {
            val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                phoneIMEI = telephonyManager.imei
            } else {
                phoneIMEI = telephonyManager.deviceId
            }
        }
    }

    override fun setupViews() {
        requestImei()
        loadLang()
        setupUI(root)
        edtActiveCode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                bindView()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

    }

    @OnClick(R.id.tv_vn)
    fun OnVnClick() {
        changeLang("vi")

    }

    @OnClick(R.id.tv_en)
    fun OnEnClick() {
        changeLang("en")

    }

    override fun initLocale() {
        languageUtils = LanguageUtils(this)
        val lang = languageUtils.loadLocale(this)
        languageUtils.changeLang(lang, this)
    }

    fun loadLang() {
        val lang = languageUtils.loadLocale(this)
        if (lang.equals("vi")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvVn.textColor = getColor(R.color.black)
                tvEn.textColor = getColor(R.color.gray)

            } else {
                tvVn.textColor = resources.getColor(R.color.black)
                tvEn.textColor = resources.getColor(R.color.gray)

            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvVn.textColor = getColor(R.color.gray)
                tvEn.textColor = getColor(R.color.black)

            } else {
                tvVn.textColor = resources.getColor(R.color.gray)
                tvEn.textColor = resources.getColor(R.color.black)
            }

        }
    }

    fun changeLang(type: String) {

        if (type == "vi") {
            languageUtils.changeLang("vi", this)
        } else {
            languageUtils.changeLang("en", this)

        }

        val intent = Intent(this, ActiveAppActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun bindView() {

        tvCode1.text = ""
        tvCode2.text = ""
        tvCode3.text = ""
        tvCode4.text = ""
        tvCode5.text = ""
        tvCode6.text = ""
//        tvCode7.text = ""
//        tvCode8.text = ""


        when (edtActiveCode.text.length) {
            1 -> {
                tvCode1.text = edtActiveCode.text[0].toString()

            }
            2 -> {
                tvCode1.text = edtActiveCode.text[0].toString()
                tvCode2.text = edtActiveCode.text[1].toString()
            }
            3 -> {
                tvCode1.text = edtActiveCode.text[0].toString()
                tvCode2.text = edtActiveCode.text[1].toString()
                tvCode3.text = edtActiveCode.text[2].toString()

            }
            4 -> {
                tvCode1.text = edtActiveCode.text[0].toString()
                tvCode2.text = edtActiveCode.text[1].toString()
                tvCode3.text = edtActiveCode.text[2].toString()
                tvCode4.text = edtActiveCode.text[3].toString()

            }
            5 -> {
                tvCode1.text = edtActiveCode.text[0].toString()
                tvCode2.text = edtActiveCode.text[1].toString()
                tvCode3.text = edtActiveCode.text[2].toString()
                tvCode4.text = edtActiveCode.text[3].toString()
                tvCode5.text = edtActiveCode.text[4].toString()
            }
            6 -> {
                tvCode1.text = edtActiveCode.text[0].toString()
                tvCode2.text = edtActiveCode.text[1].toString()
                tvCode3.text = edtActiveCode.text[2].toString()
                tvCode4.text = edtActiveCode.text[3].toString()
                tvCode5.text = edtActiveCode.text[4].toString()
                tvCode6.text = edtActiveCode.text[5].toString()

            }
            7 -> {
                tvCode1.text = edtActiveCode.text[0].toString()
                tvCode2.text = edtActiveCode.text[1].toString()
                tvCode3.text = edtActiveCode.text[2].toString()
                tvCode4.text = edtActiveCode.text[3].toString()
                tvCode5.text = edtActiveCode.text[4].toString()
                tvCode6.text = edtActiveCode.text[5].toString()
//                tvCode7.text = edtActiveCode.text[6].toString()

            }
            8 -> {
                tvCode1.text = edtActiveCode.text[0].toString()
                tvCode2.text = edtActiveCode.text[1].toString()
                tvCode3.text = edtActiveCode.text[2].toString()
                tvCode4.text = edtActiveCode.text[3].toString()
                tvCode5.text = edtActiveCode.text[4].toString()
                tvCode6.text = edtActiveCode.text[5].toString()
//                tvCode7.text = edtActiveCode.text[6].toString()
//                tvCode8.text = edtActiveCode.text[7].toString()

            }
            else -> { // Note the block
                print("x is neither 1 nor 2")
            }
        }
    }

    @OnClick(R.id.btn_active)
    fun onActiveClick() {
        if (TextUtils.isEmpty(edtUsername.text) || TextUtils.isEmpty(edtActiveCode.text)) {
//            val intent = Intent(this@ActiveAppActivity, MainActivity::class.java)
//            startActivity(intent)
//            finish()
        } else {
            ActivateProcess().execute()
        }

    }

    @OnClick(R.id.img_btn_back)
    fun onBackClick() {
        finish()
    }

    @SuppressLint("HardwareIds")
    @Throws(Exception::class)
    fun activateSample(): Boolean? {

        var result: Boolean? = false

        val accountService = AccountService()

        //this will be the security key of every important data in the SDK
        val securityDevice = PinAuthentication()
        //set Pin or Password
        securityDevice.setPin(preferenceHelper.getPincode())

        securityDevice.keyInfo
        securityDevice.setkeyInfo("")

        //to authenticate the pin or password , use this if there is existing PIN or password. return true if the PIN matched
        //securityDevice.authenticate("123456");
        model = this.getDeviceName().toString()

        //username and activation code
        val username = edtUsername.getText().toString()
        val activationCode = edtActiveCode.getText().toString()

        val instanceID = InstanceID.getInstance(this);
        TOKEN = instanceID.getToken("8283403344", GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

        try {
            //validate the username and activation code
            val sessionInfo = accountService.onlineProvisioning(username, activationCode)

            //activating or binding to the server
            val bindInfo = accountService.bindComplete(sessionInfo, TOKEN, phoneIMEI,
                    "Android " + Build.VERSION.RELEASE, model, Build.SERIAL, null, securityDevice)

            val accountInfo = bindInfo.account.accountInfo//Account Information

            val otpInfo = bindInfo.account.otpInfo//OTP Information
            val otpEnabled = accountInfo.isOtpEnabled//if the user has OTP in the server
            val deviceInfo = bindInfo.deviceInfo//device information, every device should have only one device information.

//            accountInfo.

            if (otpEnabled!!) {
                //if user has OTP direct to activate User & OTP
                val resultSuccess = accountService.updateBindCompleteStatus(sessionInfo, Build.SERIAL, accountInfo, otpInfo, securityDevice)
                if (resultSuccess!!) {
                    //save user data
                    saveAccount(applicationContext, bindInfo)
                    //User Has OTP
                    val account = Account(accountInfo, otpInfo)
                    val otpOperation = OtpOperation()
                    //get Time OTP on current time.
                    val timeOtp = otpOperation.generateTOtp(otpInfo, securityDevice)
                    //the time otp will based on the date parameter.
                    val customTimeOtp = otpOperation.generateTOtp(otpInfo, Date(), securityDevice)
                    //Change Request OTP
                    val challenge = "123456"
                    val crOtp = otpOperation.generateCrOtp(challenge, otpInfo, securityDevice)
                    //synchronize OTP
                    val otpService = OtpService()
                    val synchronizeStatus = otpService.syncOtp(Build.SERIAL, account, securityDevice)
                    result = true

                }
            } else {
                //if user doesn't has OTP direct to only activate user
                val resultUpdate = accountService.updateBindStatus(sessionInfo, accountInfo, securityDevice)
                saveAccount(applicationContext, bindInfo)
                if (resultUpdate!!) {
                    //User doesn't has OTP
                    val account = Account(accountInfo, OtpInfo())
                    result = true
                }
            }


        } catch (e: Exception) {

            println("-----------------------"+e.message)
            e.printStackTrace()
            throw e
        }

        return result
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_READ_PHONE_STATE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        phoneIMEI = telephonyManager.imei
                    } else {
                        phoneIMEI = telephonyManager.deviceId
                    }
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }

    internal inner class ActivateProcess : AsyncTask<Int, Void, Int>() {
        override fun doInBackground(vararg params: Int?): Int {
            var result: Boolean? = false
            try {
                result = activateSample()
            } catch (e: CentagateException) {
                return e.errorCode
            } catch (e: Exception) {
                return 123
            }

            return 1
        }

        var progressDialog: ProgressDialog? = null


        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog = ProgressDialog(this@ActiveAppActivity)
            progressDialog!!.setTitle("")
            progressDialog!!.setCancelable(false)
            progressDialog!!.show()
        }

        override fun onProgressUpdate(vararg values: Void) {
            super.onProgressUpdate(*values)
        }

        override fun onPostExecute(param: Int?) {
            progressDialog!!.dismiss()
            if (param == 1) {
//                val completeEntity = getAllData(applicationContext)
                Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@ActiveAppActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
//                populateTable(completeEntity)

            } else {
                Toast.makeText(this@ActiveAppActivity, ErrorDetails.getErrorString(param!!), Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun saveAccount(context: Context, bindInfo: BindInfo): Boolean? {
        val fileSystem = FileSystem()
        val account = bindInfo.account //All this account will get from accountService.bindComplete

        val accounts = ArrayList<Account>()
        accounts.add(account) //our SDK only accept List of account.
        val securityDevice = PinAuthentication()
        securityDevice.setPin(preferenceHelper.getPincode()) //please remember the PIN you set
        val fileName = getString(R.string.file_name) // make sure remember the file name.
        var saveResult: Boolean? = false
        try {
            saveResult = fileSystem.saveAccountsToFile(accounts, bindInfo.getDeviceInfo(), Configuration.getInstance(), securityDevice, fileName, context)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

        return saveResult
    }

    private fun getDeviceName(): String? {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer)) {
            capitalize(model)
        } else capitalize(manufacturer) + " " + model
    }

    private fun capitalize(str: String): String? {
        if (TextUtils.isEmpty(str)) {
            return str
        }
        val arr = str.toCharArray()
        var capitalizeNext = true

        val phrase = StringBuilder()
        for (c in arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c))
                capitalizeNext = false
                continue
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true
            }
            phrase.append(c)
        }

        return phrase.toString()
    }
}