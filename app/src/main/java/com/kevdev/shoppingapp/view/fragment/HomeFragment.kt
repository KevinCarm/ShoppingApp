package com.kevdev.shoppingapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.Gson
import com.kevdev.shoppingapp.R
import com.kevdev.shoppingapp.data.DataHelper
import com.kevdev.shoppingapp.data.SharePreferencesHelper
import com.kevdev.shoppingapp.data.model.User

class HomeFragment : Fragment() {

     private lateinit var currentUser: User

     override fun onCreateView(
          inflater: LayoutInflater, container: ViewGroup?,
          savedInstanceState: Bundle?
     ): View? {
          val json = SharePreferencesHelper(requireContext())
               .getValue(DataHelper.Login.LOGIN_RESULT)
          currentUser = Gson().fromJson(json, User::class.java)
          Toast.makeText(requireContext(), currentUser.toString(), Toast.LENGTH_SHORT).show()

          return inflater.inflate(R.layout.fragment_home, container, false)
     }


}