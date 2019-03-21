package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

data class ProfileDTO(
        @SerializedName("name")
        val name: String?,
        @SerializedName("gender")
        val gender: String?,
        @SerializedName("age")
        val age: Int?,
        @SerializedName("icon_url")
        val iconUrl: String?
)