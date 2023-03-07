package com.nauk0a.onlineshop.signin

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.nauk0a.domain.models.UserDomain
import com.nauk0a.onlineshop.App
import com.nauk0a.onlineshop.R
import com.nauk0a.onlineshop.databinding.SignInFragmentBinding
import com.nauk0a.onlineshop.login.LogInFragment
import javax.inject.Inject

class SignInFragment : Fragment() {

    private lateinit var binding: SignInFragmentBinding
    private lateinit var logInFragment: LogInFragment

    //Инжектируем viewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<SignInViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //При присоединении фрагмента к контейнеру инициализируем инъекцию
        (requireActivity().application as App).appComponent.signInComponent()
            .create().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Создаем экземпляр LogInFragment
        logInFragment = LogInFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        /** Создаем объект для доступа к привязке к представлению SignInFragmentBinding,
        * которое будет использоваться во фрагменте */
        binding = SignInFragmentBinding.inflate(inflater, container, false)
        // Возвращаем корневое представление привязки
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Получаем строку для отображения входа в приложение,
        * текст ссылки "Log In" и создаем SpannableString для создания ссылки на экране*/
        val fullText = getString(R.string.already_have_an_account)
        val login = getString(R.string.log_in)
        val spannableString = SpannableString(fullText)

        // Создаем объект ClickableSpan для создания ссылки на экране
        val loginClickable = object : ClickableSpan() {
            override fun onClick(p0: View) {
                goToLogInScreen()
            }

            // Метод, чтобы установить стиль ссылки
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.apply {
                    isUnderlineText = true
                    color = Color.parseColor("#FF3700B3")
                }
            }
        }

        // Устанавливаем объект ClickableSpan в SpannableString
        spannableString.setSpan(
            loginClickable,
            fullText.indexOf(login),
            fullText.indexOf(login) + login.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        /** Устанавливаем SpannableString в текстовое поле на экране и устанавливаем свойства,
        * чтобы ссылка была кликабельной */
        binding.haveAccountText.run {
            text = spannableString
            movementMethod = LinkMovementMethod.getInstance()
            highlightColor = Color.TRANSPARENT
        }

        // Добавляем обработчик нажатия на кнопку "Sign In"
        binding.signInButton.setOnClickListener {
            // Проверяем, существует ли пользователь в базе данных
            viewModel.isUserExists(binding.firstNameEditText.text.toString().trim())
            // Добавляем наблюдателя для получения результата проверки пользователя
            viewModel.userExists.observe(viewLifecycleOwner) { userExists ->
                when (userExists) {
                    true -> {
                        // Показываем сообщение, что пользователь уже существует
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.user_exists),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    false -> {
                        // Проверяем, заполнены ли все поля ввода
                        when (areFieldNotEmpty(
                            binding.firstNameEditText,
                            binding.lastNameEditText,
                            binding.emailEditText
                        )) {
                            true -> {
                                // Если поля не заполнены, показываем сообщение об ошибке
                                Toast.makeText(
                                    requireContext(),
                                    getString(R.string.not_all_fields_are_filled_in),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            false -> {
                                // Если все поля заполнены, проверяем правильность ввода электронной почты
                                if (checkEmailField(binding.emailEditText)) {
                                    /** Если электронная почта введена правильно,
                                    * добавляем нового пользователя в базу данных
                                    * и переходим на экран входа в приложение */
                                    addNewUser()
                                    goToLogInScreen()
                                } else {
                                    // Если почта введена неверно, показываем сообщение об ошибке
                                    Toast.makeText(
                                        requireContext(),
                                        getString(R.string.text_entered_is_not_email),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Функция для перехода на экран входа в приложение
    private fun goToLogInScreen() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, logInFragment)
            .addToBackStack(null)
            .commit()
    }

    // Функция для добавления нового пользователя в базу данных
    private fun addNewUser() {
        viewModel.addNewUser(
            UserDomain(
                firstName = binding.firstNameEditText.text.toString(),
                lastName = binding.lastNameEditText.text.toString(),
                email = binding.emailEditText.text.toString()
            )
        )
    }

    // Функция для проверки, заполнены ли все поля ввода EditText
    private fun areFieldNotEmpty(vararg fields: EditText) = fields.all {
        it.text.toString().trim().isEmpty()
    }

    // Функция для проверки введенного текста в EditText на соответствие формату электронной почты
    private fun checkEmailField(emailField: EditText) =
        Patterns.EMAIL_ADDRESS.matcher(emailField.text.toString().trim()).matches()


}