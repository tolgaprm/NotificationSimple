package com.prmto.notificationsimple

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.prmto.notificationsimple.ui.theme.NotificationSimpleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            NotificationSimpleTheme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    val builder = NotificationCompat.Builder(context, "channelId")
                        .setSmallIcon(R.drawable.sport_icon)
                        .setContentTitle("Spor Bildirimleri")
                        .setContentText("Nba finallerinde Denver Nuggets ve Miami Heat.")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                    createNotificationChannel(context)

                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = {
                            NotificationManagerCompat.from(context).notify(1, builder.build())
                        }) {
                            Text(text = getString(R.string.show_notification))
                        }

                        Button(onClick = {
                            NotificationManagerCompat.from(context).notify(
                                1, builder
                                    .setContentText("Nba finallerinde Denver Nuggets ve Miami Heat karşılaşacak. Denver'de dikkat çeken oyuncu Nikola Jokic")
                                    .setStyle(
                                        NotificationCompat.BigTextStyle()
                                            .bigText("Nba finallerinde Denver Nuggets ve Miami Heat karşılaşacak. Denver'de dikkat çeken oyuncu Nikola Jokic, Miami tarafında dikkat çeken oyuncu Jimmy Butler")
                                    ).build()
                            )
                        }) {
                            Text(text = getString(R.string.update_notification))
                        }

                        Button(onClick = {
                            NotificationManagerCompat.from(context).cancel(1)
                        }) {
                            Text(text = getString(R.string.cancel_notification))
                        }
                    }
                }
            }
        }
    }
}


fun notificationCompatBigText(context: Context) {
    var builder = NotificationCompat.Builder(context, "channelId")
        .setSmallIcon(R.drawable.baseline_notifications_24)
        .setContentTitle("Notification Title")
        .setContentText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
        .setStyle(
            NotificationCompat.BigTextStyle()
                .bigText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.")
        )
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
}

private fun createNotificationChannel(context: Context) {
    // Sadece API 26+ için NotificationChannel oluşturun
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Spor Bildirimleri"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("channelId", name, importance)

        // Sisteme kanalı (channel) kaydedin
        val notificationManager: NotificationManager =
            getSystemService(context, NotificationManager::class.java) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
