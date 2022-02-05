package ru.s44khin.devlife.presentation.post

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import ru.s44khin.devlife.data.model.Post
import ru.s44khin.devlife.databinding.FragmentPostBinding
import ru.s44khin.devlife.presentation.post.adapter.CommentsAdapter
import ru.s44khin.devlife.presentation.post.elm.Effect
import ru.s44khin.devlife.presentation.post.elm.Event
import ru.s44khin.devlife.presentation.post.elm.PostReducer
import ru.s44khin.devlife.presentation.post.elm.State
import ru.s44khin.devlife.utils.elmDialogFragment.ElmDialogFragment
import ru.s44khin.devlife.utils.elmDialogFragment.mainComponent
import vivid.money.elmslie.core.ElmStoreCompat

class PostFragment : ElmDialogFragment<Event, Effect, State>() {

    companion object {
        const val TAG = "POST_FRAGMENT"
        private const val POST = "POST"

        fun newInstance(post: Post) = PostFragment().apply {
            arguments = Bundle().apply {
                putParcelable(POST, post)
            }
        }
    }

    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!

    private val post: Post by lazy {
        requireArguments().getParcelable(POST)!!
    }

    override val initEvent by lazy {
        Event.Ui.LoadComments(post.id)
    }

    override fun createStore() = ElmStoreCompat(
        initialState = State(),
        reducer = PostReducer(),
        actor = mainComponent.postActor
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPost()
        initButtons()
    }

    override fun render(state: State) {
        if (state.comments.isNotEmpty()) {
            binding.postRecyclerView.adapter = CommentsAdapter(state.comments)
            binding.postShimmer.visibility = View.GONE
        }
    }

    private fun initPost() {
        Glide.with(requireContext())
            .asGif()
            .load(post.gifURL)
            .into(binding.postGif)

        binding.postDescription.text = post.description
        binding.postAuthor.text = post.author
        binding.postDate.text = post.date.dropLast(6)
        binding.postLikeCount.text = post.votes.toString()
        binding.postRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun initButtons() = binding.apply {
        cardShare.setOnClickListener {
            val text = "https://developerslife.ru/${post.id}"
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(sendIntent, null))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}