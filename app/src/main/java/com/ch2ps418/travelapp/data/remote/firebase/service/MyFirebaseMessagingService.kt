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
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.ch2ps418.travelapp.R
import com.ch2ps418.travelapp.data.local.datastore.DataStoreManager
import com.ch2ps418.travelapp.data.remote.firebase.model.Place
import com.ch2ps418.travelapp.presentation.ui.home.HomeActivity
import com.google.common.reflect.TypeToken
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

const val channelId = "notifiaction_channel"
const val channelName = "com.ch2ps418.travelapp"

@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {

	@Inject
	lateinit var dataStoreManager: DataStoreManager

	// Override onNewToken to get new token
	override fun onNewToken(token: String) {
		super.onNewToken(token)
		// Handle the new FCM token
		Log.d("FCMTOKEN", token)
		// Launch a coroutine to call the suspending function setDeviceToken
		CoroutineScope(Dispatchers.IO).launch {
			dataStoreManager.setDeviceToken(token)
		}
	}

	private val TAG = "MyFirebaseMsgService"

	override fun onMessageReceived(remoteMessage: RemoteMessage) {

		Log.d("FROM", "From: ${remoteMessage.from.toString()}")

		// Check if notification payload is received
		remoteMessage.notification?.let {
			showNotification(it.title!!, it.body!!)

		}

		if (remoteMessage.data.isNotEmpty()) {
			Log.d("PAYLOAD", "Message data payload: ${remoteMessage.data}")

			val tenNearestPlaceJson = remoteMessage.data["tenNearestPlace"]
			val tenNearestPlaces = Gson().fromJson<List<Place>>(
				tenNearestPlaceJson,
				object : TypeToken<List<Place>>() {}.type
			)

			val payload = remoteMessage.data["notification"]

			if (payload != null) {
				try {
					// Parse the JSON string to a JSONObject
					val notificationObject = JSONObject(payload)

					// Access the "title" field from the JSONObject
					val title = notificationObject.getString("title")

					Log.d("TITLE", "Message title: $title")
				} catch (e: JSONException) {
					Log.e("TITLE", "Error parsing JSON", e)
				}
			} else {
				Log.d("NOTIFICATION", "Notification payload is null")
			}

			// Send a broadcast to notify the UI
			val intent = Intent("Nearest Action")
			intent.putExtra("tenNearestPlaces", ArrayList(tenNearestPlaces))
			LocalBroadcastManager.getInstance(this).sendBroadcast(intent)

			// Send a broadcast to notify the UI
			val topIntent = Intent("Top Action")
			topIntent.putExtra("topPlaces", ArrayList(tenNearestPlaces))
			LocalBroadcastManager.getInstance(this).sendBroadcast(topIntent)


			// Send a broadcast to notify the UI
			val searchIntent = Intent("Search Action")
			searchIntent.putExtra("searchPlaces", ArrayList(tenNearestPlaces))
			LocalBroadcastManager.getInstance(this).sendBroadcast(searchIntent)

			// Send a broadcast to notify the UI
			val categoryIntent = Intent("Category Action")
			categoryIntent.putExtra("categoryPlaces", ArrayList(tenNearestPlaces))
			LocalBroadcastManager.getInstance(this).sendBroadcast(categoryIntent)
		} else {
			Log.d("DATA", "EMPTY")

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
		var builder: NotificationCompat.Builder =
			NotificationCompat.Builder(applicationContext, channelId)
				.setSmallIcon(R.drawable.app_logo)
				.setAutoCancel(true)
				.setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
				.setOnlyAlertOnce(true)
				.setContentIntent(pendingIntent)

		// A customized design for the notification can be set only for Android versions 4.1 and above.
		builder = builder.setContent(getCustomDesign(title, message))

		// Create an object of NotificationManager class to notify the user of events that happen in the background.
		val notificationManager =
			ContextCompat.getSystemService(applicationContext, NotificationManager::class.java)
		// Check if the Android Version is greater than Oreo
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			val notificationChannel =
				NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
			notificationManager?.createNotificationChannel(notificationChannel)
		}
		notificationManager?.notify(0, builder.build())
	}
}