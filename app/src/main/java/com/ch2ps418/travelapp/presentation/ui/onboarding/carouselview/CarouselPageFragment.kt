package com.ch2ps418.travelapp.presentation.ui.onboarding.carouselview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ch2ps418.travelapp.databinding.FragmentCarouselPageBinding

class CarouselPageFragment(private val page: CarouselPage) : Fragment() {

    private lateinit var binding: FragmentCarouselPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout using View Binding
        binding = FragmentCarouselPageBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set data to the views using View Binding
        binding.carouselPageImage.setImageResource(page.image)
        binding.carouselPageTitle.text = page.title.toString()
        binding.carouselPageText.text = page.text.toString()
    }
}
