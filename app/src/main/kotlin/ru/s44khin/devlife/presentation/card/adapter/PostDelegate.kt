package ru.s44khin.devlife.presentation.card.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.request.ImageRequest
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import ru.s44khin.devlife.data.model.Post
import ru.s44khin.devlife.databinding.ItemPostBinding
import ru.s44khin.devlife.utils.loader

class PostDelegate(
    private val itemHandler: ItemHandler
) : AbsListItemAdapterDelegate<Post, Post, PostDelegate.ViewHolder>() {

    class ViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root)

    override fun isForViewType(item: Post, items: MutableList<Post>, position: Int) = true

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(post: Post, holder: ViewHolder, payloads: MutableList<Any>) {
        val context = holder.itemView.context

        holder.binding.apply {
            description.text = post.description

            gif.apply {
                hierarchy.setPlaceholderImage(context.loader)
                controller = Fresco.newDraweeControllerBuilder().apply {
                    imageRequest = ImageRequest.fromUri(post.gifURL)
                    autoPlayAnimations = true
                }.build()
            }

            root.setOnClickListener {
                itemHandler.itemOnClick(post)
            }
        }
    }
}