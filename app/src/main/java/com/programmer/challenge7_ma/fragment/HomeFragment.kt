package com.programmer.challenge7_ma.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.programmer.challenge7_ma.R
import com.programmer.challenge7_ma.adapter.CategoryAdapter
import com.programmer.challenge7_ma.adapter.MenuAdapter
import com.programmer.challenge7_ma.databinding.FragmentHomeBinding
import com.programmer.challenge7_ma.viewmodel.MenuViewModel
import com.programmer.challenge7_ma.viewmodel.MenuViewModelFactory
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var menuAdapter: MenuAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var sharedPrefs: SharedPreferences
    private val PREF_NAME = "MyPrefs"
    private val IS_GRID_LAYOUT_KEY = "isGridLayout"
    private lateinit var layoutManagerGrid: GridLayoutManager
    private lateinit var layoutManagerLinear: LinearLayoutManager
    private lateinit var currentLayoutManager: RecyclerView.LayoutManager
    private lateinit var viewModel: MenuViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        sharedPrefs = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        // Inisialisasi LayoutManager untuk Grid dan Linear
        layoutManagerGrid = GridLayoutManager(requireContext(), 2)
        layoutManagerLinear = LinearLayoutManager(requireContext())

        // Inisialisasi LayoutManager saat aplikasi pertama kali dibuka
        val savedLayout = sharedPrefs.getString("layout", "grid") // "grid" adalah nilai default jika tidak ada yang tersimpan

        if (savedLayout == "grid") {
            currentLayoutManager = layoutManagerGrid
            binding.btnList.setImageResource(R.drawable.grid)
        } else {
            currentLayoutManager = layoutManagerLinear
            binding.btnList.setImageResource(R.drawable.list)
        }

        binding.rvMenuMakanan.layoutManager = currentLayoutManager


        binding.btnList.setOnClickListener {
            // Mengubah tata letak saat tombol diklik

            if (currentLayoutManager == layoutManagerGrid) {
                currentLayoutManager = layoutManagerLinear
                binding.btnList.setImageResource(
                    R.drawable.list
                )
                sharedPrefs.edit().putString("layout", "linear").apply()

            } else {
                currentLayoutManager = layoutManagerGrid
                binding.btnList.setImageResource(
                    R.drawable.grid
                )
                sharedPrefs.edit().putString("layout", "grid").apply()
            }
            binding.rvMenuMakanan.layoutManager = currentLayoutManager

        }

        binding.rvCategories.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)

        setupMenu()
        setupViewModel()
        return binding.root
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, MenuViewModelFactory()).get(MenuViewModel::class.java)
        viewModel.getListMenu().observe(viewLifecycleOwner){
            if (it != null) {
                menuAdapter.setMenuItems(it)
            }
        }
        viewModel.getCategories().observe(viewLifecycleOwner){
            if(it != null){
                categoryAdapter.setCategoryItems(it)
            }
        }
        viewModel.setListMenu()
        viewModel.setCategories()
    }

    private fun setupMenu() {
        menuAdapter = MenuAdapter{ selectedItem ->
            val bundle = Bundle()
            bundle.putString("name", selectedItem.nama)
            bundle.putInt("price", selectedItem.harga!!)
            bundle.putString("description", selectedItem.detail)
            bundle.putString("imageRes", selectedItem.imageUrl)
            bundle.putString("restaurantAddress", "Alamat Restaurant")
            bundle.putString("googleMapsUrl", selectedItem.alamatResto)

            val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            navController.navigate(R.id.detailActivity, bundle)
        }

        binding.rvMenuMakanan.adapter = menuAdapter

        categoryAdapter = CategoryAdapter {
            Toast.makeText(requireContext(),it.nama,Toast.LENGTH_SHORT).show()
        }

        binding.rvCategories.adapter = categoryAdapter
    }

    private fun saveLayoutPreference(isGrid: Boolean) {
        val editor = sharedPrefs.edit()
        editor.putBoolean(IS_GRID_LAYOUT_KEY, isGrid)
        editor.apply()
    }
}
