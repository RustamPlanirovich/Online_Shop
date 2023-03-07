package com.nauk0a.onlineshop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.nauk0a.onlineshop.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Отключаем системный цвет у элемнтов bottomNavView
        binding.bottomNavView.itemIconTintList = null

        //Создаем nav_host_fragment и подключаем его к bottomNavView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.findNavController().run {
            binding.bottomNavView.setupWithNavController(this)
        }

        //Радиус угла bottomNavView
        val radius = resources.getDimension(R.dimen.bottomViewCornerRadius)

        //Устанавливаем скругление углов у bottomNavView
        val shapeDrawable: MaterialShapeDrawable =
            binding.bottomNavView.background as MaterialShapeDrawable
        shapeDrawable.shapeAppearanceModel = shapeDrawable.shapeAppearanceModel
            .toBuilder()
            .setTopLeftCorner(CornerFamily.ROUNDED,radius)
            .setTopRightCorner(CornerFamily.ROUNDED,radius)
            .build()
    }
}