package vn.nsn.app.iotp.screen.transaction

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.os.Build
import android.support.constraint.ConstraintLayout
import android.text.TextUtils
import android.util.Base64
import android.view.View
import android.widget.LinearLayout
import butterknife.BindView
import butterknife.OnClick
import com.centagate.module.account.AccountInfo
import com.centagate.module.authentication.AuthenticationService
import com.centagate.module.common.CompleteEntity
import com.centagate.module.common.Configuration
import com.centagate.module.common.ErrorDetails
import com.centagate.module.device.FileSystem
import com.centagate.module.device.PinAuthentication
import com.centagate.module.exception.CentagateException
import com.centagate.module.log.Logger
import com.centagate.module.otp.OtpService
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.xml.sax.InputSource
import vn.nsn.app.iotp.R
import vn.nsn.app.iotp.extension.textColor
import vn.nsn.app.iotp.helper.DialogHelper
import vn.nsn.app.iotp.mvp.MvpActivity
import vn.nsn.app.iotp.screen.LoginFragment2
import vn.nsn.app.iotp.screen.authen.pincode.CreatePinCodeContract
import vn.nsn.app.iotp.screen.authen.pincode.CreatePinCodePresenter
import vn.nsn.app.iotp.util.LanguageUtils
import vn.nsn.app.iotp.util.LogUtils
import vn.nsn.app.iotp.view.RegularTextView
import java.io.ByteArrayInputStream
import java.io.StringReader
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.xml.parsers.DocumentBuilderFactory


class TransactionDetailActivity : MvpActivity<CreatePinCodePresenter>(), CreatePinCodeContract {

    @BindView(R.id.tv_tittle)
    lateinit var tvTittle: RegularTextView

    @BindView(R.id.toolbar)
    lateinit var toolbar: ConstraintLayout

    @BindView(R.id.tv_from_account)
    lateinit var tvFromAccount: RegularTextView
    //
    @BindView(R.id.tv_from_account_value)
    lateinit var tvFromAccountValue: RegularTextView

    @BindView(R.id.tv_trans_id)
    lateinit var tvTransId: RegularTextView

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

    var dataExtra: String = ""
    var randomString: String = ""

    @BindView(R.id.ln_trans_id)
    lateinit var ln_trans_id: LinearLayout
    @BindView(R.id.ln_from)
    lateinit var ln_from: LinearLayout
    @BindView(R.id.ln_to)
    lateinit var ln_to: LinearLayout
    @BindView(R.id.ln_money)
    lateinit var ln_money: LinearLayout

    @BindView(R.id.ln_time)
    lateinit var ln_time: LinearLayout

    override fun initPresenter() {
    }

    override fun attachViewToPresenter() {
    }

    lateinit var languageUtils: LanguageUtils


    override fun initLocale() {
        languageUtils = LanguageUtils(this)
        val lang = languageUtils.loadLocale(this)
        languageUtils.changeLang(lang, this)
    }

    override val layoutResId: Int
        get() = R.layout.activity_transaction_detail //To change initializer of created properties use File | Settings | File Templates.

    override fun setupViews() {

        tvTittle.text = getString(R.string.transaction_approval)


    }

    @OnClick(R.id.btn_submit)
    fun onSubmitClick() {


    }

    @OnClick(R.id.btn_cancel)
    fun onCancelClick() {

        finish()
    }

    @OnClick(R.id.img_btn_back)
    fun onBackClick() {
        finish()
    }

    fun onNext() {
        val notificationIntent = LoginFragment2.newIntent(applicationContext, preferenceHelper.getSession(), randomString)
        startActivity(notificationIntent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            val intent = Intent(this, OtpAdvanceActivity::class.java)
            intent.putExtra("data", dataExtra)
            intent.putExtra("randomString", randomString)
            startActivity(intent)
            finish()
        }
    }

    override fun onStop() {
        super.onStop()
        preferenceHelper.setIsNotification(false)
    }
}