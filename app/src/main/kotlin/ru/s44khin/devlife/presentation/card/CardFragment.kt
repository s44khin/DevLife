package ru.s44khin.devlife.presentation.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.s44khin.cardstackview.*
import ru.s44khin.devlife.R
import ru.s44khin.devlife.data.model.Post
import ru.s44khin.devlife.databinding.FragmentCardBinding
import ru.s44khin.devlife.presentation.card.adapter.ItemHandler
import ru.s44khin.devlife.presentation.card.adapter.PaginationAdapterHelper
import ru.s44khin.devlife.presentation.card.adapter.PostAdapter
import ru.s44khin.devlife.presentation.card.elm.CardReducer
import ru.s44khin.devlife.presentation.card.elm.Effect
import ru.s44khin.devlife.presentation.card.elm.Event
import ru.s44khin.devlife.presentation.card.elm.State
import ru.s44khin.devlife.presentation.favorites.FavoritesFragment
import ru.s44khin.devlife.presentation.post.PostFragment
import ru.s44khin.devlife.utils.elmDialogFragment.mainComponent
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.core.ElmStoreCompat

class FragmentLatest : CardFragment() {

    companion object {
        const val TAG = "LATEST"
        fun newInstance() = FragmentLatest()
    }

    override fun createStore() = ElmStoreCompat(
        initialState = State(),
        reducer = CardReducer(),
        actor = mainComponent.latestActor
    )
}

class FragmentTop : CardFragment() {

    companion object {
        const val TAG = "TOP"
        fun newInstance() = FragmentTop()
    }

    override fun createStore() = ElmStoreCompat(
        initialState = State(),
        reducer = CardReducer(),
        actor = mainComponent.topActor
    )
}

abstract class CardFragment : ElmFragment<Event, Effect, State>(R.layout.fragment_card),
    CardStackListener, ItemHandler {

    private val binding by viewBinding(FragmentCardBinding::bind)
    private lateinit var manager: CardStackLayoutManager
    private val adapter by lazy {
        PostAdapter(
            itemHandler = this,
            paginationAdapterHelper = PaginationAdapterHelper {
                store.accept(Event.Ui.LoadPosts)
            }
        )
    }
    private var lastPosition = 0
    override val initEvent = Event.Ui.LoadPosts

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initCardStackView()
        initButtons()
    }

    override fun render(state: State) {
        if (state.posts.isNotEmpty()) {
            adapter.items = state.posts
        }
    }

    override fun onCardDragging(direction: Direction, ratio: Float) {
        binding.titleBar.isLifted = true
    }

    override fun onCardSwiped(direction: Direction) {
        if (direction == Direction.Top)
            store.accept(Event.Ui.SaveToDatabase(adapter.items[manager.topPosition - 1]))
    }

    private fun initCardStackView() = binding.recyclerView.apply {
        manager = CardStackLayoutManager(this@CardFragment).apply {
            stackFrom = StackFrom.Bottom
            canScrollHorizontal = true
            canScrollVertical = true
            visibleCount = 3
            direction = listOf(Direction.Top, Direction.Left, Direction.Right)
        }
        layoutManager = manager
        adapter = this@CardFragment.adapter
    }

    private fun initButtons() = binding.apply {
        fabSwap.setOnClickListener {
            val setting = SwipeAnimationSetting().apply {
                direction = Direction.Right
                duration = Duration.Normal.duration
                interpolator = AccelerateInterpolator()
            }

            manager.swipeAnimationSetting = setting
            binding.recyclerView.swipe()
        }
        fabLike.setOnClickListener {
            val setting = SwipeAnimationSetting().apply {
                direction = Direction.Top
                duration = Duration.Normal.duration
                interpolator = AccelerateInterpolator()
            }

            manager.swipeAnimationSetting = setting
            binding.titleBar.isLifted = true
            binding.recyclerView.swipe()
        }
        fabRestore.setOnClickListener {
            val setting = RewindAnimationSetting().apply {
                direction = Direction.Bottom
                duration = Duration.Normal.duration
                interpolator = DecelerateInterpolator()
            }

            manager.rewindAnimationSetting = setting
            binding.recyclerView.rewind()
        }
    }

    private fun initToolbar() = binding.titleBarMenu.setOnMenuItemClickListener { item ->
        when (item.itemId) {
            R.id.menu_favorites -> {
                parentFragmentManager.beginTransaction()
                    .setTransition(TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(FavoritesFragment.TAG)
                    .add(android.R.id.content, FavoritesFragment.newInstance())
                    .commit()
                true
            }
            else -> false
        }
    }

    override fun itemOnClick(post: Post) {
        PostFragment.newInstance(post).show(parentFragmentManager, PostFragment.TAG)
    }

    override fun onResume() {
        super.onResume()
        initButtons()
        initCardStackView()
        if (lastPosition != 0) {
            binding.recyclerView.layoutManager!!.scrollToPosition(lastPosition)
        }
    }

    override fun onPause() {
        super.onPause()
        lastPosition = (binding.recyclerView.layoutManager as CardStackLayoutManager).topPosition
    }
}