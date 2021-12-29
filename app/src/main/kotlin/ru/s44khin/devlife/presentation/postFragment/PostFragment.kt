package ru.s44khin.devlife.presentation.postFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import ru.s44khin.devlife.App
import ru.s44khin.devlife.data.model.Post
import ru.s44khin.devlife.databinding.FragmentPostBinding
import ru.s44khin.devlife.presentation.elmDialogFragment.ElmDialogFragment
import ru.s44khin.devlife.presentation.postFragment.elm.*
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
    }

    override fun render(state: State) {

    }

    private fun initPost() {
        Glide.with(requireContext())
            .asGif()
            .load(post.gifURL)
            .into(binding.postGif)

        binding.postDescription.text = post.description
        binding.postAuthor.text = post.author
        binding.postDate.text = post.date.dropLast(6)
        binding.postLikesCount.text = post.votes.toString()
    }
}