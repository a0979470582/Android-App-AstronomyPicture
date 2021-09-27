package com.bu.cmoney.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bu.cmoney.model.Apod
import com.bu.cmoney.repository.ApodRepository
import kotlinx.coroutines.launch

class ApodViewModel(
        private val repository: ApodRepository = ApodRepository()
): ViewModel() {

    val apodListLiveData = MutableLiveData<List<Apod>>()

    init {
        viewModelScope.launch {
            apodListLiveData.value = repository.getApodList()
        }
    }

    fun downloadImage(urlString: String) = repository.downloadImage(urlString)
}