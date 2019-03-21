package vn.nsn.app.iotp.api

import com.google.gson.GsonBuilder
import com.google.gson.JsonParseException
import io.reactivex.observers.DisposableSingleObserver
import retrofit2.HttpException
import vn.nsn.app.iotp.api.dto.ErrorDTO
import java.lang.Exception

class ApiCallListener<T> : DisposableSingleObserver<T>() {

    private var onSuccessHandler: ((T) -> Unit)? = null
    private var onErrorHandler: (() -> Unit)? = null
    private var onShowErrorHandler: ((String) -> Unit)? = null
    private var onHideLoadingHandler: (() -> Unit)? = null
    private val gson by lazy {
        GsonBuilder().setPrettyPrinting().create()
    }

    fun doOnSuccess(handler: ((T) -> Unit)?) {
        onSuccessHandler = handler
    }

    fun doOnError(handler: (() -> Unit)?) {
        onErrorHandler = handler
    }

    fun showError(handler: ((String) -> Unit)?) {
        onShowErrorHandler = handler
    }

    fun doHideLoading(handler: (() -> Unit)?) {
        onHideLoadingHandler = handler
    }

    override fun onSuccess(t: T) {
        try {
            onSuccessHandler?.invoke(t)
            onHideLoadingHandler?.invoke()
        } catch (e: Exception) {
            e.printStackTrace()
            // TODO: show general error
        }
    }

    override fun onError(e: Throwable) {
        try {
            onErrorHandler?.invoke()
            onShowErrorHandler?.invoke(getErrorMessage(e))
            onHideLoadingHandler?.invoke()
        } catch (e: Exception) {
            e.printStackTrace()
            // TODO: show general error
        }
    }

    private fun getErrorMessage(e: Throwable): String {
        return if (e is HttpException) {
            val message = e.response().errorBody()?.string() ?: ""
            return try {
                val errorDTO = gson.fromJson(message, ErrorDTO::class.java)
                errorDTO.errorMessage ?: ""
            } catch (e: JsonParseException) {
                message
            }
        } else {
            e.message ?: ""
        }
    }
}