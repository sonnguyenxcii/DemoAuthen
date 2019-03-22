package vn.nsn.app.iotp.screen.main

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.TaskStackBuilder
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RemoteViews
import butterknife.BindView
import butterknife.OnClick
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import vn.nsn.app.iotp.R
import vn.nsn.app.iotp.extension.textColor
import vn.nsn.app.iotp.mvp.MvpActivity
import vn.nsn.app.iotp.screen.authen.pincode.CreatePinCodeContract
import vn.nsn.app.iotp.screen.authen.pincode.CreatePinCodePresenter
import vn.nsn.app.iotp.screen.authen.setting.SettingActivity
import vn.nsn.app.iotp.screen.qrcode.QrCodeScannerActivity
import vn.nsn.app.iotp.screen.transaction.TransactionDetailActivity
import vn.nsn.app.iotp.util.LanguageUtils
import vn.nsn.app.iotp.view.RegularTextView


class MainActivity : MvpActivity<CreatePinCodePresenter>(), CreatePinCodeContract {

//    @BindView(R.id.tv_message)
//    lateinit var tvMessage: RegularTextView
//
//    @BindView(R.id.tv_vn)
//    lateinit var tvVn: RegularTextView
//
//    @BindView(R.id.tv_en)
//    lateinit var tvEn: RegularTextView
//
//    @BindView(R.id.img_setting)
//    lateinit var imgSetting: ImageView
//
    @BindView(R.id.sheet)
    lateinit var sheet: View
//
    @BindView(R.id.conduct)
    lateinit var conduct: Button

    override fun initPresenter() {
    }

    override fun attachViewToPresenter() {
    }

    override val layoutResId: Int
        get() = R.layout.activity_main //To change initializer of created properties use File | Settings | File Templates.


    lateinit var languageUtils: LanguageUtils

    override fun initLocale() {
        languageUtils = LanguageUtils(this)
        val lang = languageUtils.loadLocale(this)
        languageUtils.changeLang(lang, this)
    }


    override fun setupViews() {


    }


    fun makeNotification() {
        val remoteViews = RemoteViews(packageName, R.layout.activity_custom_notification)

        val mBuilder = NotificationCompat.Builder(this, "OCB_CN_ID")
                .setSmallIcon(R.drawable.ic_ocb_logo_rounded)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.notif_content))
                .setAutoCancel(true)
// Creates an explicit intent for an Activity in your app
        val resultIntent = Intent(this, TransactionDetailActivity::class.java)

        val stackBuilder = TaskStackBuilder.create(this)
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(TransactionDetailActivity::class.java)
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent)
        val resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        mBuilder.priority = NotificationCompat.PRIORITY_MAX
        mBuilder.setContentIntent(resultPendingIntent)
        mBuilder.setCustomBigContentView(remoteViews)
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
// mId allows you to update the notification later on.
        mNotificationManager.notify(1000, mBuilder.build())
    }

    fun getDeviceName(): String? {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer)) {
            capitalize(model)
        } else capitalize(manufacturer) + " " + model
    }

    private fun capitalize(str: String): String? {
        if (TextUtils.isEmpty(str)) {
            return str
        }
        val arr = str.toCharArray()
        var capitalizeNext = true

        val phrase = StringBuilder()
        for (c in arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c))
                capitalizeNext = false
                continue
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true
            }
            phrase.append(c)
        }

        return phrase.toString()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: PushEvent) {

        showNotification()
    }

    private fun showNotification() {
        sheet.visibility = View.VISIBLE
//        tittlePush.text = preferenceHelper.getName()
//
        sheet.setOnClickListener {
            val intent = Intent(this, TransactionDetailActivity::class.java)
            startActivity(intent)
            sheet.visibility = View.GONE
            preferenceHelper.setIsNotification(false)
            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancelAll()
        }

    }

    override fun onResume() {
        super.onResume()
        if (preferenceHelper.getIsNotification()) {
            showNotification()
        }

    }

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @OnClick(R.id.conduct)
     fun onConductClick() {
        val intent = Intent(this, QrCodeScannerActivity::class.java)
            startActivity(intent)
    }
}