package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

data class SubMenusResponse(
        @SerializedName("components")
        val components: List<SubMenuDTO>?
)