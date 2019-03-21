package vn.nsn.app.ocb.screen.transaction

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator
import butterknife.BindView
import butterknife.OnClick
import com.centagate.module.account.Account
import com.centagate.module.common.CompleteEntity
import com.centagate.module.device.FileSystem
import com.centagate.module.device.PinAuthentication
import com.centagate.module.otp.OtpOperation
import com.crashlytics.android.Crashlytics
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xml.sax.InputSource
import vn.nsn.app.ocb.R
import vn.nsn.app.ocb.extension.textColor
import vn.nsn.app.ocb.mvp.MvpActivity
import vn.nsn.app.ocb.screen.authen.pincode.CreatePinCodeContract
import vn.nsn.app.ocb.screen.authen.pincode.CreatePinCodePresenter
import vn.nsn.app.ocb.util.CryptoUtils
import vn.nsn.app.ocb.view.RegularTextView
import java.io.StringReader
import java.util.*
import java.util.concurrent.TimeUnit
import javax.xml.parsers.DocumentBuilderFactory

class OtpAdvanceActivity : MvpActivity<CreatePinCodePresenter>(), CreatePinCodeContract {

    @BindView(R.id.tv_tittle)
    lateinit var tvTittle: RegularTextView

    @BindView(R.id.edt_active_code)
    lateinit var edtActiveCode: EditText

    @BindView(R.id.v_count_down)
    lateinit var vCountDownTimer: CircularProgressIndicator

    @BindView(R.id.tv_from_account)
    lateinit var tvFromAccount: RegularTextView
    //
    @BindView(R.id.tv_from_account_value)
    lateinit var tvFromAccountValue: RegularTextView

    @BindView(R.id.tv_trans_id)
    lateinit var tvTransId: RegularTextView
    //
    @BindView(R.id.tv_trans_id_value)
    lateinit var tvTransIdValue: RegularTextView

    @BindView(R.id.tv_to_account)
    lateinit var tvToAccount: RegularTextView
    //
    @BindView(R.id.tv_to_account_value)
    lateinit var tvToAccountValue: RegularTextView

    @BindView(R.id.tv_money)
    lateinit var tvMoney: RegularTextView
    //
    @BindView(R.id.tv_money_value)
    lateinit var tvMoneyValue: RegularTextView

    @BindView(R.id.tv_time)
    lateinit var tvTime: RegularTextView
    //
    @BindView(R.id.tv_time_value)
    lateinit var tvTimeValue: RegularTextView

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

    @BindView(R.id.ln_trans_id)
    lateinit var ln_trans_id: LinearLayout
    @BindView(R.id.ln_from)
    lateinit var ln_from: LinearLayout
    @BindView(R.id.ln_to)
    lateinit var ln_to: LinearLayout
    @BindView(R.id.ln_money)
    lateinit var ln_money: LinearLayout

    lateinit var randomString: String
    lateinit var data: String
    override fun initPresenter() {
    }

    override fun attachViewToPresenter() {
    }

    override val layoutResId: Int
        get() = R.layout.activity_otp_advandce //To change initializer of created properties use File | Settings | File Templates.

