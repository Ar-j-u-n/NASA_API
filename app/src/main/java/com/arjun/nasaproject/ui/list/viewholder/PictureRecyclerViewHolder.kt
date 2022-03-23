package com.arjun.nasaproject.ui.list.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.arjun.nasaproject.R
import com.arjun.nasaproject.databinding.AstronomyPictureRecyclerViewHolderBinding
import com.arjun.nasaproject.ui.list.data.PictureItem
import com.arjun.nasaproject.util.loadImage

class PictureRecyclerViewHolder(
    private val binding: AstronomyPictureRecyclerViewHolderBinding,
    private val pictureClickAction: (date: String, title: String) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PictureItem) {
        binding.picture.setOnClickListener {
            pictureClickAction(item.date, item.name)
        }
        binding.picture.loadImage(item.picture, R.drawable.ic_twotone_scatter_plot)
        binding.label.text = item.name
        binding.txtDesc.text = item.date
    }

}
