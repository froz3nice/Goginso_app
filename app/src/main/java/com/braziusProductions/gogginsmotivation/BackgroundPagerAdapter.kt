package com.braziusProductions.gogginsmotivation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class BackgroundPagerAdapter : RecyclerView.Adapter<BackgroundPagerAdapter.BackgroundViewHolder>() {
    private val backgrounds = listOf(
        R.drawable.gog,
        R.drawable.goggins2,
        R.drawable.goggins3,
        R.drawable.goggins4,
    )

    class BackgroundViewHolder(val imageView: ImageView) : RecyclerView.ViewHolder(imageView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BackgroundViewHolder {
        val imageView = LayoutInflater.from(parent.context)
            .inflate(R.layout.background_item, parent, false) as ImageView
        return BackgroundViewHolder(imageView)
    }

    override fun onBindViewHolder(holder: BackgroundViewHolder, position: Int) {
        holder.imageView.setImageResource(backgrounds[position])
    }

    override fun getItemCount() = backgrounds.size
}