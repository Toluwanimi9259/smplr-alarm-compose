package com.techafresh.smplralarmcompose

import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.techafresh.smplralarmcompose.ui.theme.SmplrAlarmComposeTheme
import de.coldtea.smplr.smplralarm.alarmNotification
import de.coldtea.smplr.smplralarm.channel
import de.coldtea.smplr.smplralarm.smplrAlarmSet

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmplrAlarmComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "",
                        modifier = Modifier.padding(innerPadding)
                    )

                    val hour = 13
                    val minute = 5

                    val snoozeIntent = Intent(applicationContext, ActionReceiver::class.java).apply {
                        action = "ACTION_SNOOZE"
                        putExtra("HOUR", hour)
                        putExtra("MINUTE", minute)
                    }

                    val dismissIntent = Intent(applicationContext, ActionReceiver::class.java).apply {
                        action = "ACTION_DISMISS"
                    }

                    smplrAlarmSet(applicationContext) {
                        hour { hour }
                        min { minute }

                        notification {
                            alarmNotification {
                                smallIcon { R.drawable.ic_launcher_foreground }
                                title { "Alarm Title" }
                                message { "Alarm Message" }
                                bigText { "Alarm Big Text" }
                                firstButtonText { "Snooze" }
                                secondButtonText { "Dismiss" }
                                firstButtonIntent { snoozeIntent }
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
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Simple Alarm Test",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SmplrAlarmComposeTheme {
        Greeting("Android")
    }
}