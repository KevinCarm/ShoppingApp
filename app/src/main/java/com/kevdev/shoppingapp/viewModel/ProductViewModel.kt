package com.kevdev.shoppingapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevdev.shoppingapp.data.api.ProductApi
import com.kevdev.shoppingapp.data.api.RetrofitHelper
import com.kevdev.shoppingapp.data.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductViewModel : ViewModel() {
     private val productApi = RetrofitHelper
          .getInstance()
          .create(ProductApi::class.java)


     fun saveProduct(product: Product?) {
          if (product != null) {
               viewModelScope.launch {
                    productApi.save(product)
               }
          }
     }
}