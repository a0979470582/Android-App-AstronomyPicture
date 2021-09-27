package com.bu.cmoney.repository

import com.bu.cmoney.CMoneyApplication
import com.bu.cmoney.model.Apod
import com.bu.cmoney.network.ApodService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.File


class ApodRepository(
    val apodService: ApodService = ApodService()
) {

    suspend fun getApodList(cacheFileName: String = "apod.json"): List<Apod>
    = withContext(Dispatchers.IO) {
        val jsonFile = File(CMoneyApplication.context.cacheDir, cacheFileName)
        var jsonArray: JSONArray

        if(jsonFile.exists()){
            jsonFile.inputStream().use {inputStream->
                inputStream.bufferedReader().use {reader->
                    jsonArray = JSONArray(reader.readText())
                }
            }
        }else{
            val jsonString = apodService.getJsonString()
            if(jsonString.isNotBlank()) {
                launch {
                    jsonFile.bufferedWriter().use { writer ->
                        writer.write((jsonString))
                    }
                }
                jsonArray = JSONArray(jsonString)
            }else{
                return@withContext emptyList()
            }
        }

        return@withContext jsonArrayConvertTo(jsonArray)
    }

    private fun jsonArrayConvertTo(jsonArray: JSONArray): List<Apod>{
        val apodList = ArrayList<Apod>()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val apod = Apod(
                    description = jsonObject.getString("description"),
                    copyright = jsonObject.getString("copyright"),
                    title = jsonObject.getString("title"),
                    url = jsonObject.getString("url"),
                    apod_site = jsonObject.getString("apod_site"),
                    date = jsonObject.getString("date"),
                    media_type = jsonObject.getString("media_type"),
                    hdurl = jsonObject.getString("hdurl")
            )
            apodList.add(apod)
        }
        return apodList
    }

    fun downloadImage(urlString: String) = apodService.downloadImage(urlString)
}