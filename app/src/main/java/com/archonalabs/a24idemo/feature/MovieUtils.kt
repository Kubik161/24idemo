package com.archonalabs.a24idemo.feature

import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

/**
 * Created by Jakub Juroska on 10/15/20.
 */
object MovieUtils {

    val apiDateFormat = SimpleDateFormat("YYYY-MM-dd")

    fun calculateStartDate(days: Int): String? {
        val startDateEpoch = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val startDate = LocalDate.now().minusDays(days.toLong())
            Date.from(startDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
        } else {
            val today = Calendar.getInstance()
            today.add(Calendar.DAY_OF_YEAR, -days)
            today.time
        }

        Timber.d("date is: %s", startDateEpoch)

        return apiDateFormat.format(startDateEpoch)
    }
}