    override fun setupViews() {

        tvTittle.text = getString(R.string.transaction_approval)

        try {
            data = intent.extras.getString("data")
            randomString = intent.extras.getString("randomString")

        } catch (e: Exception) {
            randomString = ""
            data = ""
        }
        var challenge = CryptoUtils.getChallengeCode(randomString, preferenceHelper.getSession())
//        var finalchallenge = CryptoUtils.getChallengeCode((challenge + preferenceHelper.getSession()))
//        challenge = finalchallenge
        println("------------challenge-----" + challenge)

        edtActiveCode.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                bindView()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        val completeEntity = getAllData(this)
        if (completeEntity != null) {
            val account = completeEntity.accounts[0]
//            generateTimeOtp(account)
            val otpOperation = OtpOperation()
            val security = PinAuthentication()
            security.setPin(preferenceHelper.getPincode())

//            val otpResult = otpOperation.generateSignOtp(account.otpInfo.getRandomData(), account.otpInfo, security)
//            edt_active_code.setText(otpResult)
            var otp = otpOperation.generateSignOtp(challenge, account.otpInfo, security)
            println("-----------------------------otp---------------" + otp)
            edtActiveCode.setText(otp)

        }
        val calendar = Calendar.getInstance()

//        System.out.println("Seconds in current minute = " + calendar.get(Calendar.SECOND))
        var seconds = calendar.get(Calendar.SECOND)
        println("-----------------------" + seconds)
        pasrser(data)

        // you can set max and current progress values individually
        vCountDownTimer.maxProgress = 60.0


        vCountDownTimer.setCurrentProgress(seconds.toDouble())

        compositeDisposable.add(
                Observable.interval(1, TimeUnit.SECONDS)
                        .take(seconds.toLong())
                        .observeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            var current = seconds - it - 1
                            vCountDownTimer.setCurrentProgress(current.toDouble())

                            if (current.toDouble() <= 15) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    vCountDownTimer.progressColor = resources.getColor(R.color.holoRed, null)
                                    vCountDownTimer.dotColor = resources.getColor(R.color.holoRed, null)
                                    vCountDownTimer.textColor = resources.getColor(R.color.holoRed, null)
                                } else {
                                    vCountDownTimer.progressColor = resources.getColor(R.color.holoRed)
                                    vCountDownTimer.dotColor = resources.getColor(R.color.holoRed)
                                    vCountDownTimer.textColor = resources.getColor(R.color.holoRed)
                                }
                            }
                        })


