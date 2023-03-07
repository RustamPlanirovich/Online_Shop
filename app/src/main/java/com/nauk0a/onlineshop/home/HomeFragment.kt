package com.nauk0a.onlineshop.home

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nauk0a.onlineshop.App
import com.nauk0a.onlineshop.R
import com.nauk0a.onlineshop.databinding.HomeFragmentBinding
import javax.inject.Inject

class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var latestAdapter: LatestAdapter
    private lateinit var flashAdapter: FlashSaleAdapter

    //Инжектируем viewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<HomeViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //При присоединении фрагмента к контейнеру инициализируем инъекцию
        (requireActivity().application as App).appComponent.homeComponent()
            .create().inject(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        /** Создаем объект для доступа к привязке к представлению HomeFragmentBinding,
         которое будет использоваться во фрагменте **/
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        // Возвращаем корневое представление привязки
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Запрашиваем лист с категориями
        viewModel.getCategoryList()
        //Запрашиваем данные из API
        viewModel.showData()

        //Создаем SpannableString для dashboardTitle
        val spannable = SpannableString(binding.dashboardTitle.text.toString())
        //Ищем начальный индекс
        val startIndex = binding.dashboardTitle.text.toString().indexOf("bata")
        //Ищем последный индекс
        val endIndex = startIndex + "bata".length
        /**Устанавливаем ForegroundColorSpan для текста который находится между
        startIndex и endIndex**/
        spannable.setSpan(
            ForegroundColorSpan(Color.BLUE),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        //Устанавливаем ранее созданный SpannableString к dashboardTitle
        binding.dashboardTitle.text = spannable

        //Инициализируем адаптер для отображения категорий
        categoryAdapter = CategoryAdapter()
        //Инициализируем адаптер для отображения последних просмотренных товаров
        latestAdapter = LatestAdapter()
        //Инициализируем адаптер для отображения товаров со скидкой
        flashAdapter = FlashSaleAdapter()

        /**Для отображения брендов не создавал отдельного адаптера,
        а переиспользовал latestAdapter, т.к в ТЗ не было явно указано
        откуда брать или как формировать данный блок **/

        //Присваиваем адаптер categoryAdapter к categoryRecyclerView
        binding.categoryRecyclerView.adapter = categoryAdapter
        //Присваиваем адаптер latestRecyclerView2 к latestAdapter
        binding.latestRecyclerView2.adapter = latestAdapter
        //Присваиваем адаптер flashRecyclerView3 к flashAdapter
        binding.flashRecyclerView3.adapter = flashAdapter
        //Присваиваем адаптер brandsRecyclerView к latestAdapter
        binding.brandsRecyclerView.adapter = latestAdapter

        //Получаем данные с категорией товаров
        viewModel.categoryList.observe(viewLifecycleOwner) { categoryList ->
            categoryAdapter.submitList(categoryList)
        }

        //Получаем данные с последними просмотренными товарами
        viewModel.latestList.observe(viewLifecycleOwner) { latest ->
            latestAdapter.submitList(latest)
        }

        //Получаем данные с товарами со скидкой
        viewModel.flashList.observe(viewLifecycleOwner) { flash ->
            flashAdapter.submitList(flash)
        }

        /** Получаем ошибки которые могут возникнуть при загрузке
         * последних просмотренных товаров и товаров со скидкой
         */
        viewModel.errorApiDownloadData.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }

        //При клике на avatar переходим в profileFragment
        binding.avatar.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

    }
}