package com.kevdev.shoppingapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevdev.shoppingapp.data.api.RetrofitHelper
import com.kevdev.shoppingapp.data.api.UserApi
import com.kevdev.shoppingapp.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel: ViewModel() {
    private val userApi = RetrofitHelper
        .getInstance()
        .create(UserApi::class.java)

    private val user: MutableLiveData<User> = MutableLiveData()
    private val registerProgress: MutableLiveData<Boolean> = MutableLiveData()

    fun loginResult(): LiveData<User> = user
    fun getRegisterProgress(): LiveData<Boolean> = registerProgress

    init {
         registerProgress.postValue(false)
    }


    fun login(email: String, password: String) {
        viewModelScope.launch {
            val response = userApi.login(email, password)
            if(response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    user.postValue(response.body())
                }
            }
        }
    }

    fun register(user: User?) {
        viewModelScope.launch {
            if(user != null) {
                userApi.register(user)
                registerProgress.postValue(true)
            }
        }
    }
}