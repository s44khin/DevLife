package ru.s44khin.devlife.presentation.favorites.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import ru.s44khin.devlife.R
import ru.s44khin.devlife.data.model.Post
import ru.s44khin.devlife.presentation.card.adapter.PostDelegate

class FavoritesAdapter(
    val posts: List<Post>
) : RecyclerView.Adapter<PostDelegate.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostDelegate.PostViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
    )

    override fun onBindViewHolder(holder: PostDelegate.PostViewHolder, position: Int) {
        val post = posts[position]
        val context = holder.itemView.context

        holder.leftOverlay.visibility = View.GONE
        holder.rightOverlay.visibility = View.GONE
        holder.topOverlay.visibility = View.GONE
        holder.description.text = post.description

        val loader = CircularProgressDrawable(context)
        loader.strokeWidth = context.resources.displayMetrics.density * 5f
        loader.centerRadius = context.resources.displayMetrics.density * 20f
        loader.setColorSchemeColors(R.color.black)
        loader.start()

        Glide.with(context)
            .asGif()
            .load(post.gifURL)
            .placeholder(loader)
            .into(holder.gif)
    }

    override fun getItemCount() = posts.size
}