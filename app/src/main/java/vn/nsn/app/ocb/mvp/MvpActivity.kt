package vn.nsn.app.ocb.mvp

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import butterknife.ButterKnife
import io.reactivex.disposables.CompositeDisposable
import vn.nsn.app.ocb.PeepApp
import vn.nsn.app.ocb.R
import vn.nsn.app.ocb.helper.PreferenceHelper


abstract class MvpActivity<P : AndroidPresenter<*>> : AppCompatActivity(), AndroidView {

    protected lateinit var presenter: P
    protected val compositeDisposable = CompositeDisposable()
    lateinit var preferenceHelper: PreferenceHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceHelper = PreferenceHelper(this)

        initLocale()
        setContentView(layoutResId)
        ButterKnife.bind(this)
        initParams()
        initViews()
        setupViews()
        initPresenter()
        if (::presenter.isInitialized) {
            attachViewToPresenter()
        }
    }

    public override fun onPause() {
        super.onPause()
        (this.application as PeepApp).mLastPause = System.currentTimeMillis()
    }

    override fun onResume() {
        super.onResume()
        val app = getApplication() as PeepApp
        if (System.currentTimeMillis() - app.mLastPause > 5000) {

            // If more than 5 seconds since last pause, prompt for password
        }
    }

//     fun isAppIsInBackground(context: Context): Boolean {
//        var isInBackground = true
//        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//        val runningProcesses = am.runningAppProcesses
//        for (processInfo in runningProcesses) {
//            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
//                for (activeProcess in processInfo.pkgList) {
//                    if (activeProcess == context.packageName) {
//                        isInBackground = false
//                    }
//                }
//            }
//        }
//
//        return isInBackground
//    }


//    public override fun onResume() {
//        super.onResume()
//        val app = getApplication() as PeepApp
//        if (System.currentTimeMillis() - app.mLastPause > 5000) {
//            // If more than 5 seconds since last pause, prompt for password
//        }
//    }


    abstract fun initPresenter()

    abstract fun attachViewToPresenter()

    override fun onDestroy() {
        if (::presenter.isInitialized) {
            presenter.detachView()
        }
        compositeDisposable.clear()
        super.onDestroy()
    }

    fun startActivityWithPopupAnimation(intent: Intent, requestCode: Int?) {
        if (requestCode != null) {
            startActivityForResult(intent, requestCode)
        } else {
            startActivity(intent)
        }
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.no_anim)
    }

    fun startActivityWithSlideAnimation(intent: Intent, requestCode: Int?) {
        if (requestCode != null) {
            startActivityForResult(intent, requestCode)
        } else {
            startActivity(intent)
        }
        overridePendingTransition(R.anim.slide_in_right, R.anim.no_anim)
    }

    fun finishWithPopupAnimation() {
        finish()
        overridePendingTransition(R.anim.no_anim, R.anim.slide_out_bottom)
    }

    fun finishWithSlideAnimation() {
        finish()
        overridePendingTransition(R.anim.no_anim, R.anim.slide_out_right)
    }


    fun setupUI(view: View) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener { v, event ->
                hideSoftKeyboard(this)
                false
            }
        }

        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupUI(innerView)
            }
        }
    }

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken, 0)
    }
}