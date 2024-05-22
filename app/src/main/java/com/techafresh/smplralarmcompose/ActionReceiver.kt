package com.techafresh.smplralarmcompose

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import de.coldtea.smplr.smplralarm.alarmNotification
import de.coldtea.smplr.smplralarm.apis.SmplrAlarmAPI.Companion.SMPLR_ALARM_NOTIFICATION_ID
import de.coldtea.smplr.smplralarm.channel
import de.coldtea.smplr.smplralarm.smplrAlarmSet
import java.util.*

class ActionReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationId = intent.getIntExtra(SMPLR_ALARM_NOTIFICATION_ID, -1)
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val now = getHourAndMinute()

        if(intent.action == "ACTION_SNOOZE"){
//            val updatedTime = addOneMinute(intent.getIntExtra("HOUR", now.first), intent.getIntExtra("MINUTE", now.second))

            notificationManager.cancel(notificationId)

            val hour = intent.getIntExtra("HOUR", now.first)
            val minute = intent.getIntExtra("MINUTE", now.second)


            val dismissIntent = Intent(context, ActionReceiver::class.java).apply {
                action = "ACTION_DISMISS"
            }

            smplrAlarmSet(context){
                hour { hour }
                min { minute + 1 }

                notification {
                    alarmNotification {
                        smallIcon { R.drawable.ic_launcher_foreground }
                        title { "Alarm Title Again" }
                        message { "Alarm Message Again" }
                        bigText { "Alarm Big Text Again" }
                        autoCancel { true }
                        secondButtonText { "Dismiss" }
                        secondButtonIntent { dismissIntent }
                    }
                }
                notificationChannel {
                    channel {
                        importance { NotificationManager.IMPORTANCE_HIGH }
                        showBadge { false }
                        name { "Alarm Channel" }
                        description { "This notification channel is created by Techafresh" }
                    }
                }
            }
        }
        if (intent.action == "ACTION_DISMISS"){
            notificationManager.cancel(notificationId)
        }
        if (intent.action == "ACTION_NOTIFICATION_DISMISS"){
//            Timber.i(" Moin --> Dismissed notification: $notificationId")
        }
    }

    private fun addOneMinute(hour: Int, minute: Int): Pair<Int, Int>{
        var mMinute = minute + 1
        var mHour = hour

        if(mMinute == 60){
            mMinute -= 60
            mHour += 1
        }

        if (mHour > 23)
            mHour = 0

        return mHour to mMinute
    }

    private fun getHourAndMinute(): Pair<Int, Int> = Calendar.getInstance().let {
            it.get(Calendar.HOUR_OF_DAY) to it.get(Calendar.MINUTE)
        }
}