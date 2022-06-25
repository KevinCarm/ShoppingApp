package com.kevdev.shoppingapp.view.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.kevdev.shoppingapp.data.DataHelper
import com.kevdev.shoppingapp.data.SharePreferencesHelper
import com.kevdev.shoppingapp.data.model.Product
import com.kevdev.shoppingapp.data.model.User
import com.kevdev.shoppingapp.databinding.*
import com.kevdev.shoppingapp.viewModel.ProductViewModel

class SellProductActivity : AppCompatActivity() {

     private lateinit var binding: ActivitySellProductBinding
     private lateinit var currentUser: User
     private var imagePath: String? = null
     private val imageCode: Int = 876
     private val model: ProductViewModel by viewModels()
     private val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
          ActivityResultContracts.StartActivityForResult()
     ) { result ->
          if (result.resultCode == Activity.RESULT_OK) {
               imagePath = result.data?.data?.toString()
               binding.productImage.setImageURI(
                    Uri.parse(imagePath)
               )
          }
     }

     override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
          binding = ActivitySellProductBinding.inflate(layoutInflater)
          setContentView(binding.root)

          setup()
     }

     private fun setup() {
          //Getting current user
          val json =
               SharePreferencesHelper(applicationContext).getValue(DataHelper.Login.LOGIN_RESULT)
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
               saveProduct()
          }
          binding.productImage.setOnClickListener {
               checkPermissions()
          }
     }

     private fun checkPermissions() {
          if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
               when {
                    ContextCompat.checkSelfPermission(
                         applicationContext,
                         Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED -> {
                         //Do something
                         selectImage()
                    }
                    shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                         Toast.makeText(
                              applicationContext,
                              "You must accept the permission",
                              Toast.LENGTH_SHORT
                         ).show()
                         requestPermissions(
                              arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                              imageCode
                         )
                    }
                    else -> {
                         requestPermissions(
                              arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                              imageCode
                         )
                    }
               }
          }
     }

     private fun selectImage() {
          val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
          resultLauncher.launch(gallery)
     }

     private fun saveProduct() {
          val name = binding.productName.text.toString()
          val description = binding.productDescription.text.toString()
          val price: Double = binding.productPrice.text.toString().toDouble()
          val discount: Float = binding.productDiscount.text.toString().toFloat()
          val quantity = binding.productQuantity.text.toString().toInt()

          val product = Product(
               currentUser.id,
               name,
               description,
               imagePath,
               price,
               discount,
               quantity
          )
          model.saveProduct(product = product)
          Toast.makeText(this, "Product saved", Toast.LENGTH_SHORT).show()
          finish()
     }

     override fun onRequestPermissionsResult(
          requestCode: Int,
          permissions: Array<out String>,
          grantResults: IntArray
     ) {
          super.onRequestPermissionsResult(requestCode, permissions, grantResults)
          when (requestCode) {
               imageCode -> {
                    if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                         selectImage()
                    }
               }
          }
     }
}