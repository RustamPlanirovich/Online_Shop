package com.nauk0a.onlineshop.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.nauk0a.domain.Item
import com.nauk0a.domain.models.FlashSaleModelDomain
import com.nauk0a.domain.models.LatestModelDomain
import com.nauk0a.onlineshop.*
import com.nauk0a.onlineshop.databinding.*

object ShopAdapterDelegate {

    fun homeAdapterDelegate() =
        adapterDelegateViewBinding<HorizontalItem, Item, HomeRecyclerItemBinding>({ inflater, container ->
            HomeRecyclerItemBinding.inflate(inflater, container, false)
        }) {
            val adapter = ShopHorizonatalAdapter()
            binding.homeRecyclerView.adapter = adapter
            bind {
                adapter.apply {
                    when (item.name.isEmpty()) {
                        true -> {
                            binding.name.isVisible = false
                            binding.viewAllTextView.isVisible = false
                            items = item.items
                        }
                        false -> {
                            items = item.items
                            binding.name.text = item.name
                        }
                    }
                }
            }
        }

    fun categoryAdapter() =
        adapterDelegateViewBinding<com.nauk0a.domain.models.CategoryModel, Item, CategoryItemBinding>(
            { inflater, container ->
                CategoryItemBinding.inflate(inflater, container, false)
            }) {
            bind {
                Glide.with(binding.root)
                    .load(
                        context.resources.getIdentifier(
                            item.categoryImage,
                            "drawable",
                            context.getPackageName()
                        )
                    )
                    .transform(CenterCrop())
                    .transition(withCrossFade())
                    .into(binding.categoryIcon)
                binding.categoryName.text = item.categoryName
            }
            onViewRecycled {
                Glide.with(binding.root).clear(binding.categoryIcon)
            }
        }

    fun progressCategoryDelegate() =
        adapterDelegateViewBinding<ProgressCategoryItem, Item, ProgressCategoryItemBinding>(
            { inflater, container ->
                ProgressCategoryItemBinding.inflate(inflater, container, false)
            }) {}


    @SuppressLint("SetTextI18n")
    fun latestAdapter() =
        adapterDelegateViewBinding<LatestModelDomain, Item, LatestItemBinding>({ inflater, container ->
            LatestItemBinding.inflate(inflater, container, false)
        }) {
            bind {
                binding.itemTag.text = item.category
                //Устанавливаем название последнего просмотренного товара
                binding.itemName.text = item.name
                //Устанавливаем цену последнего просмотренного товара
                binding.itemPrice.text = "$ ${item.price}"
                //Устанавливаем изображение последенего просмотренного товара
                Glide
                    .with(binding.root)
                    .load(item.image_url)
                    .transform(CenterCrop())
                    .transition(withCrossFade())
                    .into(binding.itemImageView)

                binding.itemImageView.setOnClickListener {
                    //Создаем объект LatestModel
                    val latestModel = LatestModelDomain(
                        category = item.category,
                        name = item.name,
                        price = item.price,
                        image_url = item.image_url
                    )
                    //Создаем бандл и помещаем в него объект LatestModel
                    val bundle = Bundle().apply {
                        putSerializable("latest", latestModel)
                    }
                    //переходим во фрагмент detailFragment и передаем в него bundle

                    Navigation.findNavController(binding.itemImageView)
                        .navigate(R.id.detailFragment, bundle)
                }
                onViewRecycled {
                    Glide.with(binding.root).clear(binding.itemImageView)
                }
            }
        }

    fun progressLatestDelegate() =
        adapterDelegateViewBinding<ProgressLatestAndBrandItem, Item, ProgressLatestAndBrandsItemBinding>(
            { inflater, container ->
                ProgressLatestAndBrandsItemBinding.inflate(inflater, container, false)
            }) {}

    @SuppressLint("SetTextI18n")
    fun flashSaleAdapter() =
        adapterDelegateViewBinding<FlashSaleModelDomain, Item, FlashSaleItemBinding>({ inflater, container ->
            FlashSaleItemBinding.inflate(inflater, container, false)
        }) {
            bind {

                //Устанавливаем категорию товара со скидкой
                binding.categoryTextView.text = item.category
                //Устанавливаем название товара со скидкой
                binding.nameTextView.text = item.name
                //Устанавливаем цену товара со скидкой
                binding.detailPriceTextView.text = "$ ${item.price}"
                //Устанавливаем размер скидки товара со скидкой
                binding.discountTextView.text = "${item.discount}% off"
                //Устанавливаем изображение товара со скидкой
                Glide
                    .with(binding.root)
                    .load(item.image_url)
                    .transform(CenterCrop())
                    .transition(withCrossFade())
                    .into(binding.flashImageView)

                /** При клике на элемент списка
                 * - создаем объект LatestModel с данными которые хранятся в элементе
                 * - создаем бандл и помещаем в него созданный объект LatestModel
                 * - переходим на detailFragment и передаем созданный бандл в него
                 */
                binding.flashImageView.setOnClickListener {
                    //Создаем объект FlashSaleModel
                    val flashSaleModel = FlashSaleModelDomain(
                        category = item.category,
                        name = item.name,
                        price = item.price,
                        image_url = item.image_url,
                        discount = 0
                    )
                    //Создаем бандл и помещаем в него объект FlashSaleModel
                    val bundle = Bundle().apply {
                        putSerializable("flash", flashSaleModel)
                    }
                    //переходим во фрагмент detailFragment и передаем в него bundle
                    Navigation.findNavController(binding.flashImageView)
                        .navigate(R.id.detailFragment, bundle)
                }
            }
            onViewRecycled {
                Glide.with(binding.root).clear(binding.flashImageView)
            }
        }

    fun progressFlashDelegate() =
        adapterDelegateViewBinding<ProgressFlashItem, Item, ProgressFlashItemBinding>(
            { inflater, container ->
                ProgressFlashItemBinding.inflate(inflater, container, false)
            }) {}

    @SuppressLint("SetTextI18n")
    fun brandAdapter() =
        adapterDelegateViewBinding<LatestModelDomain, Item, BrandsItemBinding>({ inflater, container ->
            BrandsItemBinding.inflate(inflater, container, false)
        }) {
            bind {
                binding.itemTag.text = item.category
                //Устанавливаем название последнего просмотренного товара
                binding.itemName.text = item.name
                //Устанавливаем цену последнего просмотренного товара
                binding.itemPrice.text = "$ ${item.price}"
                //Устанавливаем изображение последенего просмотренного товара
                Glide
                    .with(binding.root)
                    .load(item.image_url)
                    .transform(CenterCrop())
                    .transition(withCrossFade())
                    .into(binding.imageView2)

                binding.imageView2.setOnClickListener {
                    //Создаем объект LatestModel
                    val latestModel = LatestModelDomain(
                        category = item.category,
                        name = item.name,
                        price = item.price,
                        image_url = item.image_url
                    )
                    //Создаем бандл и помещаем в него объект LatestModel
                    val bundle = Bundle().apply {
                        putSerializable("latest", latestModel)
                    }
                    //переходим во фрагмент detailFragment и передаем в него bundle
                    Navigation.findNavController(binding.imageView2)
                        .navigate(R.id.detailFragment, bundle)
                }
            }

            onViewRecycled {
                Glide.with(binding.root).clear(binding.imageView2)
            }
        }


}