package com.ch2ps418.travelapp.presentation.ui.onboarding

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ch2ps418.travelapp.R
import com.ch2ps418.travelapp.databinding.ActivitySplashcreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Suppress("DEPRECATION")
class SplashcreenActivity : AppCompatActivity() {

	private var _binding: ActivitySplashcreenBinding? = null
	private val binding get() = _binding!!

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		_binding = ActivitySplashcreenBinding.inflate(layoutInflater)
		setContentView(binding.root)

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			// Create channel to show notifications.
			val channelId = getString(R.string.default_notification_channel_id)
			val channelName = getString(R.string.default_notification_channel_name)
			val notificationManager = getSystemService(NotificationManager::class.java)
			notificationManager?.createNotificationChannel(
				NotificationChannel(
					channelId,
					channelName,
					NotificationManager.IMPORTANCE_LOW,
				),
			)

			askNotificationPermission()
		}
		// Animasi zoom in untuk tv_app_name
		val zoomInAnimationName =
			ScaleAnimation(
				0f,
				1f,
				0f,
				1f,
				Animation.RELATIVE_TO_SELF,
				0.5f,
				Animation.RELATIVE_TO_SELF,
				0.5f
			)
		zoomInAnimationName.duration = 700 // Sesuaikan durasi animasi
		binding.tvAppName.startAnimation(zoomInAnimationName)
		binding.tvAppName.visibility = View.VISIBLE

		// Animasi zoom in untuk iv_app_logo
		val zoomInAnimationLogo =
			ScaleAnimation(
				0f,
				1f,
				0f,
				1f,
				Animation.RELATIVE_TO_SELF,
				0.5f,
				Animation.RELATIVE_TO_SELF,
				0.5f
			)
		zoomInAnimationLogo.duration = 700 // Sesuaikan durasi animasi
		binding.ivAppLogo.startAnimation(zoomInAnimationLogo)
		binding.ivAppLogo.visibility = View.VISIBLE

		// Menambahkan delay sebelum pindah ke OnboardingActivity (disesuaikan dengan durasi animasi)
		zoomInAnimationLogo.setAnimationListener(object : Animation.AnimationListener {
			override fun onAnimationStart(animation: Animation?) {}

			override fun onAnimationEnd(animation: Animation?) {
				Handler().postDelayed({
					val intent = Intent(this@SplashcreenActivity, OnboardingActivity::class.java)
					startActivity(intent)
					finishAffinity()
				}, 2000)
			}

			override fun onAnimationRepeat(animation: Animation?) {}
		})
	}

	private val requestPermissionLauncher = registerForActivityResult(
		ActivityResultContracts.RequestPermission(),
	) { isGranted: Boolean ->
		if (isGranted) {
			Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT)
				.show()
		} else {
			Toast.makeText(
				this,
				"FCM can't post notifications without POST_NOTIFICATIONS permission",
				Toast.LENGTH_LONG,
			).show()
		}
	}

	private fun askNotificationPermission() {
		// This is only necessary for API Level > 33 (TIRAMISU)
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
				PackageManager.PERMISSION_GRANTED
			) {
				// FCM SDK (and your app) can post notifications.
			} else {
				// Directly ask for the permission
				requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
			}
		}
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}
