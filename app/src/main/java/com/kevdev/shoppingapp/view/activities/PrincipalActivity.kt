package com.kevdev.shoppingapp.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.navigation.NavigationBarView
import com.kevdev.shoppingapp.R
import com.kevdev.shoppingapp.databinding.ActivityPrincipalBinding
import com.kevdev.shoppingapp.view.fragment.CarFragment
import com.kevdev.shoppingapp.view.fragment.FavoritesFragment
import com.kevdev.shoppingapp.view.fragment.HomeFragment
import com.kevdev.shoppingapp.view.fragment.MoreFragment


class PrincipalActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

     private lateinit var binding: ActivityPrincipalBinding
     override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
          binding = ActivityPrincipalBinding.inflate(layoutInflater)
          setContentView(binding.root)


          /*val badge = binding.bottomNavigation.getOrCreateBadge(R.id.car_page)
          badge.isVisible = true
          // An icon only badge will be displayed unless a number is set:
          badge.number = 5
           */

          setup()
     }

     private fun setup() {
          val fragmentManager = supportFragmentManager
          val fragmentTransaction = fragmentManager.beginTransaction()
          fragmentTransaction.add(R.id.fragment_container, HomeFragment())
          fragmentTransaction.commit()

          binding.bottomNavigation.setOnItemSelectedListener(this)
     }

     private fun changeFragment(fragment: Fragment) {
          val fragmentManager = supportFragmentManager
          fragmentManager.commit {
               setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
               )
          }

          val fragmentTransaction = fragmentManager.beginTransaction()
          fragmentTransaction.apply {
               replace(R.id.fragment_container, fragment)
               commit()
          }
     }

     override fun onNavigationItemSelected(item: MenuItem): Boolean {
          when (item.itemId) {
               R.id.home_page -> {
                    changeFragment(HomeFragment())
               }
               R.id.favorites_page -> {
                    changeFragment(FavoritesFragment())
               }
               R.id.car_page -> {
                    changeFragment(CarFragment())
               }
               R.id.more_page -> {
                    changeFragment(MoreFragment())
               }
          }
          return true
     }
}