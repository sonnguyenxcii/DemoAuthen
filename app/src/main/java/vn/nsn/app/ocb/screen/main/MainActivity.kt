package vn.nsn.app.ocb.screen.main

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
import android.widget.ImageView
import android.widget.RemoteViews
import butterknife.BindView
import butterknife.OnClick
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import vn.nsn.app.ocb.R
import vn.nsn.app.ocb.extension.textColor
import vn.nsn.app.ocb.mvp.MvpActivity
import vn.nsn.app.ocb.screen.authen.pincode.CreatePinCodeContract
import vn.nsn.app.ocb.screen.authen.pincode.CreatePinCodePresenter
import vn.nsn.app.ocb.screen.authen.setting.SettingActivity
import vn.nsn.app.ocb.screen.transaction.TransactionDetailActivity
import vn.nsn.app.ocb.util.LanguageUtils
import vn.nsn.app.ocb.view.RegularTextView


class MainActivity : MvpActivity<CreatePinCodePresenter>(), CreatePinCodeContract {

    @BindView(R.id.tv_message)
    lateinit var tvMessage: RegularTextView

    @BindView(R.id.tv_vn)
    lateinit var tvVn: RegularTextView

    @BindView(R.id.tv_en)
    lateinit var tvEn: RegularTextView

    @BindView(R.id.img_setting)
    lateinit var imgSetting: ImageView

    @BindView(R.id.sheet)
    lateinit var sheet: View

    @BindView(R.id.tittle_push)
    lateinit var tittlePush: RegularTextView

    override fun initPresenter() {
    }

    override fun attachViewToPresenter() {
    }

    override val layoutResId: Int
        get() = R.layout.activity_main //To change initializer of created properties use File | Settings | File Templates.


    lateinit var languageUtils: LanguageUtils


    @OnClick(R.id.tv_vn)
    fun OnVnClick() {
        changeLang("vi")

    }

    @OnClick(R.id.tv_en)
    fun OnEnClick() {
        changeLang("en")

    }

    fun loadLang() {
        val lang = languageUtils.loadLocale(this)
        if (lang.equals("vi")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvVn.textColor = getColor(R.color.black)
                tvEn.textColor = getColor(R.color.gray)

            } else {
                tvVn.textColor = resources.getColor(R.color.black)
                tvEn.textColor = resources.getColor(R.color.gray)

            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvVn.textColor = getColor(R.color.gray)
                tvEn.textColor = getColor(R.color.black)

            } else {
                tvVn.textColor = resources.getColor(R.color.gray)
                tvEn.textColor = resources.getColor(R.color.black)

            }
        }
    }

    fun changeLang(type: String) {

        if (type == "vi") {
            languageUtils.changeLang("vi", this)
        } else {
            languageUtils.changeLang("en", this)

        }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun initLocale() {
        languageUtils = LanguageUtils(this)
        val lang = languageUtils.loadLocale(this)
        languageUtils.changeLang(lang, this)
    }


    override fun setupViews() {

        loadLang()
        val ss = SpannableString(getString(R.string.main_msg_2))

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View?) {
//                makeNotification()

            }

        }

        val lang = languageUtils.loadLocale(this)

        if (lang == "vi") {
            ss.setSpan(clickableSpan, ss.length - 7, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        } else {
            ss.setSpan(clickableSpan, ss.length - 10, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        }

        tvMessage.text = ss
        tvMessage.movementMethod = LinkMovementMethod.getInstance()
        tvMessage.highlightColor = Color.TRANSPARENT

        imgSetting.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

    }

    @OnClick(R.id.logo)
    fun onClick() {
//        showNotification()
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
        tittlePush.text = preferenceHelper.getName()

        sheet.setOnClickListener {
            val intent = Intent(this, TransactionDetailActivity::class.java)
            startActivity(intent)
            sheet.visibility = View.GONE
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
}