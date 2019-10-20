package com.example.mynoteusingroomdatabasewithkotlin

import android.content.Context
import android.graphics.Rect
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import android.text.method.Touch.onTouchEvent
import android.view.GestureDetector
import android.view.View


class RecyclerItemClickListener(
    context: Context,
    recyclerView: RecyclerView,
    private val mListener: OnItemClickListener?
) : RecyclerView.OnItemTouchListener {

    internal var mGestureDetector: GestureDetector

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int, motionEvent: MotionEvent)

        fun onLongItemClick(view: View?, position: Int)
    }

    init {
        mGestureDetector =
            GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    return true
                }

                override fun onLongPress(e: MotionEvent) {
                    val child = recyclerView.findChildViewUnder(e.x, e.y)
                    if (child != null && mListener != null) {
                        mListener.onLongItemClick(
                            child,
                            recyclerView.getChildAdapterPosition(child)
                        )
                    }
                }
            })
    }

    override fun onInterceptTouchEvent(view: RecyclerView, e: MotionEvent): Boolean {
        val childView = view.findChildViewUnder(e.x, e.y)
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            try {
                mListener.onItemClick(childView, view.getChildAdapterPosition(childView), e)
                return true
            } catch (exception: ArrayIndexOutOfBoundsException) {
                return false
            }

        }
        return false
    }

    override fun onTouchEvent(view: RecyclerView, motionEvent: MotionEvent) {}

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

    companion object {

        fun isViewClicked(view: View, e: MotionEvent): Boolean {
            val rect = Rect()
            view.getGlobalVisibleRect(rect)
            return rect.contains(e.rawX.toInt(), e.rawY.toInt())
        }
    }
}
