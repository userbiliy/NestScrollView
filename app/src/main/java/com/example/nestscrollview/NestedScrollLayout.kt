package com.example.nestscrollview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.NestedScrollingParent2
import androidx.core.view.NestedScrollingParentHelper
import androidx.core.view.ViewCompat
import com.google.android.material.tabs.TabLayout
import kotlin.properties.Delegates


class NestedScrollLayout(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs),
    NestedScrollingParent2 {

    private val mNestedScrollParentHelper = NestedScrollingParentHelper(this)
    private val tabStartAxis = intArrayOf(0, 0)
    private var tabScopeAxis = intArrayOf(0, 0)
    private lateinit var tabLayout: TabLayout
    private lateinit var imageView: ImageView

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        // 获取子控件
        tabLayout = getChildAt(1) as TabLayout
        imageView = getChildAt(0) as ImageView
        // 计算子控件在父布局中的起始坐标
        tabLayout.getLocationInWindow(tabStartAxis)
        imageView.getLocationInWindow(tabScopeAxis)

        tabScopeAxis[0] = tabScopeAxis[1] + imageView.height // imageView的BottomY值

    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        return (axes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        mNestedScrollParentHelper.onNestedScrollAccepted(child, target, axes)
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        if (dy > 0 && tabStartAxis[1] > tabScopeAxis[1] - 5) {
            scrollBy(0, dy)
            consumed[1] = dy
        }
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        if (dyUnconsumed < 0 && tabStartAxis[1] < tabScopeAxis[0]) {
            scrollBy(0, dyUnconsumed)
        }
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        mNestedScrollParentHelper.onStopNestedScroll(target, type)
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        tabLayout.getLocationInWindow(tabStartAxis)
    }
}