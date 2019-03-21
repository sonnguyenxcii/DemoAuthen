package vn.nsn.app.ocb.screen.splash

import android.content.Context
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import vn.nsn.app.ocb.mvp.AndroidBasePresenter
import java.util.concurrent.TimeUnit

class SplashPresenter(context: Context) : AndroidBasePresenter<SplashViewContract>(context), SplashPresenterContract {
    private val showPeepSubject: PublishSubject<Any> = PublishSubject.create()

    init {
        compositeDisposable.add(showPeepSubject.debounce(3000, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    view?.gotoNext()
                })
    }

    override fun attachView(view: SplashViewContract) {
        super.attachView(view)
        showPeepSubject.onNext(true)

    }

}