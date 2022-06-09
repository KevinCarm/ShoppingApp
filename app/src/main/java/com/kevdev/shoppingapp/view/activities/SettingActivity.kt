package com.kevdev.shoppingapp.view.activities

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.kevdev.shoppingapp.R
import com.kevdev.shoppingapp.data.DataHelper
import com.kevdev.shoppingapp.data.SharePreferencesHelper
import com.kevdev.shoppingapp.data.model.User
import com.kevdev.shoppingapp.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {
     private lateinit var binding: ActivitySettingBinding
     private lateinit var currentUser: User

     override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
          binding = ActivitySettingBinding.inflate(layoutInflater)
          setContentView(binding.root)

          setup()
     }

     private fun setup() {
          //Getting current user
          val json = SharePreferencesHelper(this)
               .getValue(DataHelper.Login.LOGIN_RESULT)
          currentUser = Gson().fromJson(json, User::class.java)
          with(currentUser) {
               with(binding) {
                    userName.text = name
                    userEmail.text = email
                    userPhone.text = "466 139 9194"
                    if (image == "")
                         imageUser.setImageResource(R.drawable.icon)
                    else
                         imageUser.setImageURI(Uri.parse(image))
               }
          }
     }
}