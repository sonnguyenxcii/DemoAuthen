package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

data class DeviceTokenDTO(
        @SerializedName("body")
        val value: String?
)