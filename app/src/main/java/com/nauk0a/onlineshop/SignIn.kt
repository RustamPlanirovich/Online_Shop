package com.nauk0a.onlineshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nauk0a.onlineshop.databinding.SignInActivityBinding
import com.nauk0a.onlineshop.signin.SignInFragment

class SignIn : AppCompatActivity() {

    private lateinit var binding: SignInActivityBinding
    private lateinit var signInFragment: SignInFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignInActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Создаем экземпляр SignInFragment
        signInFragment = SignInFragment()

        //Добавляем его в fragment_container
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, signInFragment)
            .addToBackStack(null)
            .commit()

    }
}