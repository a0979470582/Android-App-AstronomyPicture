package com.bu.cmoney.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.bu.cmoney.R
import com.bu.cmoney.adapter.ApodAdapter
import com.bu.cmoney.viewmodel.ApodViewModel

class ApodFragment: Fragment() {

    private val viewModel: ApodViewModel by activityViewModels()
    private val adapter by lazy { ApodAdapter(fragment = this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_apod, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.setHasFixedSize(true)
        recyclerView.setAdapter(adapter)

        viewModel.apodListLiveData.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

        return view
    }
}