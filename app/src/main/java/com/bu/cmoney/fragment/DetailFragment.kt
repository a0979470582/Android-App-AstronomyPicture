package com.bu.cmoney.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bu.cmoney.R
import com.bu.cmoney.network.ImageDownloadStatus
import com.bu.cmoney.tool.DatePatternConvert
import com.bu.cmoney.viewmodel.ApodViewModel
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn

class DetailFragment: Fragment() {

    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: ApodViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        val hintTextView = view.findViewById<TextView>(R.id.hintTextView)
        val progressBar = view.findViewById<LinearProgressIndicator>(R.id.progressBar)
        val imageView = view.findViewById<ImageView>(R.id.imageView)

        args.apod.let{ apod ->
            val newDateString = DatePatternConvert().dateStringConvert(apod.date)

            view.findViewById<TextView>(R.id.dateTextView).text = newDateString
            view.findViewById<TextView>(R.id.titleTextView).text = apod.title
            view.findViewById<TextView>(R.id.copyrightTextView).text = apod.copyright
            view.findViewById<TextView>(R.id.descriptionTextView).text = apod.description
        }

        lifecycleScope.launchWhenStarted {
            viewModel.downloadImage(args.apod.hdurl)
                    .flowOn(Dispatchers.IO)
                    .collect {
                        when (it) {
                            is ImageDownloadStatus.Success -> {
                                delay(500)
                                imageView.setImageBitmap(it.bitmap)
                            }
                            is ImageDownloadStatus.Error -> {
                                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            }
                            is ImageDownloadStatus.Progress -> {
                                progressBar.setProgressCompat(it.progress, true)
                                if (it.progress < 100) {
                                    hintTextView.isVisible = true
                                    progressBar.isVisible = true
                                } else {
                                    delay(500)
                                    hintTextView.isVisible = false
                                    progressBar.isVisible = false
                                }
                            }
                        }
                    }
        }

        return view
    }


}