package com.ch2ps418.travelapp.presentation.ui.home.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ch2ps418.travelapp.databinding.FragmentLoginBinding
import com.ch2ps418.travelapp.databinding.ItemCategoryBinding


class CategoryAdapter(private val data: List<String>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private lateinit var contextAdapter: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(inflater, parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = data[position]
        holder.bindItem(category)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class CategoryViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(category: String) {
            binding.tvItemCategory.text = category
            binding.cvItemCategory.setOnClickListener{
            }
        }
    }
}