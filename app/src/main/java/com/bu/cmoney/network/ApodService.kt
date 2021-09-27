package com.bu.cmoney.network

import android.graphics.BitmapFactory
import android.util.Log
import com.bu.cmoney.tool.hasNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class ApodService {

    val url = "https://raw.githubusercontent.com/cmmobile/NasaDataSet/main/apod.json"

    suspend fun getJsonString(urlString: String = url):String
    = withContext(Dispatchers.IO){
        var connection: HttpURLConnection? = null

        try {
            connection = URL(urlString).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 8000//連接超時
            connection.readTimeout = 8000//讀取超時
            connection.inputStream.use {inputStream->
                inputStream.bufferedReader().use {reader->
                    return@withContext reader.readText()
                }
            }
        }catch (e: IOException){
            Log.e("ApodService", e.toString())
            return@withContext ""
        }finally {
            connection?.disconnect()
        }
    }

    fun downloadImage(urlString: String) = flow<ImageDownloadStatus>{
        var connection: HttpURLConnection? = null
        var bufferedInputStream: BufferedInputStream? = null
        var outputStream: ByteArrayOutputStream? = null
        try {
            connection = URL(urlString).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 8000//連接超時
            connection.readTimeout = 8000//讀取超時

            val length = connection.contentLength
            val data = ByteArray(1024)
            var total: Int = 0
            var count: Int = 0

            bufferedInputStream = BufferedInputStream(connection.inputStream, 8192)
            outputStream = ByteArrayOutputStream()

            while (true){
                count = bufferedInputStream.read(data)
                if(count == -1)
                    break

                total += count

                outputStream.write(data, 0, count)

                emit(ImageDownloadStatus.Progress(total.times(100).div(length)))
            }

            val bitmap = BitmapFactory.decodeByteArray(outputStream.toByteArray(), 0, length)

            emit(ImageDownloadStatus.Success(bitmap))

        }catch (e: IOException){
            Log.e("ApodService", e.toString())
            val message = if(hasNetwork())
                "載入未完成...請重新載入"
            else
                "目前無網路"
            emit(ImageDownloadStatus.Error(message))
        }finally {
            bufferedInputStream?.close()
            outputStream?.close()
            connection?.disconnect()
        }
    }
}