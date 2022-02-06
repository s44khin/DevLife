package ru.s44khin.devlife.presentation.favorites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.request.ImageRequest
import ru.s44khin.devlife.R
import ru.s44khin.devlife.data.model.Post
import ru.s44khin.devlife.databinding.ItemFavoriteBinding

class FavoritesAdapter(
    var posts: List<Post> = emptyList(),
    private val itemHandler: ItemHandler
) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        val context = holder.itemView.context

        holder.binding.description.text = post.description

        val loader = CircularProgressDrawable(context)
        loader.strokeWidth = context.resources.displayMetrics.density * 5f
        loader.centerRadius = context.resources.displayMetrics.density * 20f
        loader.setColorSchemeColors(R.color.primary)
        loader.start()

        holder.binding.gif.hierarchy.setPlaceholderImage(loader)

        holder.binding.gif.controller = Fresco.newDraweeControllerBuilder()
            .setImageRequest(ImageRequest.fromUri(post.gifURL))
            .setAutoPlayAnimations(true)
            .build()

        holder.itemView.setOnClickListener {
            itemHandler.itemOnClick(post)
        }

        holder.itemView.setOnCreateContextMenuListener { contextMenu, view, _ ->
            if (contextMenu != null && view != null) {
                contextMenu.add(0, view.id, 0, context.getString(R.string.delete))
                    .setOnMenuItemClickListener {
                        itemHandler.removeOnClick(post.id)
                        true
                    }
            }
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.itemView.setOnCreateContextMenuListener(null)
    }

    override fun getItemCount() = posts.size
}