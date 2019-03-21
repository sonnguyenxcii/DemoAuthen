package vn.nsn.app.ocb

import android.app.Application
import android.util.Log
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import vn.nsn.app.ocb.api.ApiEndPoint

class PeepApp : Application() {
    var mLastPause: Long = 0

    companion object {
        private lateinit var apiEndPoint: ApiEndPoint
        fun getApiEndPoint(): ApiEndPoint {
            return apiEndPoint
        }
    }

    override fun onCreate() {
        super.onCreate()
        mLastPause = 0;
        Log.w("Application", "Launch");

//        Fabric.with(this, Crashlytics())
//        val logging = HttpLoggingInterceptor()
//        logging.level = HttpLoggingInterceptor.Level.BODY
//        val client = OkHttpClient.Builder()
//                .addInterceptor(ApiInterceptor(this))
//                .addInterceptor(logging)
//                .build()
//        apiEndPoint = Retrofit.Builder()
//                .baseUrl(BuildConfig.SERVER_URL)
//                .client(client)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(ApiEndPoint::class.java)
    }


}