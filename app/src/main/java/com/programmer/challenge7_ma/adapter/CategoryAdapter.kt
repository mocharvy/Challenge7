package com.programmer.challenge7_ma.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.programmer.challenge7_ma.databinding.ItemFoodBinding
import com.programmer.challenge7_ma.menu.DataCategory

class CategoryAdapter(
    private val onItemClick: (DataCategory) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private val categoryItems: ArrayList<DataCategory> = arrayListOf()

    // Fungsi untuk mengatur daftar item kategori
    fun setCategoryItems(menuItems: List<DataCategory>) {
        categoryItems.clear()
        categoryItems.addAll(menuItems)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // Fungsi untuk mengikat data ke tampilan
        fun bind(categoryItem: DataCategory) {
            // Menggunakan Glide untuk memuat gambar dari URL ke ImageView
            Glide.with(itemView.context)
                .load(categoryItem.imageUrl)
                .into(binding.ivCategoryIcon)

            // Menetapkan nama kategori ke TextView
            binding.tvCategoryName.text = categoryItem.nama

            // Menambahkan onClickListener untuk item kategori
            itemView.setOnClickListener {
                onItemClick(categoryItem)
            }
        }
    }

    // Membuat ViewHolder baru
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // Mengikat data ke ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categoryItems[position])
    }

    // Mendapatkan jumlah item kategori
    override fun getItemCount(): Int {
        return categoryItems.size
    }
}
