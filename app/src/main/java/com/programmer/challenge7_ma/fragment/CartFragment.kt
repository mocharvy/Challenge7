package com.programmer.challenge7_ma.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.programmer.challenge7_ma.adapter.CartAdapter
import com.programmer.challenge7_ma.databinding.FragmentCartBinding
import com.programmer.challenge7_ma.viewmodel.CartViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private val viewModel: CartViewModel by viewModel()
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        setupRecyclerView()
        observeCartItems()

        binding.btnpesan.setOnClickListener {
            val navController = findNavController()
            val action = CartFragmentDirections.actionCartFragmentToConfirmOrderActivity()
            navController.navigate(action)
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(viewModel) { cartItem ->
            viewModel.deleteCartItem(cartItem)
        }

        binding.rvMenuMakanan1.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observeCartItems() {
        viewModel.allCartItems.observe(viewLifecycleOwner, Observer { cartItems ->
            cartAdapter.submitList(cartItems)
            val totalPrice = cartAdapter.calculateTotalPrice()
            binding.txtTotalPrice.text = "Total Price: Rp. $totalPrice"
        })
    }
}
