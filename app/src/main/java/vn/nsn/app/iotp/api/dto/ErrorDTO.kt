package vn.nsn.app.iotp.api.dto

import com.google.gson.annotations.SerializedName

open class ErrorDTO(
        @SerializedName("type")
        var type: String?,
        @SerializedName("error_message")
        var errorMessage: String?
)