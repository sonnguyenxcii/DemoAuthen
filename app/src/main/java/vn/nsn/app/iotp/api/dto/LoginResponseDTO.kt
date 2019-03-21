package vn.nsn.app.iotp.api.dto

import com.google.gson.annotations.SerializedName

class LoginResponseDTO(
        @SerializedName("user")
        val userDTO: UserDTO?,
        @SerializedName("meta")
        val metaDTO: MetaDTO?
)