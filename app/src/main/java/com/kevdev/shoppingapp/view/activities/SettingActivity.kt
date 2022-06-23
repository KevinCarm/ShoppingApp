package com.kevdev.shoppingapp.view.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
     private var photoUri: String? = null
     private val photoCode = 999
     private val model: UserViewModel by viewModels()
     private val resultLauncher: ActivityResultLauncher<Intent> =
          registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
               photoUri = result.data?.data.toString()
               if (result.resultCode == Activity.RESULT_OK) {
                    //Set image to image view
                    val source: ImageDecoder.Source =
                         result.data?.data?.let {
                              ImageDecoder.createSource(
                                   this.contentResolver,
                                   it
                              )
                         }!!
                    val bitmap = ImageDecoder.decodeBitmap(source)
                    binding.userImage.setImageBitmap(bitmap)

                    //Updating user info
                    currentUser.image = photoUri
                    model.update(currentUser.id, currentUser)

                    //Updating local current user
                    val json = Gson().toJson(currentUser)
                    SharePreferencesHelper(applicationContext)
                         .saveValue(DataHelper.Login.LOGIN_RESULT, json)
               }
          }

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
                    if (image == "" || image == null)
                         userImage.setImageResource(R.drawable.icon)
                    else
                         userImage.setImageURI(Uri.parse(image))
               }
          }

          //OnClickListeners
          binding.textPhone.setOnClickListener {
               addTextPhoneClick()
          }

          binding.buttonAddAddress.setOnClickListener {
               addAddressClick()
          }

          binding.userImage.setOnClickListener {
               selectImageClick()
          }

          binding.textEmail.setOnClickListener {
               addEmailClick()
          }

          binding.textName.setOnClickListener {
               addNameClick()
          }
     }

     private fun addNameClick() {
          val inflater = layoutInflater
          val inflateView = inflater.inflate(R.layout.custom_view, null)
          val inputName: EditText = inflateView.findViewById(R.id.input)
          inputName.hint = "Your name"
          inputName.inputType = InputType.TYPE_CLASS_TEXT
          inputName.setText(currentUser.name)

          val alertDialog = AlertDialog.Builder(this)

          alertDialog.setPositiveButton("Save") { _, _ ->
               if (inputName.text.toString().isNotEmpty()) {
                    currentUser.name = inputName.text.toString()
                    model.update(currentUser.id, currentUser)
                    binding.userName.text = currentUser.name

                    val json = Gson().toJson(currentUser)
                    SharePreferencesHelper(applicationContext)
                         .saveValue(DataHelper.Login.LOGIN_RESULT, json)

                    Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
               }
          }

          alertDialog.setNegativeButton("Cancel") { _, _ -> }
          alertDialog.apply {
               setTitle("Edit your name")
               setView(inflateView)
               setCancelable(true)
          }
          val dialog = alertDialog.create()
          dialog.show()
     }


     private fun addEmailClick() {
          val inflater = layoutInflater
          val inflateView = inflater.inflate(R.layout.custom_view, null)
          val inputEmail: EditText = inflateView.findViewById(R.id.input)
          inputEmail.hint = "Your email"
          inputEmail.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

          inputEmail.setText(currentUser.email)
          val alertDialog = AlertDialog.Builder(this)

          alertDialog.setPositiveButton("Save") { _, _ ->
               if (inputEmail.text.toString().isNotEmpty()) {
                    currentUser.email = inputEmail.text.toString()
                    model.update(currentUser.id, currentUser)
                    binding.userEmail.text = currentUser.email

                    val json = Gson().toJson(currentUser)
                    SharePreferencesHelper(applicationContext)
                         .saveValue(DataHelper.Login.LOGIN_RESULT, json)

                    Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
               }
          }
          alertDialog.setNegativeButton("Cancel") { _, _ -> }
          alertDialog.apply {
               setTitle("Edit your email direction")
               setView(inflateView)
               setCancelable(true)
          }
          val dialog = alertDialog.create()
          dialog.show()
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

               val json = Gson().toJson(currentUser)
               SharePreferencesHelper(applicationContext)
                    .saveValue(DataHelper.Login.LOGIN_RESULT, json)

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

     private fun addTextPhoneClick() {
          val inflater = layoutInflater
          val inflateView = inflater.inflate(R.layout.custom_view, null)
          val inputPhone: EditText = inflateView.findViewById(R.id.input)
          inputPhone.hint = "Your phone"
          inputPhone.inputType = InputType.TYPE_CLASS_PHONE
          inputPhone.setText(currentUser.phone)
          val alertDialog = AlertDialog.Builder(this)

          alertDialog.setNegativeButton("Cancel") { _, _ ->

          }

          alertDialog.setPositiveButton("Save") { _, _ ->
               currentUser.phone = inputPhone.text.toString()
               model.update(currentUser.id, currentUser)
               binding.userPhone.text = inputPhone.text.toString()

               val json = Gson().toJson(currentUser)
               SharePreferencesHelper(applicationContext)
                    .saveValue(DataHelper.Login.LOGIN_RESULT, json)

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

     private fun selectPhoto() {
          val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
          resultLauncher.launch(gallery)
     }


     private fun selectImageClick() {
          checkPermissions()
     }

     private fun checkPermissions() {
          if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
               when {
                    ContextCompat.checkSelfPermission(
                         applicationContext,
                         Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED -> {
                         //Do something
                         selectPhoto()
                    }
                    shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                         Toast.makeText(
                              applicationContext,
                              "You must accept the permission",
                              Toast.LENGTH_SHORT
                         ).show()
                         requestPermissions(
                              arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                              photoCode
                         )
                    }
                    else -> {
                         requestPermissions(
                              arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                              photoCode
                         )
                    }
               }
          }
     }

     override fun onRequestPermissionsResult(
          requestCode: Int,
          permissions: Array<out String>,
          grantResults: IntArray
     ) {
          super.onRequestPermissionsResult(requestCode, permissions, grantResults)
          when (requestCode) {
               photoCode -> {
                    if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                         selectPhoto()
                    }
               }
          }
     }


}