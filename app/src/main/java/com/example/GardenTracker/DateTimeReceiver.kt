package com.example.GardenTracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.GardenTracker.fragments.CropFragment
import java.util.*

// This Broadcast Receiver will be registered so that
// we're (hopefully) constantly listening to the time
// and it will then update our crops time variables and check if they're ready
class DateTimeReceiver(timeData: MainActivity.DateTimeHolder) : BroadcastReceiver() {

    private val TAG = "DATE_TIME_RECEIVER"

    private var listener: BroadcastReceiverListener? = null
    private var time: MainActivity.DateTimeHolder = timeData

    override fun onReceive(context: Context?, intent: Intent?) {

        if (context is BroadcastReceiverListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement BroadcastReceiverListener")
        }

        Log.d(TAG, "Received an intent: ${intent.toString()}")
        if (intent != null) {
            if (intent.action == "android.intent.action.TIME_TICK") { // Check intent matches
                if (time.currHour != GregorianCalendar.  // Only perform updates at each hour
                    getInstance(Locale("en_US@calendar=english"))
                        .get(Calendar.HOUR_OF_DAY)) {

                    Log.d(TAG, "Updating app's date information")

                    // Update the app's current date data
                    time.currYear = GregorianCalendar.getInstance(Locale("en_US@calendar=english"))
                        .get(Calendar.YEAR)
                    time.currMonth = GregorianCalendar.getInstance(Locale("en_US@calendar=english"))
                        .get(Calendar.MONTH)
                    time.currDay = GregorianCalendar.getInstance(Locale("en_US@calendar=english"))
                        .get(Calendar.DAY_OF_MONTH)
                    time.currHour = GregorianCalendar.getInstance(Locale("en_US@calendar=english"))
                        .get(Calendar.HOUR_OF_DAY)

                    // Check and update crops for watering
                    listener?.timeUpdateCrops()
                }
            }
        }

        listener = null
    }

    interface BroadcastReceiverListener {
        fun timeUpdateCrops()
    }
}