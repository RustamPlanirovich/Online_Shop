package com.nauk0a.onlineshop.profile

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.nauk0a.onlineshop.App
import com.nauk0a.onlineshop.SignIn
import com.nauk0a.onlineshop.databinding.ProfileFragmentBinding
import javax.inject.Inject

const val REQUEST_IMAGE_PICK = 2

class ProfileFragment : Fragment() {

    private lateinit var binding: ProfileFragmentBinding

    //Инжектируем viewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<ProfileViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //При присоединении фрагмента к контейнеру инициализируем инъекцию
        (requireActivity().application as App).appComponent.profileComponent()
            .create().inject(this)
        viewModel.getUri()
        viewModel.getUserName()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        /** Создаем объект для доступа к привязке к представлению ProfileFragmentBinding,
         * которое будет использоваться во фрагменте */
        binding = ProfileFragmentBinding.inflate(inflater, container, false)
        // Возвращаем корневое представление привязки
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentUser.observe(viewLifecycleOwner) { userName ->
            binding.userName.text = "${userName.firstName} ${userName.lastName}"
        }



        //При клике на backButton переходим на HomeFragment
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        //Создаем интент для перехода на SignIn активити
        binding.logoutButton.setOnClickListener {
            val intent = Intent(requireContext(), SignIn::class.java)
            startActivity(intent)
            //завершаем текущую активити
            activity?.finish()
        }

        //При клике открывем системную службу выбора фото
        binding.changePthotoButton.setOnClickListener {
            someActivityResultLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        /**При первом каждом открытии фрагмента подписываемся к viewModel.selectPhotoUri
         * которая хранит загруженную фото которую мы сохранили ранее
         * и устанавливаем ее в profilePhoto */

        viewModel.selectPhotoUri.observe(viewLifecycleOwner) { uri ->

            Glide
                .with(requireContext())
                .load(uri)
                .into(binding.profilePhoto)
        }
    }

    //Возвращаем выбранную фото и устанавливаем ее в profilePhoto
    private val someActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            viewModel.setUri(uri.toString())
            binding.profilePhoto.setImageURI(uri)
        }
}