package com.nauk0a.onlineshop.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.nauk0a.onlineshop.App
import com.nauk0a.onlineshop.MainActivity
import com.nauk0a.onlineshop.R
import com.nauk0a.onlineshop.databinding.LogInFragmentBinding
import javax.inject.Inject

class LogInFragment : Fragment() {

    private lateinit var binding: LogInFragmentBinding

    //Инжектируем viewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<LogInViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //При присоединении фрагмента к контейнеру инициализируем инъекцию
        (requireActivity().application as App).appComponent.logInComponent()
            .create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        /** Создаем объект для доступа к привязке к представлению LogInFragmentBinding,
        * которое будет использоваться во фрагменте */
        binding = LogInFragmentBinding.inflate(inflater, container, false)
        // Возвращаем корневое представление привязки
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logInButton.setOnClickListener {
            /**Делаем запрос в базу данных для проверки существования
            * введенного пользователя */
            viewModel.isUserExists(
                binding.firstNameEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
            viewModel.userExists.observe(viewLifecycleOwner) { user ->
                //Возвращается ответ и если он не null, то переходим в MainActivity
                if (user != null) {
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                } else {
                    //если null то показываем сообщение с ошибкой
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.no_user),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }
    }
}