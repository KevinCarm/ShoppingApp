package com.kevdev.shoppingapp.view.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.kevdev.shoppingapp.data.DataHelper
import com.kevdev.shoppingapp.data.SharePreferencesHelper
import com.kevdev.shoppingapp.data.model.User
import com.kevdev.shoppingapp.databinding.FragmentMoreBinding
import com.kevdev.shoppingapp.view.activities.SellProductActivity
import com.kevdev.shoppingapp.view.activities.SettingActivity


class MoreFragment : Fragment() {
     private lateinit var binding: FragmentMoreBinding
     private lateinit var currentUser: User

     override fun onCreateView(
          inflater: LayoutInflater, container: ViewGroup?,
          savedInstanceState: Bundle?
     ): View {
          binding = FragmentMoreBinding.inflate(layoutInflater, container, false)
          setup()
          return binding.root
     }

     @SuppressLint("SetTextI18n")
     private fun setup() {
          //Getting current user data
          val json = SharePreferencesHelper(requireContext())
               .getValue(DataHelper.Login.LOGIN_RESULT)
          currentUser = Gson().fromJson(json, User::class.java)

          binding.userName.text = "Hi!, ${currentUser.name}"

          binding.imageUser.setImageURI(
               Uri.parse(currentUser.image)
          )

          //OnClickListeners
          binding.moreOptionSettings.setOnClickListener {
               openSettings()
          }
          binding.optionSell.setOnClickListener {
               requireActivity()
                    .startActivity(
                         Intent(
                              requireContext(),
                              SellProductActivity::class.java
                         )
                    )
          }
     }

     private fun openSettings() {
          requireActivity()
               .startActivity(
                    Intent(
                         requireContext(),
                         SettingActivity::class.java
                    )
               )
     }

}