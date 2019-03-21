package vn.nsn.app.iotp.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import vn.nsn.app.iotp.R

/**
 * Created by NaPro on 01/29/2018.
 */
fun ImageView.setImage(imgSource: Any?, placeholder: Int = R.drawable.bg_gray_gradient, error: Int = R.mipmap.ic_launcher) {
    val requestOptions = RequestOptions()
            .centerCrop()
            .placeholder(placeholder)
            .error(error)
    Glide.with(this.ctx).load(imgSource).apply(requestOptions).into(this)
}

fun ImageView.setImageWithGrayHolder(url: String) {
    val requestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.bg_gray_gradient)
    Glide.with(this.context).load(url).apply(requestOptions).into(this)
}

fun ImageView.setImageBackground(imgSource: Any?) {
    val requestOptions = RequestOptions()
            .centerCrop()
    Glide.with(this.ctx).load(imgSource).apply(requestOptions).into(this)
}

fun ImageView.setImageBackgroundFade(imageUrl: String, oldImage: String) {
    Glide.with(this.ctx)

            .load(imageUrl)

            .into(this)
}

fun ImageView.setImageReading(imgSource: Any?, placeholder: Int = R.drawable.bg_gray_gradient, error: Int = R.mipmap.ic_launcher) {
    val requestOptions = RequestOptions()
            .fitCenter()
            .placeholder(placeholder)
            .transform(RoundedCorners(16))
            .error(error)

    Glide.with(this.ctx).load(imgSource).apply(requestOptions).into(this)
}
