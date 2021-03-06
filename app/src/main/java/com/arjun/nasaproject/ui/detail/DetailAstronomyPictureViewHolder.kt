package com.arjun.nasaproject.ui.detail

import androidx.core.view.isVisible
import com.arjun.nasaproject.R
import com.arjun.nasaproject.databinding.DetailAstronomyPictureFragmentBinding
import com.arjun.nasaproject.service.model.Apod
import com.arjun.nasaproject.ui.common.ScreenUiData
import com.arjun.nasaproject.ui.common.State
import com.arjun.nasaproject.util.loadImage
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class DetailAstronomyPictureViewHolder(
    private val binding: DetailAstronomyPictureFragmentBinding,
    private val viewModel: DetailAstronomyPictureViewModel,
    private val pictureClickAction: (pictureUrl: String, pictureUrlHd: String) -> Unit,
) {

    private val compositeDisposable = CompositeDisposable()

    init {
        compositeDisposable.add(viewModel.uiDataSource.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { screenUiData ->
                updateUiDataForChange(screenUiData)
            })
    }

    private fun updateUiDataForChange(screenUiData: ScreenUiData<Apod>) {
        when (screenUiData.state) {
            State.LOADING -> {
                binding.progress.isVisible = true
                binding.content.isVisible = false
            }
            State.IDLE -> {
                binding.progress.isVisible = false
                binding.content.isVisible = true

                binding.description.text = screenUiData.data.explanation
                binding.copyright.text = screenUiData.data.copyright
                binding.picture.loadImage(screenUiData.data.url, R.drawable.ic_twotone_scatter_plot)
                binding.button.setOnClickListener {
                    val pictureUrlHd = if (!screenUiData.data.hdUrl.isBlank()) {
                        screenUiData.data.hdUrl
                    } else {
                        screenUiData.data.url
                    }
                    pictureClickAction(screenUiData.data.url, pictureUrlHd)
                }
            }
            State.ERROR -> {
                binding.progress.isVisible = false
                binding.content.isVisible = false
                if (!screenUiData.error.isNullOrEmpty()) {
                    Snackbar.make(binding.root, screenUiData.error, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.retry) {
                            viewModel.fetchData()
                        }.show()
                }
            }
            State.EMPTY -> {
                Timber.w("empty state screen is not supposed to happen for detail screen")
            }
        }
    }

    fun release() {
        compositeDisposable.clear()
    }

}