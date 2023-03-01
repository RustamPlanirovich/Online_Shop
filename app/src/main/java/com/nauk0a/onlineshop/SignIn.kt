package com.nauk0a.onlineshop

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.nauk0a.onlineshop.databinding.SignInActivityBinding
import com.nauk0a.onlineshop.databinding.SignInBinding

class SignIn : AppCompatActivity() {

    private lateinit var binding: SignInActivityBinding
    private lateinit var signInFragment: SignInFragment
    private lateinit var logInFragment: LogInFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignInActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        signInFragment = SignInFragment()
        logInFragment = LogInFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, signInFragment)
            .addToBackStack(null)
            .commit()

    }
}