package vn.nsn.app.ocb.interactor

import android.content.Context
import io.reactivex.Single
import vn.nsn.app.ocb.Constant
import vn.nsn.app.ocb.api.dto.PurchaseHistoriesResponse
import vn.nsn.app.ocb.api.dto.StoreProductsResponse

class StoreInteractor(context: Context) : BaseInteractor(context) {

    fun getStoreProducts(): Single<StoreProductsResponse> {
        return apiEndPoint.getStoreProducts(token = Constant.AUTHORIZATION_PREFIX + preferenceHelper.getAccessToken())
    }

    fun getPurchaseHistories(page: Int): Single<PurchaseHistoriesResponse> {
        return apiEndPoint.getPurchaseHistories(token = Constant.AUTHORIZATION_PREFIX + preferenceHelper.getAccessToken(), page = page.toString())
    }
}