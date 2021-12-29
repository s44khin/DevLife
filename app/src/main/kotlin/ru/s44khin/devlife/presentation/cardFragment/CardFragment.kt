package ru.s44khin.devlife.presentation.cardFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import com.yuyakaido.android.cardstackview.*
import ru.s44khin.devlife.App
import ru.s44khin.devlife.data.model.Post
import ru.s44khin.devlife.databinding.FragmentCardBinding
import ru.s44khin.devlife.presentation.cardFragment.adapter.PaginationAdapterHelper
import ru.s44khin.devlife.presentation.cardFragment.adapter.PostAdapter
import ru.s44khin.devlife.presentation.cardFragment.elm.*
import ru.s44khin.devlife.presentation.postFragment.PostFragment
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.core.ElmStoreCompat

class FragmentLatest : CardFragment() {

    companion object {
        const val TAG = "LATEST"
        fun newInstance() = FragmentLatest()
    }

    override val type = Type.LATEST
}

class FragmentTop : CardFragment() {

    companion object {
        const val TAG = "TOP"
        fun newInstance() = FragmentTop()
    }

    override val type = Type.TOP
}

abstract class CardFragment : ElmFragment<Event, Effect, State>(), CardStackListener, ItemHandler {

    abstract val type: Type
    private var _binding: FragmentCardBinding? = null
    private val binding get() = _binding!!
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

    override fun createStore() = ElmStoreCompat(
        initialState = State(),
        reducer = CardReducer(),
        actor = CardActor(App.instance.repository, type)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCardStackView()
        initButtons()
    }

    override fun render(state: State) {
        if (state.posts.isNotEmpty()) {
            adapter.items = state.posts
        }
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        binding.titleBar.isLifted = true
    }

    override fun onCardSwiped(direction: Direction?) {

    }

    override fun onCardRewound() {

    }

    override fun onCardCanceled() {

    }

    override fun onCardAppeared(view: View?, position: Int) {

    }

    override fun onCardDisappeared(view: View?, position: Int) {

    }

    private fun initCardStackView() = binding.recyclerView.apply {
        manager = CardStackLayoutManager(context, this@CardFragment)

        manager.apply {
            setStackFrom(StackFrom.Bottom)
            setCanScrollHorizontal(true)
            setCanScrollVertical(true)
            setVisibleCount(3)
            setDirections(listOf(Direction.Top, Direction.Left, Direction.Right))
        }

        layoutManager = manager

        adapter = this@CardFragment.adapter
    }

    private fun initButtons() = binding.apply {
        fabSwap.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            binding.recyclerView.swipe()
        }
        fabLike.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Top)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            binding.titleBar.isLifted = true
            binding.recyclerView.swipe()
        }
        fabRestore.setOnClickListener {
            val setting = RewindAnimationSetting.Builder()
                .setDirection(Direction.Bottom)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(DecelerateInterpolator())
                .build()
            manager.setRewindAnimationSetting(setting)
            binding.recyclerView.rewind()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}