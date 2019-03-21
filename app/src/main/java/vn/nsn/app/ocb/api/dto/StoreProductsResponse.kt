package vn.nsn.app.ocb.api.dto

import com.google.gson.annotations.SerializedName

data class StoreProductsResponse(
        @SerializedName("consumable")
        val consumable: List<StoreProductDTO>?,
        @SerializedName("auto_renewable")
        val autoRenewable: List<StoreProductDTO>?
)