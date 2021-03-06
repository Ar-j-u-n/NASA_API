package com.arjun.nasaproject.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arjun.nasaproject.R
import com.arjun.nasaproject.databinding.AstronomyPictureRecyclerViewHolderBinding
import com.arjun.nasaproject.ui.list.data.AstronomyPictureRecyclerItem
import com.arjun.nasaproject.ui.list.data.LoadingItem
import com.arjun.nasaproject.ui.list.data.PictureItem
import com.arjun.nasaproject.ui.list.viewholder.LoadingRecyclerViewHolder
import com.arjun.nasaproject.ui.list.viewholder.PictureRecyclerViewHolder
import timber.log.Timber

class AstronomyPictureAdapter(private val pictureClickAction: (date: String, title: String) -> Unit) :
    ListAdapter<AstronomyPictureRecyclerItem, RecyclerView.ViewHolder>(diffCallback) {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            AstronomyPictureRecyclerItem.ViewType.LOADING_VIEW.type -> {
                LoadingRecyclerViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.loading_recycler_view_holder, parent, false)
                )
            }
            AstronomyPictureRecyclerItem.ViewType.PICTURE_VIEW.type -> {
                PictureRecyclerViewHolder(
                    AstronomyPictureRecyclerViewHolderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ), pictureClickAction
                )
            }
            else -> {
                Timber.w("view type not managed in adapter")
                throw IllegalStateException("Unsupported ViewType")
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val data = getItem(position)) {
            is LoadingItem -> {
                // nothing to bind there
            }
            is PictureItem -> {
                holder as PictureRecyclerViewHolder
                holder.bind(data)
            }
            else -> {
                Timber.w("binding of data $data not managed")
            }
        }
    }

    override fun getItemViewType(position: Int) = getItem(position).getType()

    override fun getItemId(position: Int) = getItem(position).getUniqueId()

    companion object {
        private val diffCallback = object :
            DiffUtil.ItemCallback<AstronomyPictureRecyclerItem>() {

            override fun areItemsTheSame(
                oldItem: AstronomyPictureRecyclerItem,
                newItem: AstronomyPictureRecyclerItem,
            ): Boolean {
                if (oldItem.getType() == newItem.getType()) {
                    return oldItem.isSameItem(newItem)
                }
                return false
            }

            override fun areContentsTheSame(
                oldItem: AstronomyPictureRecyclerItem,
                newItem: AstronomyPictureRecyclerItem,
            ): Boolean {
                return oldItem.isSameContent(newItem)
            }
        }
    }

}
