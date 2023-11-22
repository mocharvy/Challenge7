package com.programmer.challenge7_ma.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.programmer.challenge7_ma.LoginActivity
import com.programmer.challenge7_ma.User
import com.programmer.challenge7_ma.databinding.FragmentProfilBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfilBinding
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private val usersRef = database.getReference("users")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfilBinding.inflate(inflater, container, false)

        // Mendapatkan ID pengguna saat ini dari Firebase Authentication
        val userId = auth.currentUser?.uid

        // Memeriksa apakah pengguna masuk atau tidak
        userId?.let { uid ->

            // Mendapatkan data pengguna dari Firebase Database
            usersRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        // Mengambil data pengguna dari snapshot
                        val user = snapshot.getValue(User::class.java)
                        user?.let {
                            // Menetapkan nilai pada tampilan jika diperlukan
                            binding.etUsernameValue.text = it.username
                            binding.tvEmailValue.text = it.email
                            binding.tvMobileValue.text = it.phone
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Menangani kesalahan yang mungkin terjadi
                }
            })
        }

        // Mengatur fungsi logout
        binding.logoutButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }

        return binding.root
    }
}