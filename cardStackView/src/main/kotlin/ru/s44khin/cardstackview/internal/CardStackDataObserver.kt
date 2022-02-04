package ru.s44khin.cardstackview.internal

import androidx.recyclerview.widget.RecyclerView
import ru.s44khin.cardstackview.CardStackLayoutManager
import kotlin.math.min

class CardStackDataObserver(
    private val recyclerView: RecyclerView
) : RecyclerView.AdapterDataObserver() {

    private val cardStackLayoutManager: CardStackLayoutManager
        get() {
            val manager = recyclerView.layoutManager

            if (manager is CardStackLayoutManager)
                return manager
            else
                error("CardStackView must be set CardStackLayoutManager.")
        }

    override fun onChanged() {
        cardStackLayoutManager.topPosition = 0
    }

    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
        if (cardStackLayoutManager.itemCount == 0) {
            cardStackLayoutManager.topPosition = 0
        } else if (positionStart < cardStackLayoutManager.topPosition) {
            val diff = cardStackLayoutManager.topPosition - positionStart
            cardStackLayoutManager.topPosition = min(
                cardStackLayoutManager.topPosition - diff,
                cardStackLayoutManager.itemCount - 1
            )
        }
    }

    override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) =
        cardStackLayoutManager.removeAllViews()
}