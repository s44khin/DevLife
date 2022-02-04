package ru.s44khin.cardstackview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import ru.s44khin.cardstackview.internal.CardStackDataObserver
import ru.s44khin.cardstackview.internal.CardStackSnapHelper

class CardStackView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {

    private val observer = CardStackDataObserver(this)

    override fun setLayoutManager(manager: LayoutManager?) = when (manager) {
        is CardStackLayoutManager -> super.setLayoutManager(manager)
        else -> error("CardStackView must be set CardStackLayoutManager.")
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        if (layoutManager == null)
            layoutManager = CardStackLayoutManager()


        this@CardStackView.adapter?.let { oldAdapter ->
            oldAdapter.unregisterAdapterDataObserver(observer)
            oldAdapter.onDetachedFromRecyclerView(this)
        }

        adapter?.registerAdapterDataObserver(observer)
        super.setAdapter(adapter)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN && layoutManager != null)
            (layoutManager as CardStackLayoutManager).updateProportion(event.x, event.y)

        return super.onInterceptTouchEvent(event)
    }

    fun swipe() {
        if (layoutManager is CardStackLayoutManager)
            smoothScrollToPosition((layoutManager as CardStackLayoutManager).topPosition + 1)
    }

    fun rewind() {
        if (layoutManager is CardStackLayoutManager)
            smoothScrollToPosition((layoutManager as CardStackLayoutManager).topPosition - 1)
    }

    fun initialize() {
        CardStackSnapHelper().attachToRecyclerView(this)
        overScrollMode = OVER_SCROLL_NEVER
    }
}