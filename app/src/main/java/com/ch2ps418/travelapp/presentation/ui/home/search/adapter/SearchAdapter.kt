package com.ch2ps418.travelapp.presentation.ui.home.search.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ch2ps418.travelapp.data.remote.firebase.model.Place
import com.ch2ps418.travelapp.databinding.ItemPlaceHorizontalBinding
import com.ch2ps418.travelapp.presentation.ui.home.home.detail.DetailActivity
import java.text.NumberFormat
import java.util.Locale

class SearchAdapter(private val data: List<Place>) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private lateinit var contextAdapter: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPlaceHorizontalBinding.inflate(inflater, parent, false)
        contextAdapter = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], contextAdapter)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(private val binding: ItemPlaceHorizontalBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(data: Place, context: Context) {
            binding.tvNamePlace.text = data.Place_Name
            binding.tvRatingPlace.text = data.Rating.toString()
            binding.tvItemPrice.text = formatToRupiah(data.Price)
            binding.cvPlace.setOnClickListener {
                data?.let { place ->
                    val intent = Intent(context, DetailActivity::class.java)

                    // Use a Bundle to send the entire TenNearestPlace object
                    val bundle = Bundle().apply {
                        putSerializable("placeData", place)
                    }
                    intent.putExtras(bundle)

                    context.startActivity(intent)
                }
            }

            // Use Glide for loading images if needed
            Glide.with(context).load(data.Photos).into(binding.ivImagePlace)

            // Set up click listeners or any other binding logic here
        }

        fun formatToRupiah(price: Int): String {
            val formatter: NumberFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
            return formatter.format(price)
        }

        private fun navigateToDetailActivity(context: Context) {

        }
    }
}
