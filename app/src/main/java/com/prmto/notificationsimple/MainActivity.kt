package com.prmto.notificationsimple

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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
                        .setContentIntent(createPendingIntent(context))

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
                                1, notificationCompatBigText(builder)
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

    private fun createNotificationChannel(context: Context) {
        // Sadece API 26+ için NotificationChannel oluşturun
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Spor Bildirimleri"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("channelId", name, importance)

            // Sisteme kanalı (channel) kaydedin
            val notificationManager: NotificationManager =
                context.getSystemService(NotificationManager::class.java) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        return PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun notificationCompatBigText(builder: NotificationCompat.Builder): Notification {
        return builder
            .setContentText("Nba finallerinde Denver Nuggets ve Miami Heat karşılaşacak. Denver'de dikkat çeken oyuncu Nikola Jokic")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Nba finallerinde Denver Nuggets ve Miami Heat karşılaşacak. Denver'de dikkat çeken oyuncu Nikola Jokic, Miami tarafında dikkat çeken oyuncu Jimmy Butler")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }
}


