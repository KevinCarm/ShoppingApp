package com.kevdev.shoppingapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kevdev.shoppingapp.R


class CarFragment : Fragment() {


     override fun onCreateView(
          inflater: LayoutInflater, container: ViewGroup?,
          savedInstanceState: Bundle?
     ): View? {
          // Inflate the layout for this fragment
          return inflater.inflate(R.layout.fragment_car, container, false)
     }

}