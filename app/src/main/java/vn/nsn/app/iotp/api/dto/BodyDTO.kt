package vn.nsn.app.iotp.api.dto

import com.google.gson.annotations.SerializedName

class BodyDTO(
        @SerializedName("body")
        val body: String?
)