package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

data class UserResponse(
        @SerializedName("user")
        val userDTO: UserDTO?
)