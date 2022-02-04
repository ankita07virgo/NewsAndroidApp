package com.test.newsapp.utils

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.view.MotionEventCompat
import androidx.viewpager.widget.ViewPager


class VerticalViewPager : ViewPager {
    var xVal = 0f
    var mStartDragX = 0f

    constructor(context: Context?) : super(context!!) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
        init()
    }

    private fun init() {
        // The majority of the magic happens here
        setPageTransformer(true, VerticalPageTransformer())
        // The easiest way to get rid of the overscroll drawing that happens on the left and right
        overScrollMode = OVER_SCROLL_NEVER
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (adapter != null) {
            if (currentItem >= 0 || currentItem < adapter!!.count) {
                swapXY(event)
                val action = event.action
                when (action and MotionEventCompat.ACTION_MASK) {
                    MotionEvent.ACTION_MOVE -> {
                    }
                    MotionEvent.ACTION_UP -> {
                        mStartDragX = event.x
                        if (x < mStartDragX && mStartDragX - xVal > SWIPE_X_MIN_THRESHOLD
                            && currentItem > 0
                        ) {
                            Log.i(
                                "VerticalViewPager",
                                "down " + xVal + " : " + mStartDragX + " : " + (mStartDragX - xVal)
                            )
                            setCurrentItem(currentItem - 1, true)
                            return true
                        } else if (xVal > mStartDragX && xVal - mStartDragX > SWIPE_X_MIN_THRESHOLD
                            && currentItem < adapter!!.count - 1
                        ) {
                            Log.i(
                                "VerticalViewPager",
                                "up " + xVal + " : " + mStartDragX + " : " + (xVal - mStartDragX)
                            )
                            mStartDragX = 0f
                            setCurrentItem(currentItem + 1, true)
                            return true
                        }
                    }
                }
            } else {
                mStartDragX = 0f
            }
            swapXY(event)
            return super.onTouchEvent(swapXY(event))
        }
        return false
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        val intercepted = super.onInterceptTouchEvent(swapXY(event))
        when (event.action and MotionEventCompat.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> xVal = event.x
        }
        swapXY(event) // return touch coordinates to original reference frame for any child views
        return intercepted
    }

    /**
     * Swaps the X and Y coordinates of your touch event.
     */
    private fun swapXY(ev: MotionEvent): MotionEvent {
        val width = width.toFloat()
        val height = height.toFloat()
        val newX = ev.y / height * width
        val newY = ev.x / width * height
        ev.setLocation(newX, newY)
        return ev
    }

    private inner class VerticalPageTransformer : PageTransformer {
        override fun transformPage(view: View, position: Float) {
            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.alpha = 0f
            } else if (position <= 1) { // [-1,1]
                view.alpha = 1f

                // Counteract the default slide transition
                view.translationX = view.width * -position

                //set Y position to swipe in from top
                val yPosition = position * view.height
                view.translationY = yPosition
            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.alpha = 0f
            }
        }
    }

    companion object {
        private const val SWIPE_X_MIN_THRESHOLD =
            50f // Decide this magical nuber as per your requirement
    }
}