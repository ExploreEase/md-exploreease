package com.ch2ps418.travelapp.presentation.ui.onboarding.carouselview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.ch2ps418.travelapp.R
import com.ch2ps418.travelapp.databinding.CarouselPagerBinding
import com.google.android.material.tabs.TabLayoutMediator

class CarouselPager @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

    private val binding: CarouselPagerBinding =
        CarouselPagerBinding.inflate(LayoutInflater.from(context), this, true)

    private val zoomInAnim = AnimationUtils.loadAnimation(context, R.anim.zoom_in)
    private val carouselPages = mutableListOf<CarouselPage>()
    private var carouselListener: CarouselListener? = null

    init {
        setUpListeners()
    }

    private fun setUpListeners() = with(binding) {
        btnGetStarted.setOnClickListener {
            carouselListener?.onCarouselFinished()
        }

        btnSkip.setOnClickListener {
            carouselListener?.onCarouselFinished(true)
        }
    }

    fun setUpCarousel(fragmentActivity: FragmentActivity, carouselPages: List<CarouselPage>): CarouselPager {
        val viewPagerAdapter = ViewPagerAdapter(fragmentActivity)
        viewPagerAdapter.setPages(carouselPages)

        with(binding) {
            viewPager.adapter = viewPagerAdapter
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    if (position == carouselPages.size - 1) {
                        btnGetStarted.visibility = View.VISIBLE
                        btnGetStarted.startAnimation(zoomInAnim)
                        btnSkip.visibility = View.GONE
                    } else {
                        btnGetStarted.visibility = View.GONE
                        btnSkip.visibility = View.VISIBLE
                    }
                }
            })

            TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()
        }

        this.carouselPages.addAll(carouselPages)
        return this
    }

    fun setUpPageTransformer(pageTransformer: ViewPager2.PageTransformer): CarouselPager {
        binding.viewPager.setPageTransformer(pageTransformer)
        return this
    }

    fun setUpCarouselListener(carouselListener: CarouselListener): CarouselPager {
        this.carouselListener = carouselListener
        return this
    }

    interface CarouselListener {
        fun onCarouselFinished(skipped: Boolean = false)
    }
}
