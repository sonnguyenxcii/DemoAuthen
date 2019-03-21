package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

class MetaDTO(
        @SerializedName("newly_created")
        val newlyCreated: Boolean?
)