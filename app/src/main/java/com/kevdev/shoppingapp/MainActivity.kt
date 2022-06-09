package com.kevdev.shoppingapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.kevdev.shoppingapp.data.DataHelper
import com.kevdev.shoppingapp.data.SharePreferencesHelper
import com.kevdev.shoppingapp.databinding.ActivityMainBinding
import com.kevdev.shoppingapp.view.activities.PrincipalActivity
import com.kevdev.shoppingapp.view.activities.RegisterActivity
import com.kevdev.shoppingapp.viewModel.UserViewModel

class MainActivity : AppCompatActivity() {
     private val model: UserViewModel by viewModels()

     private lateinit var binding: ActivityMainBinding
     override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
          binding = ActivityMainBinding.inflate(layoutInflater)
          setContentView(binding.root)

          setup()

          model.loginResult()
               .observe(this) {
                    if (it != null) {
                         SharePreferencesHelper(this).saveValue(
                              DataHelper.Login.LOGIN_RESULT,
                              Gson().toJson(it)
                         )
                         startActivity(
                              Intent(
                                   this,
                                   PrincipalActivity::class.java
                              )
                         )
                         finish()
                    }
               }
     }

     private fun setup() {
          binding.buttonLogin.setOnClickListener {
               login()
          }
          binding.buttonCreateAccount.setOnClickListener {
               startActivity(
                    Intent(
                         this,
                         RegisterActivity::class.java
                    )
               )
          }
     }

     private fun login() {
          if (binding.email.text.isNotEmpty() && binding.password.text.isNotEmpty()) {
               model.login(
                    binding.email.text.toString(),
                    binding.password.text.toString()
               )
          }
     }
}