package ru.s44khin.devlife.presentation.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.s44khin.devlife.App
import ru.s44khin.devlife.data.model.Post
import ru.s44khin.devlife.databinding.FragmentFavoritesBinding
import ru.s44khin.devlife.presentation.card.adapter.ItemHandler
import ru.s44khin.devlife.presentation.favorites.adapter.FavoritesAdapter
import ru.s44khin.devlife.presentation.favorites.elm.*
import ru.s44khin.devlife.presentation.post.PostFragment
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.core.ElmStoreCompat

class FavoritesFragment : ElmFragment<Event, Effect, State>(), ItemHandler {

    companion object {
        const val TAG = "FAVORITES_FRAGMENT"
        fun newInstance() = FavoritesFragment()
    }

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    override val initEvent = Event.Ui.LoadPosts

    override fun createStore() = ElmStoreCompat(
        initialState = State(),
        reducer = FavoritesReducer(),
        actor = FavoritesActor(App.instance.database)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.titleBarMenu.setNavigationOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
        initRecyclerView()
    }

    override fun render(state: State) {
        binding.shimmer.isVisible = state.isLoading

        if (state.posts != null && state.posts.isNotEmpty()) {
            binding.favoritesRecyclerView.adapter = FavoritesAdapter(state.posts, this)
        }

        if (state.posts != null && state.posts.isEmpty()) {
            binding.emptyIcon.visibility = View.VISIBLE
            binding.emptyText.visibility = View.VISIBLE
        }
    }

    private fun initRecyclerView() = binding.favoritesRecyclerView.apply {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                binding.titleBar.isLifted = recyclerView.canScrollVertically(-1)
            }
        })
    }

    override fun itemOnClick(post: Post) {
        PostFragment.newInstance(post).show(parentFragmentManager, PostFragment.TAG)
    }
}