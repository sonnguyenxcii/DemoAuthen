package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

data class UpdateDeviceTokenDTO(
        @SerializedName("token")
        val deviceToken: DeviceTokenDTO?
)