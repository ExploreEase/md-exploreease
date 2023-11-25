package com.ch2ps418.travelapp.data.remote.firebase.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.ch2ps418.travelapp.R
import com.ch2ps418.travelapp.presentation.ui.home.HomeActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelId = "notifiaction_channel"
const val channelName = "com.ch2ps418.travelapp"

class MyFirebaseMessagingService : FirebaseMessagingService() {

	// Override onNewToken to get new token
	override fun onNewToken(token: String) {
		super.onNewToken(token)
		// Handle the new FCM token
		Log.d("FCMTOKEN", token)
	}

	// Override onMessageReceived() method to extract the title and body from the message passed in FCM
	override fun onMessageReceived(remoteMessage: RemoteMessage) {
		// Check if notification payload is received
		remoteMessage.notification?.let {
			// Since the notification is received directly from FCM, the title and the body can be fetched directly
			showNotification(it.title!!, it.body!!)
		}
	}

	// Method to get the custom Design for the display of notification.
	private fun getCustomDesign(title: String, message: String): RemoteViews {
		val remoteViews = RemoteViews(applicationContext.packageName, R.layout.notification)
		remoteViews.setTextViewText(R.id.tv_notification_title, title)
		remoteViews.setTextViewText(R.id.tv_notification_description, message)
		remoteViews.setImageViewResource(R.id.iv_app_logo_notif, R.drawable.app_logo)
		return remoteViews
	}

	// Method to display the notifications
	private fun showNotification(title: String, message: String) {
		// Pass the intent to switch to the MainActivity
		val intent = Intent(this, HomeActivity::class.java)
		// Assign channel ID
		// Here FLAG_ACTIVITY_CLEAR_TOP flag is set to clear the activities present in the activity stack,
		// on the top of the Activity that is to be launched
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
		// Pass the intent to PendingIntent to start the next Activity
		val pendingIntent = PendingIntent.getActivity(
			this,
			0,
			intent,
			PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
		)

		// Create a Builder object using NotificationCompat class.
		var builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
			.setSmallIcon(R.drawable.app_logo)
			.setAutoCancel(true)
			.setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
			.setOnlyAlertOnce(true)
			.setContentIntent(pendingIntent)

		// A customized design for the notification can be set only for Android versions 4.1 and above.
		builder = builder.setContent(getCustomDesign(title, message))

		// Create an object of NotificationManager class to notify the user of events that happen in the background.
		val notificationManager = ContextCompat.getSystemService(applicationContext, NotificationManager::class.java)
		// Check if the Android Version is greater than Oreo
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
			notificationManager?.createNotificationChannel(notificationChannel)
		}
		notificationManager?.notify(0, builder.build())
	}
}