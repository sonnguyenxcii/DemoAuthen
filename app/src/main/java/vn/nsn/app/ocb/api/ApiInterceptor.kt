//package vn.nsn.app.ocb.api
//
//import android.content.Context
//import android.text.TextUtils
//import okhttp3.Interceptor
//import okhttp3.Response
//import vn.nsn.app.ocb.BuildConfig
//import vn.nsn.app.ocb.Constant
//import vn.nsn.app.ocb.PeepApp
//import vn.nsn.app.ocb.api.dto.RefreshTokenDTO
//import vn.nsn.app.ocb.api.transformer.toUser
//import vn.nsn.app.ocb.helper.EncryptHelper
//import vn.nsn.app.ocb.helper.PreferenceHelper
//
//class ApiInterceptor(context: Context) : Interceptor {
//
//    private val preferenceHelper = PreferenceHelper(context)
//    private val encryptHelper = EncryptHelper()
//
////    override fun intercept(chain: Interceptor.Chain): Response {
////        val orgRequest = chain.request()
////        val url = orgRequest.url()
////        val queries = url.encodedQuery()?.split("?")?.sortedWith(Comparator { o1, o2 ->
////            if ((o1.codePointAt(0) < o2.codePointAt(0))) {
////                -1
////            } else {
////                1
////            }
////        })
////        val arr = ArrayList<String>()
////        arr.add(orgRequest.method())
////        arr.add(preferenceHelper.getAccessToken() ?: "")
////        arr.add(url.host())
////        arr.add(url.encodedPath())
////        arr.add(TextUtils.join("?", queries ?: ArrayList<String>()))
////        var signature = encryptHelper.getSignature(TextUtils.join("\n", arr))
////        val adaptedRequest = orgRequest.newBuilder()
////                .addHeader(Constant.HEADER_SIGNATURE, signature)
////                .build()
////        val response = chain.proceed(adaptedRequest)
////        return if (response.code() == 401) {
////            /** if token expired, refresh it */
////            val refreshResponse = PeepApp.getApiEndPoint().refreshToken(BuildConfig.VERSION_NAME, RefreshTokenDTO(preferenceHelper.getRefreshToken())).execute()
////            val userDTO = refreshResponse.body()
////            userDTO?.let {
////                preferenceHelper.setUser(it.userDTO!!.toUser())
////            }
////            arr.removeAt(1)
////            arr.add(1, preferenceHelper.getAccessToken() ?: "")
////            signature = encryptHelper.getSignature(TextUtils.join("\n", arr))
////            val newRequest = orgRequest.newBuilder()
////                    .removeHeader(Constant.HEADER_AUTHORIZATION)
////                    .addHeader(Constant.HEADER_AUTHORIZATION, Constant.AUTHORIZATION_PREFIX + preferenceHelper.getAccessToken())
////                    .removeHeader(Constant.HEADER_SIGNATURE)
////                    .addHeader(Constant.HEADER_SIGNATURE, signature)
////                    .build()
////            chain.proceed(newRequest)
////        } else {
////            response
////        }
////    }
//}