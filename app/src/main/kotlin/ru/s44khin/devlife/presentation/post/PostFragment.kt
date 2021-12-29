package ru.s44khin.devlife.presentation.post

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import ru.s44khin.devlife.App
import ru.s44khin.devlife.R
import ru.s44khin.devlife.data.model.Post
import ru.s44khin.devlife.databinding.FragmentPostBinding
import ru.s44khin.devlife.presentation.post.adapter.CommentsAdapter
import ru.s44khin.devlife.presentation.post.elm.*
import ru.s44khin.devlife.utils.elmDialogFragment.ElmDialogFragment
import vivid.money.elmslie.core.ElmStoreCompat

class PostFragment : ElmDialogFragment<Event, Effect, State>() {

    companion object {
        const val TAG = "POST_FRAGMENT"
        private var postNull: Post? = null
        private val post get() = postNull!!

        fun newInstance(post: Post): PostFragment {
            postNull = post
            return PostFragment()
        }
    }

    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!

    override val initEvent = Event.Ui.LoadComments(post.id)

    override fun createStore() = ElmStoreCompat(
        initialState = State(),
        reducer = PostReducer(),
        actor = PostActor(App.instance.repository)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostBinding.inflate(inflater, container, false)
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
        postIcShare.setOnClickListener {
            val text = "https://developerslife.ru/${post.id}"
            val clipboardManager = context?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            clipboardManager.setPrimaryClip(ClipData.newPlainText("url", text))
            Snackbar.make(
                binding.root,
                requireContext().getString(R.string.link_copied),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}