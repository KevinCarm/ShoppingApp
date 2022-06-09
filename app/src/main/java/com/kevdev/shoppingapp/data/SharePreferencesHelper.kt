package com.kevdev.shoppingapp.data

import android.content.Context


class SharePreferencesHelper(private val context: Context) {
     private val name = "com.keep.shoppingapp.data.SharePreferencesHelper"

     fun saveValue(key: String, value: String) {
          val sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)
          sharedPreferences.edit()
               .putString(key, value)
               .apply()
     }

     fun getValue(key: String): String? {
          val sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)
          return sharedPreferences.getString(key, null)
     }
}
