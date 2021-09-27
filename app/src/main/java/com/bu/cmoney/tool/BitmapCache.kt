package com.bu.cmoney.tool

import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.toolbox.ImageLoader.ImageCache


class BitmapCache: ImageCache {

    private val mCache: LruCache<String, Bitmap>

    init {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        val cacheSize = maxMemory / 8
        mCache = object: LruCache<String, Bitmap>(cacheSize){
            override fun sizeOf(string: String, bitmap: Bitmap): Int {
                return bitmap.allocationByteCount/1024
            }
        }
    }

    override fun getBitmap(url: String): Bitmap? {
        return mCache.get(url)
    }

    override fun putBitmap(url: String, bitmap: Bitmap?) {
        mCache.put(url, bitmap)
    }
}