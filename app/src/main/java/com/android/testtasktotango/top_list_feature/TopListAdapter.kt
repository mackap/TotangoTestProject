package com.android.testtasktotango.top_list_feature

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.testtasktotango.R
import com.android.testtasktotango.core_comp.IRecItemClickListener
import com.android.testtasktotango.core_comp.data.pojo.Post
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.post_item.view.*


/**
 *Created by Makarov on 21.05.2019
 */
class TopListAdapter(
    topList: List<Post>,
    itemClickListener: IRecItemClickListener
) : RecyclerView.Adapter<TopListAdapter.TopListHolder>() {
    val TAG = javaClass.simpleName
    var mTopList = topList
    var mItemClickListener = itemClickListener
    lateinit var mContext: Context
    var currentTimeInMillisecond = System.currentTimeMillis()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TopListHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.post_item, null, false)
        mContext = viewGroup.context
        return TopListHolder(view)
    }

    override fun getItemCount(): Int {
        return mTopList.size
    }

    override fun onBindViewHolder(holder: TopListHolder, position: Int) {
        val post = mTopList.get(position)
        holder.tvTitle.text = post.title
        holder.tvAuthorName.text = mContext.getString(R.string.author) + post.author
        holder.tvComments.text = mContext.getString(R.string.comments) + post.numComments
        holder.tvDate.text = getHoursAgo(post.createdUtc.toLong()) + mContext.getString(R.string.hors_ago)

        // This is the simplest common way to loading images
        Picasso.get().load(post.thumbnail).into(holder.ivThumb)
        holder.ivThumb.setOnClickListener {
            mItemClickListener.onItemClick(post.url)
        }
    }

    private fun getHoursAgo(created: Long): String {

        return ((currentTimeInMillisecond - (created * 1000)) / (1000 * 60 * 60)).toString()
    }

    fun updateData(postList: List<Post>) {
        mTopList = postList
        notifyDataSetChanged()
    }


    class TopListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle = itemView.tv_title_post_list
        var tvAuthorName = itemView.tv_author_post_list
        var tvDate = itemView.tv_date_post_list
        var tvComments = itemView.tv_comments_post_list
        var ivThumb = itemView.iv_post_list

    }

}