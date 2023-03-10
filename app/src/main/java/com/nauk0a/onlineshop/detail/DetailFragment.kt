package com.nauk0a.onlineshop.detail

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.DimenRes
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.nauk0a.onlineshop.R
import com.nauk0a.onlineshop.databinding.DetailFragmentBinding
import com.nauk0a.domain.models.FlashSaleModelDomain
import com.nauk0a.domain.models.ImagesModel
import com.nauk0a.domain.models.LatestModelDomain
import kotlin.math.abs


class DetailFragment : Fragment() {

    private lateinit var binding: DetailFragmentBinding
    private var price = 22.50
    private var commonPrice = 22.50

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DetailFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**Устанавливаем стартовый ценник в цену товара и центу товара на кнопке
         * добавления в корзину
         */

        setPrice(price, commonPrice)

        //Переходим обратно в HomeFragment при клике на goBack
        binding.goBack.setOnClickListener {
            findNavController().popBackStack()
        }

        //Отображаем сообщение при клике на favoriteButton
        binding.favoriteButton.setOnClickListener {
            Toast.makeText(requireContext(), "Товар добавлен в избранное", Toast.LENGTH_SHORT)
                .show()
        }

        //Отображение сообщения при клике на shareButton
        binding.shareButton.setOnClickListener {
            Toast.makeText(requireContext(), "Поделиться ссылкой на товар", Toast.LENGTH_SHORT)
                .show()
        }

        /** Вычитаем стоимость товара из стоимости которая находится на кнопке добавления
         * в корзину, только при том условии если цена меньше цены которая находитсья на кнопке
         * добавления в корзину
         */
        binding.deleteButton.setOnClickListener {
            if (price != commonPrice) {
                commonPrice -= price
                setPrice(price, commonPrice)
            }
        }

        //Прибавляем цену к цене которая находиться на кнопке добавления в корзину
        binding.addButton.setOnClickListener {
            commonPrice += price
            setPrice(price, commonPrice)
        }

        //Лист для добавления изображений товара которые будут отображаться в карусели
        val data = mutableListOf<ImagesModel>()

        /** Если аргументы приходят с ключом "latest" то отрабатываем метод
         * getLatestAdapterArguments, если с ключом "flash" отрабатываем метод
         * getFlashSaleAdapterArguments
         */

        if (arguments?.containsKey("latest") == true) {
            getLatestAdapterArguments(data)
        } else if (arguments?.containsKey("flash") == true) {
            getFlashSaleAdapterArguments(data)
        }

        //Создаем адаптер для карусели в которой отображаются дополнительные фото товара
        val adapter = ViewPagerAdapter(data)

        //Привязываем адаптер к карусели detailFragmentViewPager
        binding.detailFragmentViewPager.adapter = adapter

        /**Устанавливаем количество страниц которые должны быть сохранены по обе стороны
         * текущей видимой страницы
         */
        binding.detailFragmentViewPager.offscreenPageLimit = 1

        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx =
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.25f * abs(position))
        }
        binding.detailFragmentViewPager.setPageTransformer(pageTransformer)

        //Создаем декотратор для элемента карусели
        val itemDecoration = HorizontalMarginItemDecoration(
            requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )
        //Присваиваем созданный декоратор к detailFragmentViewPager
        binding.detailFragmentViewPager.addItemDecoration(itemDecoration)
        binding.detailFragmentViewPager.setCurrentItem(1, false)
    }

    // Метод в котором получаем данные из latest адаптера
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun getLatestAdapterArguments(
        data: MutableList<ImagesModel>,
    ) {
        //Получаем аргументы по ключу latest
        val getArguments = arguments?.getSerializable("latest") as LatestModelDomain
        //Если аргументы не пустые

        //Устанавливаем цену товара
        price = getArguments.price.toDouble()
        //Устанавливаем цену товара на кнопке добавления в корзину
        commonPrice = getArguments.price.toDouble()
        //Устанавливаем название товара
        binding.productName.text = getArguments.name
        //Устанавливаем изображение товара
        Glide
            .with(binding.root)
            .load(getArguments.image_url)
            .into(binding.productPhoto)
        //Обновляем цены на загруженные
        setPrice(price, commonPrice)
        //Добавляем фото товара три раза для демонстрации работы карусели
        repeat(3) {
            data.add(ImagesModel(getArguments.image_url))
        }


    }

    // Метод в котором получаем данные из flash адаптера
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun getFlashSaleAdapterArguments(
        data: MutableList<ImagesModel>,
    ) {
        //Получаем аргументы по ключу flash
        val getArguments = arguments?.getSerializable("flash") as FlashSaleModelDomain

        //Если аргументы не пустые
        if (getArguments != null) {
            //Устанавливаем цену товара
            price = getArguments.price.toDouble()
            //Устанавливаем цену товара на кнопке добавления в корзину
            commonPrice = getArguments.price.toDouble()
            //Устанавливаем название товара
            binding.productName.text = getArguments.name
            //Устанавливаем изображение товара
            Glide
                .with(requireActivity())
                .load(getArguments.image_url)
                .into(binding.productPhoto)
            //Обновляем цены на загруженные
            setPrice(price, commonPrice)
            //Добавляем фото товара три раза для демонстрации работы карусели
            repeat(3) {
                data.add(ImagesModel(getArguments.image_url))
            }

        }
    }

    /** Метод для обновления цен
     * - основная цена
     * - цена на кнопке добавления товара в корзину
     */
    @SuppressLint("SetTextI18n")
    private fun setPrice(price: Double, commonPrice: Double) {
        //Обновляем цену на основном экране
        binding.detailPriceTextView.text = "$ $price"
        //Обновляем цену на кнопке добавления товара в корзину
        binding.commonPrice.text = "#$commonPrice"
    }

}

//Плохо понимаю работу данного метода - нашел на просторах stack overflow
class HorizontalMarginItemDecoration(context: Context, @DimenRes horizontalMarginInDp: Int) :
    RecyclerView.ItemDecoration() {

    // Преобразуйте значение dp в пиксели для расчета
    private val horizontalMarginInPx: Int =
        context.resources.getDimension(horizontalMarginInDp).toInt()

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State,
    ) {
        // Установите левое и правое поля представления элемента
        outRect.right = horizontalMarginInPx
        outRect.left = horizontalMarginInPx
    }

}