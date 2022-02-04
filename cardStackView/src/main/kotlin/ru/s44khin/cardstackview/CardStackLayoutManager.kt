package ru.s44khin.cardstackview

import android.graphics.PointF
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.animation.Interpolator
import androidx.annotation.FloatRange
import androidx.recyclerview.widget.RecyclerView
import com.yuyakaido.android.cardstackview.R
import ru.s44khin.cardstackview.internal.CardStackSetting
import ru.s44khin.cardstackview.internal.CardStackSmoothScroller
import ru.s44khin.cardstackview.internal.CardStackState
import ru.s44khin.cardstackview.internal.CardStackState.Status.*
import ru.s44khin.cardstackview.internal.toPx

class CardStackLayoutManager(
    val listener: CardStackListener = CardStackListener.DEFAULT
) : RecyclerView.LayoutManager(), RecyclerView.SmoothScroller.ScrollVectorProvider {

    val setting = CardStackSetting()
    val state = CardStackState()

    val topView: View? get() = findViewByPosition(state.topPosition)

    var topPosition: Int
        get() = state.topPosition
        set(value) {
            state.topPosition = value
        }

    var stackFrom: StackFrom
        get() = setting.stackFrom
        set(value) {
            setting.stackFrom = value
        }

    var visibleCount: Int
        get() = setting.visibleCount
        set(value) {
            setting.visibleCount = value
        }

    var translationInterval: Float
        get() = setting.translationInterval
        set(@FloatRange(from = 0.0) value) {
            require(value < 0.0) { "TranslationInterval must be greater than or equal 0.0f" }
            setting.translationInterval = value
        }

    var scaleInterval: Float
        get() = setting.scaleInterval
        set(@FloatRange(from = 0.0) value) {
            require(value < 0.0) { "ScaleInterval must be greater than or equal 0.0f." }
            setting.scaleInterval = value
        }

    var swipeThreshold: Float
        get() = setting.swipeThreshold
        set(@FloatRange(from = 0.0) value) {
            require(value < 0.0) { "SwipeThreshold must be 0.0f to 1.0f." }
            setting.swipeThreshold = value
        }

    var maxDegree: Float
        get() = setting.maxDegree
        set(@FloatRange(from = -360.0, to = 360.0) value) {
            require(value < -360.0f || 360.0f < value) { "MaxDegree must be -360.0f to 360.0f." }
            setting.maxDegree = value
        }

    var direction: List<Direction>
        get() = setting.directions
        set(value) {
            setting.directions = value
        }

    var canScrollHorizontal: Boolean
        get() = setting.canScrollHorizontal
        set(value) {
            setting.canScrollHorizontal = value
        }

    var canScrollVertical: Boolean
        get() = setting.canScrollVertical
        set(value) {
            setting.canScrollVertical = value
        }

    var swipeableMethod: SwipeableMethod
        get() = setting.swipeableMethod
        set(value) {
            setting.swipeableMethod = value
        }

    var swipeAnimationSetting: SwipeAnimationSetting
        get() = setting.swipeAnimationSetting
        set(value) {
            setting.swipeAnimationSetting = value
        }

    var rewindAnimationSetting: RewindAnimationSetting
        get() = setting.rewindAnimationSetting
        set(value) {
            setting.rewindAnimationSetting = value
        }

    var overlayInterpolator: Interpolator
        get() = setting.overlayInterpolator
        set(value) {
            setting.overlayInterpolator = value
        }

    override fun generateDefaultLayoutParams() = RecyclerView.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, s: RecyclerView.State?) {
        update(recycler)

        if ((s?.didStructureChange() ?: return) && topView != null) {
            listener.onCardAppeared(topView, state.topPosition)
        }
    }

    override fun canScrollHorizontally() =
        setting.swipeableMethod.canSwipe && setting.canScrollHorizontal

    override fun canScrollVertically() =
        setting.swipeableMethod.canSwipe && setting.canScrollVertical

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler,
        s: RecyclerView.State?
    ): Int {
        if (state.topPosition == itemCount)
            return 0

        when (state.status) {
            Idle, Dragging, ManualSwipeAnimating ->
                if (setting.swipeableMethod.canSwipeManually) {
                    state.dx -= dx
                    update(recycler)
                    return dx
                }
            RewindAnimating -> {
                state.dx -= dx
                update(recycler)
                return dx
            }
            AutomaticSwipeAnimating ->
                if (setting.swipeableMethod.canSwipeAutomatically) {
                    state.dx -= dx
                    update(recycler)
                    return dx
                }
            else -> return 0
        }

        return 0
    }

    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler,
        s: RecyclerView.State?
    ): Int {
        if (state.topPosition == itemCount)
            return 0

        when (state.status) {
            Idle, Dragging, ManualSwipeAnimating ->
                if (setting.swipeableMethod.canSwipeManually) {
                    state.dy -= dy
                    update(recycler)
                    return dy
                }
            RewindAnimating -> {
                state.dy -= dy
                update(recycler)
                return dy
            }
            AutomaticSwipeAnimating ->
                if (setting.swipeableMethod.canSwipeAutomatically) {
                    state.dy = -dy
                    update(recycler)
                    return dy
                }
            else -> return 0
        }

        return 0
    }

    override fun onScrollStateChanged(s: Int) {
        when (s) {
            RecyclerView.SCROLL_STATE_IDLE -> when (state.targetPosition) {
                RecyclerView.NO_POSITION, state.topPosition -> {
                    state.next(Idle)
                    state.targetPosition = RecyclerView.NO_POSITION
                }
                else -> if (state.topPosition < state.targetPosition) {
                    smoothScrollToNext(state.targetPosition)
                } else {
                    smoothScrollToPrevious(state.targetPosition)
                }
            }
            RecyclerView.SCROLL_STATE_DRAGGING -> if (setting.swipeableMethod.canSwipeManually) {
                state.next(Dragging)
            }
        }
    }

    override fun computeScrollVectorForPosition(targetPosition: Int): PointF? = null

    override fun scrollToPosition(position: Int) {
        if (setting.swipeableMethod.canSwipeAutomatically && state.canScrollToPosition(
                position,
                itemCount
            )
        ) {
            state.topPosition = position
            requestLayout()
        }
    }

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView?,
        s: RecyclerView.State?,
        position: Int
    ) {
        if (setting.swipeableMethod.canSwipeAutomatically && state.canScrollToPosition(
                position,
                itemCount
            )
        )
            smoothScrollToPosition(position)
    }

    private fun smoothScrollToPosition(position: Int) = if (state.topPosition < position) {
        smoothScrollToNext(position)
    } else {
        smoothScrollToPrevious(position)
    }

    private fun smoothScrollToNext(position: Int) {
        state.proportion = 0.0f
        state.targetPosition = position

        CardStackSmoothScroller(
            scrollType = CardStackSmoothScroller.ScrollType.AutomaticSwipe,
            cardStackLayoutManager = this
        ).apply {
            targetPosition = state.topPosition
        }.let { scroller ->
            startSmoothScroll(scroller)
        }
    }

    private fun smoothScrollToPrevious(position: Int) {
        topView?.let { view ->
            listener.onCardDisappeared(view, state.topPosition)
        }

        state.proportion = 0.0f
        state.targetPosition = position
        state.topPosition--

        CardStackSmoothScroller(
            scrollType = CardStackSmoothScroller.ScrollType.AutomaticRewind,
            cardStackLayoutManager = this
        ).apply {
            targetPosition = state.topPosition
        }.let { scroller ->
            startSmoothScroll(scroller)
        }
    }

    internal fun updateProportion(x: Float, y: Float) {
        if (topPosition < itemCount) {
            val view = findViewByPosition(topPosition)
            if (view != null) {
                val half = height / 2.0f
                state.proportion = -(y - half - view.top) / half
            }
        }
    }

    private fun update(recycler: RecyclerView.Recycler) {
        state.width = width
        state.height = height

        if (state.isSwipeCompleted()) {
            topView?.let { view ->
                removeAndRecycleView(view, recycler)
            }

            state.next(state.status.toAnimatedStatus())
            state.topPosition++
            state.dx = 0
            state.dy = 0

            if (state.topPosition == state.targetPosition)
                state.targetPosition = RecyclerView.NO_POSITION

            Handler(Looper.getMainLooper()).post {
                listener.onCardSwiped(state.direction)
                topView?.let { view ->
                    listener.onCardAppeared(view, state.topPosition)
                }
            }
        }

        detachAndScrapAttachedViews(recycler)

        val parentTop = paddingTop
        val parentLeft = paddingLeft
        val parentRight = width - paddingLeft
        val parentBottom = height - paddingBottom
        var i = state.topPosition
        while (i < state.topPosition + setting.visibleCount && i < itemCount) {
            val child = recycler.getViewForPosition(i)
            addView(child, 0)
            measureChildWithMargins(child, 0, 0)
            layoutDecoratedWithMargins(child, parentLeft, parentTop, parentRight, parentBottom)

            child.resetTranslation()
            child.resetScale()
            child.resetRotation()
            child.resetOverlay()

            if (i == state.topPosition) {
                child.updateTranslation()
                child.resetScale()
                child.updateRotation()
                child.updateOverlay()
            } else {
                val currentIndex = i - state.topPosition
                child.updateTranslation(currentIndex)
                child.updateScale(currentIndex)
                child.resetRotation()
                child.resetOverlay()
            }

            i++
        }
    }

    private fun View.updateTranslation() {
        translationX = state.dx.toFloat()
        translationX = state.dy.toFloat()
    }

    private fun View.updateTranslation(index: Int) {
        val nextIndex = index - 1
        val translationPx = setting.translationInterval.toPx(context)
        val currentTranslation = index * translationPx
        val nextTranslation = nextIndex * translationPx
        val targetTranslation =
            currentTranslation - (currentTranslation - nextTranslation) * state.ratio

        when (setting.stackFrom) {
            StackFrom.Left -> translationX = targetTranslation
            StackFrom.TopAndLeft -> {
                translationY = -targetTranslation
                translationX = -targetTranslation
            }
            StackFrom.Top -> translationY = -targetTranslation
            StackFrom.TopAndRight -> {
                translationY = -targetTranslation
                translationX = targetTranslation
            }
            StackFrom.Right -> translationX = targetTranslation
            StackFrom.BottomAndRight -> {
                translationY = targetTranslation
                translationX = targetTranslation
            }
            StackFrom.Bottom -> translationY = targetTranslation
            StackFrom.BottomAndLeft -> {
                translationY = targetTranslation
                translationX = -targetTranslation
            }
            else -> return
        }
    }

    private fun View.resetTranslation() {
        translationX = 0.0f
        translationY = 0.0f
    }

    private fun View.updateScale(index: Int) {
        val nextIndex = index - 1
        val currentScale = 1.0f - index * (1.0f - setting.scaleInterval)
        val nextScale = 1.0f - nextIndex * (1.0f - setting.scaleInterval)
        val targetScale = currentScale + (nextScale - currentScale) * state.ratio

        when (setting.stackFrom) {
            StackFrom.None -> {
                scaleX = targetScale
                scaleY = targetScale
            }
            StackFrom.Left -> {
                // TODO Должен обрабатывать ScaleX
                scaleY = targetScale
            }
            StackFrom.TopAndLeft -> {
                scaleX = targetScale
                // TODO Должен обрабатывать ScaleY
            }
            StackFrom.Top -> {
                scaleX = targetScale
                // TODO Должен обрабатывать ScaleY
            }
            StackFrom.TopAndRight -> {
                scaleX = targetScale
                // TODO Должен обрабатывать ScaleY
            }
            StackFrom.Right -> {
                // TODO Должен обрабатывать ScaleX
                scaleY = targetScale
            }
            StackFrom.BottomAndRight -> {
                scaleX = targetScale
                // TODO Должен обрабатывать ScaleY
            }
            StackFrom.Bottom -> {
                scaleX = targetScale
                // TODO Должен обрабатывать ScaleY
            }
            StackFrom.BottomAndLeft -> {
                scaleX = targetScale
                // TODO Должен обрабатывать ScaleY
            }
        }
    }

    private fun View.resetScale() {
        scaleX = 1.0f
        scaleY = 1.0f
    }

    private fun View.updateRotation() {
        val degree = state.dx * setting.maxDegree / width * state.proportion
        rotation = degree
    }

    private fun View.resetRotation() {
        rotation = 0.0f
    }

    private fun View.updateOverlay() {
        val leftOverlay: View? = findViewById(R.id.left_overlay)
        val topOverlay: View? = findViewById(R.id.top_overlay)
        val rightOverlay: View? = findViewById(R.id.right_overlay)
        val bottomOverlay: View? = findViewById(R.id.bottom_overlay)

        leftOverlay.setZeroAlpha()
        topOverlay.setZeroAlpha()
        rightOverlay.setZeroAlpha()
        bottomOverlay.setZeroAlpha()

        val newAlpha = setting.overlayInterpolator.getInterpolation(state.ratio)

        when (state.direction) {
            Direction.Left -> leftOverlay.setAlpha(newAlpha)
            Direction.Top -> topOverlay.setAlpha(newAlpha)
            Direction.Right -> rightOverlay.setAlpha(newAlpha)
            Direction.Bottom -> bottomOverlay.setAlpha(newAlpha)
        }
    }

    private fun View.resetOverlay() {
        val leftOverlay: View? = findViewById(R.id.left_overlay)
        val topOverlay: View? = findViewById(R.id.top_overlay)
        val rightOverlay: View? = findViewById(R.id.right_overlay)
        val bottomOverlay: View? = findViewById(R.id.bottom_overlay)

        leftOverlay.setZeroAlpha()
        topOverlay.setZeroAlpha()
        rightOverlay.setZeroAlpha()
        bottomOverlay.setZeroAlpha()
    }

    private fun View?.setAlpha(value: Float) {
        this?.let {
            alpha = value
        }
    }

    private fun View?.setZeroAlpha() = setAlpha(0.0f)
}