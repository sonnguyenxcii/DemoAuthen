package vn.nsn.app.iotp.api.dto

import com.google.gson.annotations.SerializedName

class UserCodeDTO(
        @SerializedName("code")
        val code: HashMap<String, String?>?
)