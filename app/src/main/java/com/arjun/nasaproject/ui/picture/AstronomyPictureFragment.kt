package com.arjun.nasaproject.ui.picture

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.arjun.nasaproject.R
import com.arjun.nasaproject.databinding.AstronomyPictureFragmentBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class AstronomyPictureFragment : Fragment() {

    private val args by navArgs<AstronomyPictureFragmentArgs>()

    private var _binding: AstronomyPictureFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AstronomyPictureFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadPicture()
    }

    private fun loadPicture() {
        Glide.with(this)
            .load(args.pictureUrlHd)
            // display quickly the SD picture that should already be in the cache of the app
            .thumbnail(Glide.with(this).load(args.pictureUrl).priority(Priority.IMMEDIATE))
            .apply(
                RequestOptions()
                    .error(R.drawable.ic_connection_error)
            )
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    binding.picture.setImageDrawable(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) = Unit
            })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}