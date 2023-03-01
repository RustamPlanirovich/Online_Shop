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
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.nauk0a.onlineshop.databinding.ActivityMainBinding
import com.nauk0a.onlineshop.databinding.LogInBinding
import com.nauk0a.onlineshop.databinding.SignInBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: SignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignInBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}