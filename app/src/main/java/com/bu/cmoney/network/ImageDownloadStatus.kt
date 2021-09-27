package com.bu.cmoney.network

import android.graphics.Bitmap

sealed class ImageDownloadStatus {
    data class Success(val bitmap: Bitmap): ImageDownloadStatus()
    data class Error(val message: String): ImageDownloadStatus()
    data class Progress(val progress: Int): ImageDownloadStatus()
}