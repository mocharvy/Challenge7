package com.programmer.challenge7_ma.adapter

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.programmer.challenge7_ma.databinding.CartItemBinding
import com.programmer.challenge7_ma.item.CartItem
import com.programmer.challenge7_ma.viewmodel.CartViewModel

class CartAdapter(private val viewModel: CartViewModel, private val onItemClick: (CartItem) -> Unit) :
    ListAdapter<CartItem, CartAdapter.CartViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val currentItem = getItem(position)

        holder.bind(currentItem)

        holder.binding.btnPlus.setOnClickListener {
            currentItem.quantity++
            updateCartItem(currentItem)
            currentItem.totalPrice = currentItem.calculateTotalPrice()
            notifyItemChanged(holder.bindingAdapterPosition)
        }

        holder.binding.btnMinus.setOnClickListener {
            currentItem.quantity--
            currentItem.totalPrice = currentItem.calculateTotalPrice()
            if (currentItem.quantity < 1) {
                showDeleteConfirmationDialog(holder)
            } else {
                updateCartItem(currentItem)
                notifyItemChanged(holder.bindingAdapterPosition)
            }
        }

        holder.binding.ivDelete.setOnClickListener {
            showDeleteConfirmationDialog(holder)
        }

        holder.binding.tvNote.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                holder.binding.tvNote.clearFocus()
                currentItem.note = holder.binding.tvNote.text.toString().trim()
                updateCartItem(currentItem)
                Log.d("TAG", "XXXXXXXXXXXXX")
                true
            }
            false
        }
    }

    fun calculateTotalPrice(): Int {
        var totalPrice = 0
        currentList.forEach { cartItem ->
            totalPrice += cartItem.totalPrice
        }

        return totalPrice
    }
    private fun updateCartItem(cartItem: CartItem) {
        viewModel.updateCartItem(cartItem)
    }

    inner class CartViewHolder(val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cartItem: CartItem) {
            Glide.with(itemView.context)
                .load(cartItem.imageResourceId)
                .into(binding.ivFood)
            binding.tvDesc.text = cartItem.foodName
            binding.tvPrice.text = "Rp. ${cartItem.totalPrice}"
            binding.tvNumber.text = cartItem.quantity.toString()
            binding.tvNote.setText(cartItem.note)
        }
    }

    private fun showDeleteConfirmationDialog(holder: CartViewHolder) {
        val position = holder.bindingAdapterPosition
        if (position != RecyclerView.NO_POSITION) {
            val cartItem = getItem(position)

            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Delete Item")
                .setMessage("Apakah yakin akan menghapus Item ini?")
                .setPositiveButton("Delete") { _, _ ->
                    onItemClick(cartItem)
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CartItem>() {
            override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
                return oldItem.foodName == newItem.foodName
            }

            override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
