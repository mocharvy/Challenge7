package com.programmer.challenge7_ma

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.programmer.challenge7_ma.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()

        binding.registerButton1.setOnClickListener {
            val username = binding.etUsernameValue1.text.toString().trim()
            val email = binding.etEmailValue1.text.toString().trim()
            val phone = binding.etMobileValue1.text.toString().trim()
            val password = binding.etPasswordValue1.text.toString()

            // Validasi input
            if (username.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Semua kolom harus di isi!!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Pemeriksaan apakah username sudah ada
            val usersRef = FirebaseDatabase.getInstance().getReference("users")
            usersRef.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        Toast.makeText(applicationContext, "Username sudah digunakan", Toast.LENGTH_SHORT).show()
                    } else {
                        // Pemeriksaan apakah email sudah ada
                        usersRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(emailSnapshot: DataSnapshot) {
                                if (emailSnapshot.exists()) {
                                    Toast.makeText(applicationContext, "Email sudah digunakan", Toast.LENGTH_SHORT).show()
                                } else {
                                    // Registrasi pengguna menggunakan Firebase Authentication
                                    auth.createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                // Registrasi berhasil, simpan data pengguna ke Firebase Realtime Database
                                                val user = User(auth.uid, username, email, phone)
                                                usersRef.child(auth.uid.toString()).setValue(user)
                                                Toast.makeText(applicationContext, "Registrasi berhasil", Toast.LENGTH_SHORT).show()
                                                // arahkan ke LoginActivity
                                                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                                                finish()
                                            } else {
                                                Toast.makeText(applicationContext, "Registrasi gagal: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                // Handle errors
                            }
                        })
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle errors
                }
            })
        }

        binding.registerText1.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        }
    }
}
