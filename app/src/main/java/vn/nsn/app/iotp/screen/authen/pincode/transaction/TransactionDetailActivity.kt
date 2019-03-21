package vn.nsn.app.iotp.screen.authen.pincode.transaction

import vn.nsn.app.iotp.R
import vn.nsn.app.iotp.mvp.MvpActivity
import vn.nsn.app.iotp.screen.authen.pincode.CreatePinCodeContract
import vn.nsn.app.iotp.screen.authen.pincode.CreatePinCodePresenter

class TransactionDetailActivity : MvpActivity<CreatePinCodePresenter>(), CreatePinCodeContract {
    override fun initPresenter() {
    }

    override fun attachViewToPresenter() {
    }

    override val layoutResId: Int
        get() = R.layout.activity_transaction_detail //To change initializer of created properties use File | Settings | File Templates.



}