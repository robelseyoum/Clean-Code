package com.robelseyoum3.cleancode.business.domain.util

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateUtil @Inject constructor(
    private val dateFormat: SimpleDateFormat) {

    //date format: "2020-07-23 HH:mm:ss"
    //2020-97-23
    fun removeTimeFromDateString(sd: String): String{
        return sd.substring(0, sd.indexOf(" "))
    }

    fun convertFirebaseTimestampToStringDate(timestamp: Timestamp): Timestamp {
        return Timestamp(timestamp.toDate())
    }

    fun convertStringToFirebaseTimestamp(date: String): Timestamp {
        return Timestamp(dateFormat.parse(date))
    }

    fun getCurrentTimeStamp(): String{
        return dateFormat.format(Date())
    }
}