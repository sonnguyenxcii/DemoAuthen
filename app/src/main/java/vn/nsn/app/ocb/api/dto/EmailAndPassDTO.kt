package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

data class EmailAndPassDTO(
        @SerializedName("email")
        val email: String?,
        @SerializedName("password")
        val password: String?
)