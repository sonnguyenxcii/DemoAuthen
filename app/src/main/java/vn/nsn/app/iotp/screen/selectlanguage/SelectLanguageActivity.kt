package vn.nsn.app.iotp.screen.selectlanguage

import android.content.Intent
import butterknife.OnClick
import vn.nsn.app.iotp.R
import vn.nsn.app.iotp.mvp.MvpActivity
import vn.nsn.app.iotp.screen.authen.pincode.CreatePinCodeActivity
import vn.nsn.app.iotp.screen.authen.pincode.CreatePinCodeContract
import vn.nsn.app.iotp.screen.authen.pincode.CreatePinCodePresenter
import vn.nsn.app.iotp.util.LanguageUtils

class SelectLanguageActivity : MvpActivity<CreatePinCodePresenter>(), CreatePinCodeContract {


    override fun initPresenter() {
    }

    override fun attachViewToPresenter() {
    }

    override val layoutResId: Int
        get() = R.layout.activity_select_language

    override fun initViews() {
    }


    @OnClick(R.id.tv_vn)
    fun onVnClick() {
        val languageUtils = LanguageUtils(this.baseContext)
        languageUtils.changeLang("vi",this)
        val intent = Intent(this, CreatePinCodeActivity::class.java)
        startActivity(intent)
        finish()
    }

    @OnClick(R.id.tv_en)
    fun onEnClick() {
        val languageUtils = LanguageUtils(this.baseContext)
        languageUtils.changeLang("en",this)

        val intent = Intent(this, CreatePinCodeActivity::class.java)
        startActivity(intent)
        finish()
    }


}