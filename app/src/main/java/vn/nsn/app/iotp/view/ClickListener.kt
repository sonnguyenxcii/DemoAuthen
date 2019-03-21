package vn.nsn.app.iotp.view

import android.view.MotionEvent
import android.view.View

interface ClickListener {
    fun onClick(view: View)

    fun onOutsideClick(event: MotionEvent)
}