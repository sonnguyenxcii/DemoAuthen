package vn.nsn.app.iotp.api.dto

import com.google.gson.annotations.SerializedName

class InputInvitationCodeDTO(
        @SerializedName("invite_code")
        val inviteCode: BodyDTO
)