package com.kevdev.shoppingapp.view.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kevdev.shoppingapp.data.model.User
import com.kevdev.shoppingapp.databinding.ActivityRegisterBinding

import com.kevdev.shoppingapp.viewModel.UserViewModel

class RegisterActivity : AppCompatActivity() {
     private val model: UserViewModel by viewModels()
     private lateinit var binding: ActivityRegisterBinding
     private var isReadyToRegister: Boolean = false

     override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
          binding = ActivityRegisterBinding.inflate(layoutInflater)
          setContentView(binding.root)

          setup()
     }

     private fun setup() {

          model.getRegisterProgress().observe(this) {
               if (it)
                    binding.progress.visibility = View.INVISIBLE
          }

          binding.buttonCreateAccount.setOnClickListener {
               if (checkInput()) {
                    binding.progress.visibility = View.VISIBLE
                    model.register(
                         User(
                              binding.name.text.toString(),
                              binding.email.text.toString(),
                              binding.password.text.toString(),
                              null,
                              null,
                              null,
                              null,
                              null,
                              null
                         )
                    )
               } else
                    Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
          }
     }

     private fun checkInput(): Boolean {
          if (binding.name.text.isEmpty()) {
               binding.name.error = "Name is required"
               isReadyToRegister = false
          } else {
               binding.name.error = null
               isReadyToRegister = true
          }
          if (binding.email.text.isEmpty()) {
               binding.email.error = "Email is required"
               isReadyToRegister = false
          } else {
               binding.email.error = null
               isReadyToRegister = true
          }
          if (binding.password.text.isEmpty()) {
               binding.password.error = "Password is required"
               isReadyToRegister = false
          } else {
               binding.password.error = null
               isReadyToRegister = true
          }
          return isReadyToRegister
     }
}