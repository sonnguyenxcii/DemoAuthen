package vn.nsn.app.iotp.screen.tutorialprofile

import android.content.Context
import vn.nsn.app.iotp.extension.result
import vn.nsn.app.iotp.interactor.UserInteractor
import vn.nsn.app.iotp.mvp.AndroidBasePresenter

class TutorialProfilePresenter(context: Context) : AndroidBasePresenter<TutorialProfileViewContract>(context), TutorialProfilePresenterContract {

    private val userInteractor = UserInteractor(context)

    override fun updateProfile(gender: String, age: Int) {
        view?.showLoading()
        val single = userInteractor.updateProfile(gender = gender, age = age)
        val disposable = single.result {
            doOnSuccess {
                view?.onUpdateSuccess()
            }
            doHideLoading {
                view?.hideLoading()
            }
            showError {
                view?.showError(it)
            }
        }
        compositeDisposable.add(disposable)
    }

    override fun onTutorialProfileCompleted() {
        userInteractor.setNoNeedShowTutorialProfile()
    }
}