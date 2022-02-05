package ru.s44khin.devlife.presentation.favorites

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.s44khin.devlife.R
import ru.s44khin.devlife.data.model.Post
import ru.s44khin.devlife.databinding.FragmentFavoritesBinding
import ru.s44khin.devlife.presentation.favorites.adapter.FavoritesAdapter
import ru.s44khin.devlife.presentation.favorites.adapter.FavoritesDiffUtilCallback
import ru.s44khin.devlife.presentation.favorites.adapter.ItemHandler
import ru.s44khin.devlife.presentation.favorites.elm.Effect
import ru.s44khin.devlife.presentation.favorites.elm.Event
import ru.s44khin.devlife.presentation.favorites.elm.FavoritesReducer
import ru.s44khin.devlife.presentation.favorites.elm.State
import ru.s44khin.devlife.presentation.post.PostFragment
import ru.s44khin.devlife.utils.elmDialogFragment.mainComponent
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.core.ElmStoreCompat

class FavoritesFragment : ElmFragment<Event, Effect, State>(R.layout.fragment_favorites),
    ItemHandler {

    companion object {
        const val TAG = "FAVORITES_FRAGMENT"
        fun newInstance() = FavoritesFragment()
    }

    private val binding by viewBinding(FragmentFavoritesBinding::bind)
    private val adapter: FavoritesAdapter by lazy {
        FavoritesAdapter(itemHandler = this)
    }
    override val initEvent = Event.Ui.LoadPosts

    override fun createStore() = ElmStoreCompat(
        initialState = State(),
        reducer = FavoritesReducer(),
        actor = mainComponent.favoritesActor
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.titleBarMenu.setNavigationOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
        initRecyclerView()
        registerForContextMenu(binding.favoritesRecyclerView)
    }

    override fun render(state: State) {
        if (state.posts != null) {
            val callback = FavoritesDiffUtilCallback(adapter.posts, state.posts)
            val diffUtilResult = DiffUtil.calculateDiff(callback, true)
            adapter.posts = state.posts
            diffUtilResult.dispatchUpdatesTo(adapter)
        }

        if (!state.posts.isNullOrEmpty()) {
            binding.empty.isVisible = false
        }
    }

    private fun initRecyclerView() = binding.favoritesRecyclerView.apply {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = this@FavoritesFragment.adapter
    }

    override fun itemOnClick(post: Post) {
        PostFragment.newInstance(post).show(parentFragmentManager, PostFragment.TAG)
    }

    override fun removeOnClick(id: Int) {
        store.accept(Event.Ui.DeletePost(id))
    }
}