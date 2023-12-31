package com.ch2ps418.travelapp.presentation.ui.home.home.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.ch2ps418.travelapp.data.remote.firebase.model.Place
import com.ch2ps418.travelapp.databinding.ActivityDetailBinding
import java.text.NumberFormat
import java.util.Locale

class DetailActivity : AppCompatActivity() {

	private var _binding: ActivityDetailBinding? = null
	private val binding get() = _binding!!

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		_binding = ActivityDetailBinding.inflate(layoutInflater)
		setContentView(binding.root)

		getData()
	}

	private fun getData(){
		val extras = intent.extras
		if (extras != null) {
			val placeData = extras.getSerializable("placeData") as? Place

			// Use the TenNearestPlace object in your DetailActivity
			if (placeData != null) {
				// Access placeData.Place_Id, placeData.Place_Name, placeData.Rating, etc.

				binding.collapsingToolbar.title = placeData.Place_Name
				binding.tvNamePlace.text = placeData.Place_Name
				binding.tvCategory.text = placeData.Category
				binding.tvPrice.text = formatToRupiah(placeData.Price)
				binding.tvRating.text = placeData.Rating.toString()
				// Use Glide for loading images if needed
				Glide.with(this).load(placeData.Photos).into(binding.ivDetailPhoto)
			}
		}
	}

	fun formatToRupiah(price: Int): String {
		val formatter: NumberFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
		return formatter.format(price)
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}