package vn.nsn.app.iotp.api.dto

import com.google.gson.annotations.SerializedName

data class PurchaseHistoryDTO(
        @SerializedName("name")
        val name: String?,
        @SerializedName("price")
        val price: Int?,
        @SerializedName("purchased_at")
        val purchasedAt: String?
)