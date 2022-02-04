package ru.s44khin.cardstackview.internal

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.s44khin.cardstackview.CardStackLayoutManager
import ru.s44khin.cardstackview.Direction

class CardStackSmoothScroller(
    private val scrollType: ScrollType,
    private val cardStackLayoutManager: CardStackLayoutManager
) : RecyclerView.SmoothScroller() {

    override fun onSeekTargetStep(dx: Int, dy: Int, state: RecyclerView.State, action: Action) {
        if (scrollType == ScrollType.AutomaticRewind) {
            val setting = cardStackLayoutManager.setting.rewindAnimationSetting
            action.update(
                -getDx(setting),
                -getDy(setting),
                setting.duration,
                setting.interpolator
            )
        }
    }

    override fun onTargetFound(targetView: View, state: RecyclerView.State, action: Action) =
        when (scrollType) {
            ScrollType.AutomaticSwipe -> action.update(
                -getDx(cardStackLayoutManager.setting.swipeAnimationSetting),
                -getDy(cardStackLayoutManager.setting.swipeAnimationSetting),
                cardStackLayoutManager.setting.swipeAnimationSetting.duration,
                cardStackLayoutManager.setting.swipeAnimationSetting.interpolator
            )
            ScrollType.ManualSwipe -> action.update(
                -targetView.translationX.toInt() * 10,
                -targetView.translationY.toInt() * 10,
                cardStackLayoutManager.setting.swipeAnimationSetting.duration,
                cardStackLayoutManager.setting.swipeAnimationSetting.interpolator
            )
            ScrollType.AutomaticRewind, ScrollType.ManualCancel -> action.update(
                targetView.translationX.toInt(),
                targetView.translationY.toInt(),
                cardStackLayoutManager.setting.rewindAnimationSetting.duration,
                cardStackLayoutManager.setting.rewindAnimationSetting.interpolator
            )
        }

    override fun onStart() = when (scrollType) {
        ScrollType.AutomaticSwipe -> {
            cardStackLayoutManager.state.next(CardStackState.Status.AutomaticSwipeAnimating)
            cardStackLayoutManager.listener.onCardDisappeared(
                view = cardStackLayoutManager.topView,
                position = cardStackLayoutManager.topPosition
            )
        }
        ScrollType.ManualSwipe -> {
            cardStackLayoutManager.state.next(CardStackState.Status.ManualSwipeAnimating)
            cardStackLayoutManager.listener.onCardDisappeared(
                view = cardStackLayoutManager.topView,
                position = cardStackLayoutManager.topPosition
            )
        }
        ScrollType.AutomaticRewind, ScrollType.ManualCancel -> {
            cardStackLayoutManager.state.next(CardStackState.Status.RewindAnimating)
        }
    }

    override fun onStop() = when (scrollType) {
        ScrollType.AutomaticRewind -> {
            cardStackLayoutManager.listener.onCardRewound()
            cardStackLayoutManager.listener.onCardAppeared(
                view = cardStackLayoutManager.topView,
                position = cardStackLayoutManager.topPosition
            )
        }
        ScrollType.ManualCancel -> cardStackLayoutManager.listener.onCardCanceled()
        else -> Unit
    }

    private fun getDx(setting: AnimationSetting) = when (setting.direction) {
        Direction.Left -> -cardStackLayoutManager.state.width * 2
        Direction.Right -> cardStackLayoutManager.state.width * 2
        Direction.Top, Direction.Bottom -> 0
    }

    private fun getDy(setting: AnimationSetting) = when (setting.direction) {
        Direction.Left, Direction.Right -> cardStackLayoutManager.state.height / 4
        Direction.Top -> -cardStackLayoutManager.state.height * 2
        Direction.Bottom -> cardStackLayoutManager.state.height * 2
    }

    enum class ScrollType {

        AutomaticSwipe,
        AutomaticRewind,
        ManualSwipe,
        ManualCancel
    }
}