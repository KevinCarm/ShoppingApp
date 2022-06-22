package com.kevdev.shoppingapp.view.activities

import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.kevdev.shoppingapp.R
import com.kevdev.shoppingapp.data.DataHelper
import com.kevdev.shoppingapp.data.SharePreferencesHelper
import com.kevdev.shoppingapp.data.model.User
import com.kevdev.shoppingapp.databinding.ActivitySettingBinding
import com.kevdev.shoppingapp.viewModel.UserViewModel


class SettingActivity : AppCompatActivity() {
     private lateinit var binding: ActivitySettingBinding
     private lateinit var currentUser: User
     private val model: UserViewModel by viewModels()

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
                    userPhone.text = phone
                    userPostCode.text = "Post code: $postcode"
                    userCity.text = "$state - $city"
                    userStreet.text = street
                    if (image == "")
                         imageUser.setImageResource(R.drawable.icon)
                    else
                         imageUser.setImageURI(Uri.parse(image))
               }
          }

          //OnClickListeners
          binding.textPhone.setOnClickListener {
               textPhoneClick()
          }

          binding.buttonAddAddress.setOnClickListener {
               addAddressClick()
          }
     }

     private fun addAddressClick() {
          val inflater = layoutInflater
          val inflateView = inflater.inflate(R.layout.custom_view_address, null)
          val state: EditText = inflateView.findViewById(R.id.inputState)
          val street: EditText = inflateView.findViewById(R.id.inputStreet)
          val postCode: EditText = inflateView.findViewById(R.id.inputPostCode)
          val city: EditText = inflateView.findViewById(R.id.inputCity)

          state.setText(currentUser.state)
          street.setText(currentUser.street)
          postCode.setText(currentUser.postcode)
          city.setText(currentUser.city)

          val alertDialog = AlertDialog.Builder(this)
          alertDialog.setPositiveButton("Save") { _, _ ->

               currentUser.postcode = postCode.text.toString()
               currentUser.city = city.text.toString()
               currentUser.street = street.text.toString()
               currentUser.state = state.text.toString()

               model.update(currentUser.id, currentUser)

               binding.userPostCode.text = "Post code: ${postCode.text}"
               binding.userCity.text = "${state.text} - ${city.text}"
               binding.userStreet.text = street.text.toString()

               Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
          }
          alertDialog.setNegativeButton("Cancel") { _, _ ->

          }
          alertDialog.apply {
               setTitle("Edit your direction")
               setView(inflateView)
               setCancelable(true)
          }
          val dialog = alertDialog.create()
          dialog.show()
     }

     private fun textPhoneClick() {
          val inflater = layoutInflater
          val inflateView = inflater.inflate(R.layout.custom_view_phone, null)
          val inputPhone: EditText = inflateView.findViewById(R.id.inputPhone)
          val alertDialog = AlertDialog.Builder(this)

          alertDialog.setNegativeButton("Cancel") { _, _ ->

          }

          alertDialog.setPositiveButton("Save") { _, _ ->
               currentUser.phone = inputPhone.text.toString()
               model.update(currentUser.id, currentUser)
               binding.userPhone.text = inputPhone.text.toString()
               Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
          }

          alertDialog.apply {
               setTitle("Edit your phone number")
               setView(inflateView)
               setCancelable(true)
          }
          val dialog = alertDialog.create()
          dialog.show()
     }
}