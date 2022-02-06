package ru.s44khin.devlife.presentation.favorites.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.request.ImageRequest
import ru.s44khin.devlife.R
import ru.s44khin.devlife.data.model.Post
import ru.s44khin.devlife.presentation.card.adapter.PostDelegate

class FavoritesAdapter(
    var posts: List<Post> = emptyList(),
    private val itemHandler: ItemHandler
) : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    class FavoritesViewHolder(
        itemView: View
    ) : PostDelegate.PostViewHolder(itemView) {
        val topOverlay: FrameLayout = itemView.findViewById(R.id.top_overlay)
        val leftOverlay: FrameLayout = itemView.findViewById(R.id.left_overlay)
        val rightOverlay: FrameLayout = itemView.findViewById(R.id.right_overlay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FavoritesViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
    )

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val post = posts[position]
        val context = holder.itemView.context

        holder.leftOverlay.visibility = View.GONE
        holder.rightOverlay.visibility = View.GONE
        holder.topOverlay.visibility = View.GONE
        holder.description.text = post.description

        val loader = CircularProgressDrawable(context)
        loader.strokeWidth = context.resources.displayMetrics.density * 5f
        loader.centerRadius = context.resources.displayMetrics.density * 20f
        loader.setColorSchemeColors(R.color.primary)
        loader.start()

        holder.gif.hierarchy.setPlaceholderImage(loader)

        holder.gif.controller = Fresco.newDraweeControllerBuilder()
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

    override fun onViewRecycled(holder: FavoritesViewHolder) {
        super.onViewRecycled(holder)
        holder.itemView.setOnCreateContextMenuListener(null)
    }

    override fun getItemCount() = posts.size
}