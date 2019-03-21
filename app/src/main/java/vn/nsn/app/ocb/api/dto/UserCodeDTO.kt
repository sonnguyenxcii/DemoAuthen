package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

class UserCodeDTO(
        @SerializedName("code")
        val code: HashMap<String, String?>?
)