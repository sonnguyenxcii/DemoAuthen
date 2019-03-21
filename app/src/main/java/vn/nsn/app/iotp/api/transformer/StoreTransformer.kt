package vn.nsn.app.iotp.api.transformer

import vn.nsn.app.iotp.api.dto.PurchaseHistoryDTO
import vn.nsn.app.iotp.api.dto.StoreProductDTO
import vn.nsn.app.iotp.api.entity.PurchaseHistory
import vn.nsn.app.iotp.api.entity.StoreProduct
import java.text.SimpleDateFormat
import java.util.*

private val dateFormatter by lazy {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ'", Locale.US)
    format.timeZone = TimeZone.getTimeZone("UTC")
    format
}

fun StoreProductDTO.toStoreProduct() = StoreProduct(
        name ?: "",
        productId ?: ""
)

fun PurchaseHistoryDTO.toPurchaseHistory() = PurchaseHistory(
        name ?: "",
        price ?: 0,
        dateFormatter.parse(purchasedAt)
)