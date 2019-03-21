package vn.nsn.app.ocb.screen.authen.pincode.transaction

import vn.nsn.app.ocb.R
import vn.nsn.app.ocb.api.entity.TutorialStory
import vn.nsn.app.ocb.mvp.MvpActivity
import vn.nsn.app.ocb.screen.authen.pincode.CreatePinCodeContract
import vn.nsn.app.ocb.screen.authen.pincode.CreatePinCodePresenter
import vn.nsn.app.ocb.screen.splash.SplashPresenterContract
import vn.nsn.app.ocb.screen.splash.SplashViewContract

class TransactionDetailActivity : MvpActivity<CreatePinCodePresenter>(), CreatePinCodeContract {
    override fun initPresenter() {
    }

    override fun attachViewToPresenter() {
    }

    override val layoutResId: Int
        get() = R.layout.activity_transaction_detail //To change initializer of created properties use File | Settings | File Templates.



}