//        val completeEntity = getAllData(this)
//        if (completeEntity != null) {
//            val account = completeEntity.accounts[0]
//            generateTimeOtp(account)
//            val otpOperation = OtpOperation()
//            val security = PinAuthentication()
//            security.setPin("123456")
//            val otpResult = otpOperation.generateSignOtp(account.otpInfo.getRandomData(), account.otpInfo, security)
//            edt_active_code.setText(otpResult)
//
//
//        }
//        populateTable(completeEntity);
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

    private fun pasrser(data: String) {
        try {
//            val inputStream = assets.open("file.xml")
//            val inputStream = StringReader(preferenceHelper.getSession())

            val dbFactory = DocumentBuilderFactory.newInstance()
            val dBuilder = dbFactory.newDocumentBuilder()
            val string = "<?xml version=\"1.0\"?>$data"

            val doc = dBuilder.parse(InputSource(StringReader(string)))

            val element = doc.documentElement
            element.normalize()

            val field1s = doc.getElementsByTagName("Field2")
            if (field1s.getLength() > 1) {
                if (preferenceHelper.getLang() == "vi") {
                    val node = field1s.item(0)
                    if (node.getNodeType() === Node.ELEMENT_NODE) {
                        val element2 = node as Element

                        tvFromAccount.text = getValue("Caption", element2)
                        tvFromAccountValue.text = getValue("Data", element2)
                        tvFromAccountValue.textColor = Color.parseColor("#" + getValue("Color", element2))

                    }
                } else {
                    val node = field1s.item(1)
                    if (node.getNodeType() === Node.ELEMENT_NODE) {
                        val element2 = node as Element

                        tvFromAccount.text = getValue("Caption", element2)
                        tvFromAccountValue.text = getValue("Data", element2)
                        tvFromAccountValue.textColor = Color.parseColor("#" + getValue("Color", element2))

                    }
                }
            } else {
                ln_from.visibility = View.GONE
            }

            val field2s = doc.getElementsByTagName("Field1")
            if (field2s.getLength() > 1) {
                if (preferenceHelper.getLang() == "vi") {
                    val node = field2s.item(0)
                    if (node.getNodeType() === Node.ELEMENT_NODE) {
                        val element2 = node as Element

                        tvTransId.text = getValue("Caption", element2)
                        tvTransIdValue.text = getValue("Data", element2)
                        tvTransIdValue.textColor = Color.parseColor("#" + getValue("Color", element2))

                    }
                } else {
                    val node = field2s.item(1)
                    if (node.getNodeType() === Node.ELEMENT_NODE) {
                        val element2 = node as Element

                        tvTransId.text = getValue("Caption", element2)
                        tvTransIdValue.text = getValue("Data", element2)
                        tvTransIdValue.textColor = Color.parseColor("#" + getValue("Color", element2))

                    }
                }
            } else {
                ln_from.visibility = View.GONE
            }

            val field3s = doc.getElementsByTagName("Field3")
            if (field3s.getLength() > 1) {
                if (preferenceHelper.getLang() == "vi") {
                    val node = field3s.item(0)
                    if (node.getNodeType() === Node.ELEMENT_NODE) {
                        val element2 = node as Element
                        tvToAccount.text = getValue("Caption", element2)
                        tvToAccountValue.text = getValue("Data", element2)
                        tvToAccountValue.textColor = Color.parseColor("#" + getValue("Color", element2))

                    }
                } else {
                    val node = field3s.item(1)
                    if (node.getNodeType() === Node.ELEMENT_NODE) {
                        val element2 = node as Element
                        tvToAccount.text = getValue("Caption", element2)
                        tvToAccountValue.text = getValue("Data", element2)
                        tvToAccountValue.textColor = Color.parseColor("#" + getValue("Color", element2))

                    }
                }
            } else {
                ln_to.visibility = View.GONE
            }

            val field4s = doc.getElementsByTagName("Field4")
            if (field4s.getLength() > 1) {
                if (preferenceHelper.getLang() == "vi") {
                    val node = field4s.item(0)
                    if (node.getNodeType() === Node.ELEMENT_NODE) {
                        val element2 = node as Element
                        tvMoney.text = getValue("Caption", element2)
                        tvMoneyValue.text = getValue("Data", element2)
                        tvMoneyValue.textColor = Color.parseColor("#" + getValue("Color", element2))

                    }
                } else {
                    val node = field4s.item(1)
                    if (node.getNodeType() === Node.ELEMENT_NODE) {
                        val element2 = node as Element
                        tvMoney.text = getValue("Caption", element2)
                        tvMoneyValue.text = getValue("Data", element2)
                        tvMoneyValue.textColor = Color.parseColor("#" + getValue("Color", element2))

                    }
                }
            } else {
                ln_money.visibility = View.GONE
            }
            val field5s = doc.getElementsByTagName("Field5")
            if (field5s.getLength() > 1) {
                if (preferenceHelper.getLang() == "vi") {
                    val node = field5s.item(0)
                    if (node.getNodeType() === Node.ELEMENT_NODE) {
                        val element2 = node as Element
                        tvTime.text = getValue("Caption", element2)
                        tvTimeValue.text = getValue("Data", element2)
                        tvTimeValue.textColor = Color.parseColor("#" + getValue("Color", element2))

                    }
                } else {
                    val node = field5s.item(1)
                    if (node.getNodeType() === Node.ELEMENT_NODE) {
                        val element2 = node as Element
                        tvTime.text = getValue("Caption", element2)
                        tvTimeValue.text = getValue("Data", element2)
                        tvTimeValue.textColor = Color.parseColor("#" + getValue("Color", element2))

                    }
                }
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }


    }

    private fun getAllData(context: Context): CompleteEntity? {

        val fileName = getString(R.string.file_name)// make sure always remember the file name
        val fileSystem = FileSystem()
        try {
            val completeEntity = fileSystem.getAccountsFromFile(fileName, context)

            for (account in completeEntity.accounts) {
                account.accountInfo
                account.otpInfo
            }
            val securityDevice = PinAuthentication()
            securityDevice.setPin(preferenceHelper.getPincode()) //please remember the PIN you set
            val otpOperation = OtpOperation()
            val otpResult = otpOperation.generateTOtp(completeEntity.accounts[0].otpInfo, securityDevice)
            completeEntity.deviceInfo

            return completeEntity
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    @OnClick(R.id.img_btn_back)
    fun onBackClick() {
        finish()
    }

    @OnClick(R.id.tv_ok)
    fun onOkClick() {
        finish()
    }

    private fun getValue(tag: String, element: Element): String {
        val nodeList = element.getElementsByTagName(tag).item(0).getChildNodes()
        val node = nodeList.item(0)
        return node.getNodeValue()
    }


    private fun generateTimeOtp(account: Account): String {
        val otpOperation = OtpOperation()
        val security = PinAuthentication()
        security.setPin("123456")
        var timeOtpString = "0"
        try {
            timeOtpString = otpOperation.generateTOtp(account.otpInfo, security)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return timeOtpString

    }
}