package vn.nsn.app.iotp.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent

class RecyclerTouchEvent(context: Context, internal var clickListener: ClickListener?) : RecyclerView.OnItemTouchListener {

    init {
        GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {

            override fun onLongPress(e: MotionEvent) {
                super.onLongPress(e)
            }

        })

    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val childView = rv.findChildViewUnder(e.x, e.y)


        if (childView != null && clickListener != null) {
            clickListener!!.onClick(childView)
        } else if (childView == null && clickListener != null) {
            clickListener!!.onOutsideClick(e)
        }

        return false
    }

    override fun onTouchEvent(rv: RecyclerView, motionEvent: MotionEvent) {

    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }
}

