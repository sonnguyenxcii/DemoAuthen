package vn.nsn.app.iotp.extension

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import vn.nsn.app.iotp.api.ApiCallListener

fun <T> Single<T>.result(init: ApiCallListener<T>.() -> Unit): DisposableSingleObserver<T> {
    val listener = ApiCallListener<T>()
    listener.init()
    return subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(listener)
}

fun Disposable.addToCompositeDisposable(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}