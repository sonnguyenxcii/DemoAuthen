package vn.nsn.app.iotp.api.dto

import com.google.gson.annotations.SerializedName

class MetaDTO(
        @SerializedName("newly_created")
        val newlyCreated: Boolean?
)