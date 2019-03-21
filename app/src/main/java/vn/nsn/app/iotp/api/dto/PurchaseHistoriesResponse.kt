package vn.nsn.app.iotp.api.dto

import com.google.gson.annotations.SerializedName

data class PurchaseHistoriesResponse(
        @SerializedName("purchase_histories")
        val purchaseHistoryDTOs: List<PurchaseHistoryDTO>?
)