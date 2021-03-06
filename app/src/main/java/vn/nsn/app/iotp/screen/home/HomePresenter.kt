package vn.nsn.app.iotp.screen.home

import android.content.Context
import vn.nsn.app.iotp.extension.result
import vn.nsn.app.iotp.interactor.UserInteractor
import vn.nsn.app.iotp.mvp.AndroidBasePresenter

class HomePresenter(context: Context) : AndroidBasePresenter<HomeViewContract>(context), HomePresenterContract {

    private val userInteractor = UserInteractor(context)

    override fun setupPush() {
        if (userInteractor.needShowAcceptPush()) {
            view?.showAcceptPush()
        } else {
            uploadDeviceToken()
        }
    }

    private fun uploadDeviceToken() {
        if (!userInteractor.isDeviceTokenUploaded()) {
            val single = userInteractor.uploadDeviceToken(userInteractor.getDeviceToken())
            val disposable = single.result {
                doOnSuccess {
                    userInteractor.setDeviceTokenUploaded(true)
                }
            }
            compositeDisposable.add(disposable)
        }
    }

    override fun checkShowTutorialProfile() {
        if (userInteractor.needShowTutorialProfile()) {
            view?.showTutorialProfile()
        }
    }
}