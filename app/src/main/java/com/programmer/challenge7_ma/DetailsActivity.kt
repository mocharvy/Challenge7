package com.programmer.challenge7_ma

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.programmer.challenge7_ma.databinding.ActivityDetailsBinding
import com.programmer.challenge7_ma.item.CartItem
import com.programmer.challenge7_ma.viewmodel.CartViewModel
import com.programmer.challenge7_ma.viewmodel.ViewModelFactory


class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var googleMapsUrl: String
    private var quantity = 0
    private var priceItem = 0
    private var totalPrice = 0
    private lateinit var viewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(application)
        )[CartViewModel::class.java]

        val bundle = intent.extras
        val name = bundle?.getString("name")
        val price = bundle?.getInt("price")
        val description = bundle?.getString("description")
        val imageRes = bundle?.getString("imageRes")
        val restaurantAddress = bundle?.getString("restaurantAddress")
        googleMapsUrl = bundle!!.getString("googleMapsUrl", "")

        if (binding.btnAddToCart.text == "Tambah Ke Keranjang - Rp.0"){
            binding.btnAddToCart.text = "Tambah Ke Keranjang - Rp. $price"
        }

        priceItem = price!!

        imageRes?.let { updateUI(name, price.toString(), description, it, restaurantAddress) }

        // Tambahkan onClickListener untuk tombol kembali
        binding.btkembali.setOnClickListener{
            finish()
        }

        // Tambahkan onClickListener untuk tombol tambah
        binding.btnPlus.setOnClickListener {
            quantity++
            updateQuantity(quantity)
        }

        // Tambahkan onClickListener untuk tombol kurang
        binding.btnMinus.setOnClickListener {
            if (quantity > 1) {
                quantity--
                updateQuantity(quantity)
            }
        }

        //Tambahkan onClickListener untuk tombol "Tambah Ke Keranjang"
        binding.btnAddToCart.setOnClickListener {
            // Ambil data makanan untuk disimpan ke dalam keranjang
            val cartItem = CartItem(
                foodName = name.toString(),
                totalPrice = totalPrice,
                price = price,
                quantity = quantity,
                imageResourceId = imageRes!! // Gunakan image sebagai Integer
            )
            if (quantity > 0){
                // Untuk menyimpan item ke dalam keranjang menggunakan CartItemDao
                viewModel.insertCartItem(cartItem)

                // Tampilkan pesan berhasil menambahkan ke keranjang
                Toast.makeText(this@DetailsActivity, "Item ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this@DetailsActivity, "Jumlah item tidak boleh 0!", Toast.LENGTH_SHORT).show()

            }


        }

        binding.txtGoogleMaps.setOnClickListener {
            // Memeriksa apakah URL Google Maps tidak kosong
            if (googleMapsUrl.isNotEmpty()) {
                // Membuka tautan Google Maps di browser
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(googleMapsUrl))
                startActivity(intent)
            }
        }
    }

    private fun updateQuantity(quantity: Int) {
        binding.txtQuantity.text = quantity.toString()
        totalPrice = quantity * priceItem
        binding.btnAddToCart.text = "Tambah Ke Keranjang - Rp. $totalPrice"

    }


    private fun updateUI(name: String?, price: String?, description: String?, imageRes: String, restaurantAddress: String?) {
        binding.apply {
            Glide.with(this@DetailsActivity)
                .load(imageRes)
                .into(imgFood)
            txtFoodName.text = name
            txtFoodPrice.text = "Rp. $price"
            txtFoodDescription.text = description
            txtLocation.text = restaurantAddress
            txtGoogleMaps.text = googleMapsUrl
        }
    }
}