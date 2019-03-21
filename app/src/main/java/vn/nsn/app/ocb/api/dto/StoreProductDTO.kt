package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

data class StoreProductDTO(
        @SerializedName("name")
        val name: String?,
        @SerializedName("product_id")
        val productId: String?
)