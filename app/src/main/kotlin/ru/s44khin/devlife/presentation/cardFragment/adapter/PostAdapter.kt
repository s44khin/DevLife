package ru.s44khin.devlife.presentation.cardFragment.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.s44khin.devlife.data.model.Post
import ru.s44khin.devlife.presentation.cardFragment.ItemHandler

val diffUtilCallback = object : DiffUtil.ItemCallback<Post>() {

    override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem
}

class PostAdapter(
    private val paginationAdapterHelper: PaginationAdapterHelper,
    itemHandler: ItemHandler
) : AsyncListDifferDelegationAdapter<Post>(
    diffUtilCallback,
    PostDelegate(itemHandler)
) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        paginationAdapterHelper.onBind(position, itemCount)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any?>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        paginationAdapterHelper.onBind(position, itemCount)
    }
}