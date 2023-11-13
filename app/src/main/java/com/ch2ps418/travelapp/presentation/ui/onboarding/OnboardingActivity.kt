package com.ch2ps418.travelapp.presentation.ui.onboarding

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ch2ps418.travelapp.R
import com.ch2ps418.travelapp.databinding.ActivityOnboardingBinding
import com.ch2ps418.travelapp.presentation.ui.onboarding.carouselview.CarouselPage
import com.ch2ps418.travelapp.presentation.ui.onboarding.carouselview.CarouselPager
import com.ch2ps418.travelapp.presentation.ui.onboarding.carouselview.ZoomOutPageTransformer

class OnboardingActivity : AppCompatActivity(), CarouselPager.CarouselListener {

	private lateinit var binding: ActivityOnboardingBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityOnboardingBinding.inflate(layoutInflater)
		setContentView(binding.root)

		val titles = resources.getStringArray(R.array.carousel_titles)
		val subtitles = resources.getStringArray(R.array.carousel_subtitles)
		val drawableIds = resources.obtainTypedArray(R.array.carousel_drawables)

		val carouselPages = mutableListOf<CarouselPage>()
		for (i in titles.indices) {
			val drawableId = drawableIds.getResourceId(i, 0)
			carouselPages.add(CarouselPage(drawableId, titles[i], subtitles[i]))
		}

		drawableIds.recycle()

		binding.carouselPager
			.setUpCarousel(this, carouselPages)
			.setUpPageTransformer(ZoomOutPageTransformer())
			.setUpCarouselListener(this)
	}

	override fun onCarouselFinished(skipped: Boolean) {
		val carouselStatus = if (skipped) "skipped" else "completed"
		Toast.makeText(this, "You've $carouselStatus the onboarding.", Toast.LENGTH_SHORT).show()
	}
}
