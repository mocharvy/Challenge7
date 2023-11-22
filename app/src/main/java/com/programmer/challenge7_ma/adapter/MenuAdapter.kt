package com.programmer.challenge7_ma.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.programmer.challenge7_ma.databinding.MenuItemBinding
import com.programmer.challenge7_ma.menu.MenuListData

class MenuAdapter(
    private val onItemClick: (MenuListData) -> Unit // Menambahkan parameter onClick
) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    private val menuItems: ArrayList<MenuListData> = arrayListOf()

    fun setMenuItems(menuItems: List<MenuListData>){
        this.menuItems.clear()
        this.menuItems.addAll(menuItems)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(menuItem: MenuListData) {
            // Isi komponen-komponen tampilan dengan data dari objek MenuItem

            Glide.with(itemView.context)
                .load(menuItem.imageUrl)
                .into(binding.imgMakanan)

            binding.txtNamaMakanan.text = menuItem.nama
            binding.txtTotalMakanan.text = menuItem.hargaFormat

            // Menambahkan onClickListener untuk item
            itemView.setOnClickListener {
                onItemClick(menuItem)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(menuItems[position])
    }

    override fun getItemCount(): Int {
        return menuItems.size
    }
}
