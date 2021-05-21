package ua.kpi.comsys.ip8405.ui.gallery

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max
import kotlin.math.min

class GalleryLayoutManager : RecyclerView.LayoutManager() {
    private val cellSize: Int
        get() = width / 5

    override fun canScrollVertically(): Boolean = true

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams = RecyclerView
        .LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)
        if (state.itemCount <= 0) return
        fillWithChildren(recycler)
    }

    private fun fillWithChildren(recycler: RecyclerView.Recycler) {
        val rect = Rect(0, -cellSize * 3, cellSize * 5, 0)
        for (i in 0 until itemCount) {
            val view = recycler.getViewForPosition(i)
            addView(view)
            if (i % 7 == 0) rect.offset(0, cellSize * 3)
            locateView(rect, view, i)
        }
    }

    private fun locateView(r: Rect, v: View, i: Int) = when (i % 7) {
        0 -> {
            addSmallImage(v)
            layoutDecorated(v, r.left, r.top, r.left + cellSize, r.top + cellSize)
        }
        1 -> {
            addBigImage(v)
            layoutDecorated(v, r.left + cellSize, r.top, r.left + cellSize * 4, r.top + cellSize * 3)
        }
        2 -> {
            addSmallImage(v)
            layoutDecorated(v, r.left + cellSize * 4, r.top, r.right, r.top + cellSize)
        }
        3 -> {
            addSmallImage(v)
            layoutDecorated(v, r.left, r.top + cellSize, r.left + cellSize, r.top + cellSize * 2)
        }
        4 -> {
            addSmallImage(v)
            layoutDecorated(v, r.left + cellSize * 4, r.top + cellSize, r.right, r.top + cellSize * 2)
        }
        5 -> {
            addSmallImage(v)
            layoutDecorated(v, r.left, r.top + cellSize * 2, r.left + cellSize, r.bottom)
        }
        6 -> {
            addSmallImage(v)
            layoutDecorated(v, r.left + cellSize * 4, r.top + cellSize * 2, r.right, r.bottom)
        }
        else -> error("Layout manager: error filling view with children")
    }

    private fun addSmallImage(v: View) {
        measureChildWithMargins(v, 0, 0)
        addImageWithBorders(v, View.MeasureSpec.makeMeasureSpec(cellSize, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(cellSize, View.MeasureSpec.EXACTLY))
    }

    private fun addBigImage(v: View) {
        measureChildWithMargins(v, 0, 0)
        addImageWithBorders(v, View.MeasureSpec.makeMeasureSpec(cellSize * 3, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(cellSize * 3, View.MeasureSpec.EXACTLY))
    }

    private fun addImageWithBorders(v: View, width: Int, height: Int) {
        val borderRect = Rect()
        calculateItemDecorationsForChild(v, borderRect)
        val lp = v.layoutParams as RecyclerView.LayoutParams
        v.measure(updateMargins(width, lp.leftMargin + borderRect.left, lp.rightMargin + borderRect.right), updateMargins(height, lp.topMargin + borderRect.top, lp.bottomMargin + borderRect.bottom))
    }

    private fun updateMargins(size: Int, startInset: Int, endInset: Int) : Int{
        if (startInset == 0 && endInset == 0) return size
        val mode = View.MeasureSpec.getMode(size)
        return if (mode == View.MeasureSpec.AT_MOST || mode == View.MeasureSpec.EXACTLY) {
            View.MeasureSpec.makeMeasureSpec(
                View.MeasureSpec.getSize(size) - startInset - endInset,
                mode
            )
        } else {
            size
        }
    }

    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        if (childCount == 0) return 0

        val topView = getChildAt(0)!!
        val bottomView = getChildAt(childCount - 1)!!

        val viewSpan = getDecoratedBottom(bottomView) - getDecoratedTop(topView)
        val delta = if (dy < 0) {
            val firstViewAdapterPos = getPosition(topView)
            if (firstViewAdapterPos > 0) dy else max(getDecoratedTop(topView), dy)
        } else {
            val lastViewAdapterPos = getPosition(bottomView)
            if (lastViewAdapterPos < itemCount - 1) {
                dy
            } else {
                val pos = getPosition(bottomView) % 7
                val h = getDecoratedBottom(bottomView) - height + when (pos) {
                    2 -> cellSize * 2
                    3, 4 -> cellSize
                    else -> 0
                }
                min(h, dy)
            }
        }
        offsetChildrenVertical(-delta)
        return delta
    }
}