package ru.s44khin.devlife.presentation.card.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import ru.s44khin.devlife.R
import ru.s44khin.devlife.data.model.Post

class PostDelegate(
    private val itemHandler: ItemHandler
) : AbsListItemAdapterDelegate<Post, Post, PostDelegate.PostViewHolder>() {

    open class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gif: ImageView = itemView.findViewById(R.id.gif)
        val description: TextView = itemView.findViewById(R.id.description)
    }

    override fun isForViewType(item: Post, items: MutableList<Post>, position: Int) = true

    override fun onCreateViewHolder(parent: ViewGroup): PostViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(itemView)
    }

    override fun onBindViewHolder(item: Post, holder: PostViewHolder, payloads: MutableList<Any>) {
        val context = holder.itemView.context

        holder.description.text = item.description

        val loader = CircularProgressDrawable(context)
        loader.strokeWidth = context.resources.displayMetrics.density * 5f
        loader.centerRadius = context.resources.displayMetrics.density * 20f
        loader.setColorSchemeColors(R.color.black)
        loader.start()

        Glide.with(context)
            .asGif()
            .load(item.gifURL)
            .placeholder(loader)
            .into(holder.gif)

        holder.itemView.setOnClickListener {
            itemHandler.itemOnClick(item)
        }
    }
}