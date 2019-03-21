package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

data class SettingPushDTO(

        @SerializedName("settings")
        val settings: PushSettingDTO?

)