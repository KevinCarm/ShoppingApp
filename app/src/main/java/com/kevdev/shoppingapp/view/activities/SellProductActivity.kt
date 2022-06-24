package com.kevdev.shoppingapp.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.kevdev.shoppingapp.data.DataHelper
import com.kevdev.shoppingapp.data.SharePreferencesHelper
import com.kevdev.shoppingapp.data.model.User
import com.kevdev.shoppingapp.databinding.*

class SellProductActivity : AppCompatActivity() {
     private lateinit var binding: ActivitySellProductBinding
     private lateinit var currentUser: User
     override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
          binding = ActivitySellProductBinding.inflate(layoutInflater)
          setContentView(binding.root)

          setup()
     }

     private fun setup() {
          //Getting current user
          val json = SharePreferencesHelper(applicationContext).getValue(DataHelper.Login.LOGIN_RESULT)
          currentUser = Gson().fromJson(json, User::class.java)

          //Toolbar setup
          val mToolbar: androidx.appcompat.widget.Toolbar =
               findViewById(com.kevdev.shoppingapp.R.id.toolbar)
          mToolbar.title = "Sell a product !"
          mToolbar.setNavigationIcon(com.kevdev.shoppingapp.R.drawable.ic_back)

          mToolbar.setNavigationOnClickListener {
               finish()
          }

          //OnClickListeners
          binding.buttonSave.setOnClickListener {

          }
     }
}