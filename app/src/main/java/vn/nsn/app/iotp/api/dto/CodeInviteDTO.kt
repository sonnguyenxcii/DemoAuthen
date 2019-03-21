package vn.nsn.app.iotp.api.dto

import com.google.gson.annotations.SerializedName

class CodeInviteDTO(
        @SerializedName("code")
        val code: String?
)