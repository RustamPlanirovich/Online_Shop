package com.nauk0a.onlineshop

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.nauk0a.onlineshop.databinding.SignInBinding

class SignInFragment : Fragment() {

    private lateinit var binding: SignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = SignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val fullText = getString(R.string.already_have_an_account)
        val login = getString(R.string.log_in)
        val spannableString = SpannableString(fullText)

        val loginClickable = object : ClickableSpan() {
            override fun onClick(p0: View) {
                childFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, LogInFragment())
                    .addToBackStack(null)
                    .commit()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
                ds.color = Color.parseColor("#FF3700B3")
            }
        }

        spannableString.setSpan(
            loginClickable,
            fullText.indexOf(login),
            fullText.indexOf(login) + login.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.haveAccountText.run {
            text = spannableString
            movementMethod = LinkMovementMethod.getInstance()
            highlightColor = Color.TRANSPARENT
        }



        binding.signInButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            if (email.isEmpty()) {
                Toast.makeText(requireContext(), "Поле не может быть пустым", Toast.LENGTH_SHORT).show()
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(requireContext(), "Введенный текст не является email", Toast.LENGTH_SHORT).show()
            } else {
                // Введенный текст соответствует формату email
            }
        }
    }

}