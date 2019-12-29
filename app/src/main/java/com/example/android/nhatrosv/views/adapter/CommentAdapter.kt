package com.example.android.nhatrosv.views.adapter

import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.nhatrosv.R
import com.example.android.nhatrosv.models.Comment
import com.example.android.nhatrosv.utils.CircleTransform
import com.example.android.nhatrosv.utils.inflate
import com.squareup.picasso.Picasso

data class CommentAdapter(var mComments: List<Comment>) :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val inflatedView = parent.inflate(R.layout.comment_item, false)
        return CommentViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return mComments.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = mComments[position]

        holder.textViewName.text = "${comment.firstName} ${comment.lastName}"
        holder.textViewContent.text = comment.content
        holder.textViewDate.text = comment.date
        Picasso.get()
            .load(Uri.parse(comment.photoUrl))
            .transform(CircleTransform())
            .into(holder.imageViewAvatar)

    }

    fun updateComment(comments: List<Comment>) {
        mComments = comments
        notifyDataSetChanged()
    }

    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view = itemView
        val imageViewAvatar: ImageView = view.findViewById(R.id.imageView_avatar)
        val textViewName: TextView = view.findViewById(R.id.textView_name)
        val textViewContent: TextView = view.findViewById(R.id.textView_content)
        val textViewDate: TextView = view.findViewById(R.id.textView_date)
    }

}