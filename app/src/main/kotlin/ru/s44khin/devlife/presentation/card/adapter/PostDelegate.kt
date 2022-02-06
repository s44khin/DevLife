package ru.s44khin.devlife.presentation.card.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.request.ImageRequest
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import ru.s44khin.devlife.R
import ru.s44khin.devlife.data.model.Post
import ru.s44khin.devlife.databinding.ItemPostBinding

class PostDelegate(
    private val itemHandler: ItemHandler
) : AbsListItemAdapterDelegate<Post, Post, PostDelegate.ViewHolder>() {

    class ViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root)

    override fun isForViewType(item: Post, items: MutableList<Post>, position: Int) = true

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(item: Post, holder: ViewHolder, payloads: MutableList<Any>) {
        val context = holder.itemView.context

        holder.binding.description.text = item.description

        val loader = CircularProgressDrawable(context)
        loader.strokeWidth = context.resources.displayMetrics.density * 5f
        loader.centerRadius = context.resources.displayMetrics.density * 20f
        loader.setColorSchemeColors(R.color.primary)
        loader.start()

        holder.binding.gif.hierarchy.setPlaceholderImage(loader)

        holder.binding.gif.controller = Fresco.newDraweeControllerBuilder()
            .setImageRequest(ImageRequest.fromUri(item.gifURL))
            .setAutoPlayAnimations(true)
            .build()

        holder.itemView.setOnClickListener {
            itemHandler.itemOnClick(item)
        }
    }
}