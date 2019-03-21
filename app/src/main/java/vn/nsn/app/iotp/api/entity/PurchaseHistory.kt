package vn.nsn.app.iotp.api.entity

import java.util.*

data class PurchaseHistory(
        val name: String,
        val price: Int,
        val purchaseAt: Date
)