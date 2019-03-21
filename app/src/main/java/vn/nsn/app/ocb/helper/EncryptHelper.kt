package vn.nsn.app.ocb.helper

import android.util.Base64
import vn.nsn.app.ocb.Constant
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class EncryptHelper {

    private val mac = Mac.getInstance("HmacSHA256")
    private val secretKeySpec = SecretKeySpec(Constant.HMAC_SECRET_KEY.toByteArray(), "HmacSHA256")

    init {
        mac.init(secretKeySpec)
    }

    fun getSignature(data: String): String {
        return String(Base64.encode(mac.doFinal(data.toByteArray()), Base64.DEFAULT)).trim()
    }
}