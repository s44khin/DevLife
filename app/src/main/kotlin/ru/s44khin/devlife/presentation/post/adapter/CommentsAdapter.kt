package ru.s44khin.devlife.presentation.post.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.s44khin.devlife.R
import ru.s44khin.devlife.data.model.Comment

class CommentsAdapter(
    private val comments: List<Comment>
) : RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {

    class CommentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val author: TextView = itemView.findViewById(R.id.item_comment_author)
        val content: TextView = itemView.findViewById(R.id.item_comment_content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CommentsViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
    )

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val comment = comments[position]
        holder.author.text = comment.authorName
        holder.content.text = comment.text
    }

    override fun getItemCount() = comments.size
}