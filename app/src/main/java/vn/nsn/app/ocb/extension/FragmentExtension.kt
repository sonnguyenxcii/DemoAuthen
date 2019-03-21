package vn.nsn.app.ocb.extension

import android.content.Context
import android.support.v4.app.Fragment
import vn.nsn.app.ocb.R

/**
 * Created by NaPro on 01/29/2018.
 */
val Fragment.ctx: Context?
    get() = context

val Fragment.TAG: String
    get() = this::class.java.simpleName

fun Fragment.nextFragment(containerId: Int, fragment: Fragment) {
    val transaction = childFragmentManager.beginTransaction()
    transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
    transaction.add(containerId, fragment, fragment.TAG).addToBackStack(fragment.TAG)
    transaction.commit()
}