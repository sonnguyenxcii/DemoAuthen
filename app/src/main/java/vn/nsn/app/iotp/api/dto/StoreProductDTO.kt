package vn.nsn.app.iotp.api.dto

import com.google.gson.annotations.SerializedName

data class StoreProductDTO(
        @SerializedName("name")
        val name: String?,
        @SerializedName("product_id")
        val productId: String?
)