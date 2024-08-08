package com.example.nestscrollview

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView

class VpgAdapter(private val recyclerViews: List<RecyclerView>) :
    RecyclerView.Adapter<VpgAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // 创建一个FrameLayout或直接使用RecyclerView作为ViewHolder的根视图
        val frameLayout = FrameLayout(parent.context)
        frameLayout.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        return ViewHolder(frameLayout)
    }

    override fun getItemCount(): Int {
        return recyclerViews.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val frameLayout = holder.itemView as FrameLayout
        frameLayout.removeAllViews()
        frameLayout.addView(recyclerViews[position])
    }
}
