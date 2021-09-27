package com.bu.cmoney.tool

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

/**
 * input: "2006-12-31"
 * output: "2006 Dec. 31"
 * It can set other pattern.
 */
class DatePatternConvert{

    companion object{
        const val PATTERN1 = "yyyy-MM-dd"
        const val PATTERN2 = "yyyy MMM. dd"
    }

    fun dateStringConvert(
            dateString:String,
            oldPattern: String = PATTERN1,
            newPattern: String = PATTERN2,
            locale: Locale = Locale("en", "US")
    ): String = try {
            val time = SimpleDateFormat(oldPattern, locale).parse(dateString).time
            val output = SimpleDateFormat(newPattern, locale).format(time)
            output
        }catch(e: IllegalArgumentException){
            Log.e("DateConvert", "if the Format cannot format the given object.")
            dateString
        }catch(e: ParseException) {
            Log.e("DateConvert", "if the beginning of the specified string cannot be parsed.")
            dateString
        }
}