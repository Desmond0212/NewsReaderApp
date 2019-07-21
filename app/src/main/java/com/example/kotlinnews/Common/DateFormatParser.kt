package com.example.kotlinnews.Common

import java.text.SimpleDateFormat
import java.util.*

object DateFormatParser
{
    fun parse(params: String): Date
    {
        var input = params
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz")

        if (input.endsWith("Z"))
        {
            input = input.substring(0, input.length -1) + "GMT-00:00"
        }
        else
        {
            val inset = 6
            val startText = input.subSequence(0, input.length - inset)
            val endText = input.substring(input.length - inset, input.length)

            input = StringBuilder(startText).append("GMT").append(endText).toString()
        }

        return dateFormat.parse(input)
    }

    fun toString(date: Date): String
    {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz")
        val timeZone = TimeZone.getTimeZone("UTC")

        dateFormat.timeZone = timeZone

        val output = dateFormat.format(date)
        val inset0 = 9
        val inset1 = 6
        val s0 = output.substring(0, output.length - inset0)
        val s1 = output.subSequence(output.length - inset1, output.length)
        var result = s0 + s1
        result = result.replace("UTC".toRegex(), "+00:00")

        return result
    }
}