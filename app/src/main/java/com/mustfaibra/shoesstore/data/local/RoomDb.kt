package com.mustfaibra.shoesstore.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mustfaibra.shoesstore.R
import com.mustfaibra.shoesstore.models.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [
        Advertisement::class,
        Manufacturer::class,
        Review::class,
        User::class,
        Product::class,
        CartItem::class,
        Order::class,
        OrderItem::class,
        OrderPayment::class,
        Notification::class,
        ProductColor::class,
        ProductSize::class,
    ],
    version = 1, exportSchema = false)
abstract class RoomDb : RoomDatabase() {

    /** A function that used to retrieve Room's related dao instance */
    abstract fun getDao(): RoomDao

    class PopulateDataClass @Inject constructor(
        private val client: Provider<RoomDb>,
        private val scope: CoroutineScope,
    ) : RoomDatabase.Callback() {
        private val description =
            "This is the description text that is supposed to be long enough to show how the UI looks, so it's not a real text.\n"
        val manufacturers = listOf(
            Manufacturer(id = 1, name = "Nike", icon = R.drawable.ic_nike),
            Manufacturer(id = 2, name = "Adidas", icon = R.drawable.adidas_48),
        )
        val advertisements = listOf(
            Advertisement(1, R.drawable.air_huarache_gold_black_ads, 1, 0),
            Advertisement(2, R.drawable.pegasus_trail_gortex_ads, 2, 0),
            Advertisement(3, R.drawable.blazer_low_black_ads, 3, 0),
        )
        val nikeProducts = listOf(
            Product(
                id = 1,
                name = "Pegasus Trail Gortex Green",
                image = R.drawable.pegasus_trail_3_gore_tex_dark_green,
                price = 149.0,
                description = description,
                manufacturerId = 1,
                basicColorName = "dark-green",
            ).also {
                it.copies = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.basicColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "lemon",
                        image = R.drawable.pegasus_trail_3_gore_tex_lemon),
                )
            },
            Product(
                id = 2,
                name = "Pegasus Trail Gortex Lemon",
                image = R.drawable.pegasus_trail_3_gore_tex_lemon,
                price = 155.0,
                description = description,
                manufacturerId = 1,
                basicColorName = "lemon",
            ).also {
                it.copies = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.basicColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "dark-green",
                        image = R.drawable.pegasus_trail_3_gore_tex_dark_green),
                )
            },
            Product(
                id = 3,
                name = "Air Huarache Gold",
                image = R.drawable.air_huarache_le_gold_black,
                price = 159.0,
                description = description,
                manufacturerId = 1,
                basicColorName = "gold",
            ).also {
                it.copies = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.basicColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "gray",
                        image = R.drawable.air_huarache_le_gray_dark),
                    ProductColor(productId = it.id,
                        colorName = "pink",
                        image = R.drawable.air_huarache_le_pink_black),
                    ProductColor(productId = it.id,
                        colorName = "red",
                        image = R.drawable.air_huarache_le_red_black),
                )
            },
            Product(
                id = 4,
                name = "Air Huarache Gray",
                image = R.drawable.air_huarache_le_gray_dark,
                price = 149.0,
                description = description,
                manufacturerId = 1,
                basicColorName = "gray",
            ).also {
                it.copies = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.basicColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "gold",
                        image = R.drawable.air_huarache_le_gold_black),
                    ProductColor(productId = it.id,
                        colorName = "pink",
                        image = R.drawable.air_huarache_le_pink_black),
                    ProductColor(productId = it.id,
                        colorName = "red",
                        image = R.drawable.air_huarache_le_red_black),
                )
            },
            Product(
                id = 5,
                name = "Air Huarache Pink",
                image = R.drawable.air_huarache_le_pink_black,
                price = 125.0,
                description = description,
                manufacturerId = 1,
                basicColorName = "pink",
            ).also {
                it.copies = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.basicColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "gray",
                        image = R.drawable.air_huarache_le_gray_dark),
                    ProductColor(productId = it.id,
                        colorName = "gold",
                        image = R.drawable.air_huarache_le_gold_black),
                    ProductColor(productId = it.id,
                        colorName = "red",
                        image = R.drawable.air_huarache_le_red_black),
                )
            },
            Product(
                id = 6,
                name = "Air Huarache Red",
                image = R.drawable.air_huarache_le_red_black,
                price = 145.0,
                description = description,
                manufacturerId = 1,
                basicColorName = "red",
            ).also {
                it.copies = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.basicColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "gray",
                        image = R.drawable.air_huarache_le_gray_dark),
                    ProductColor(productId = it.id,
                        colorName = "pink",
                        image = R.drawable.air_huarache_le_pink_black),
                    ProductColor(productId = it.id,
                        colorName = "gold",
                        image = R.drawable.air_huarache_le_gold_black),
                )
            },
            Product(
                id = 7,
                name = "Blazer Low Black",
                image = R.drawable.blazer_low_black,
                price = 120.0,
                description = description,
                manufacturerId = 1,
                basicColorName = "black",
            ).also {
                it.copies = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.basicColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "pink",
                        image = R.drawable.blazer_low_pink),
                    ProductColor(productId = it.id,
                        colorName = "lemon",
                        image = R.drawable.blazer_low_light_green),
                )
            },
            Product(
                id = 8,
                name = "Blazer Low Pink",
                image = R.drawable.blazer_low_pink,
                price = 189.99,
                description = description,
                manufacturerId = 1,
                basicColorName = "pink",
            ).also {
                it.copies = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.basicColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "black",
                        image = R.drawable.blazer_low_black),
                    ProductColor(productId = it.id,
                        colorName = "lemon",
                        image = R.drawable.blazer_low_light_green),
                )
            },
            Product(
                id = 9,
                name = "Blazer Low Light Green",
                image = R.drawable.blazer_low_light_green,
                price = 170.0,
                description = description,
                manufacturerId = 1,
                basicColorName = "lemon",
            ).also {
                it.copies = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.basicColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "pink",
                        image = R.drawable.blazer_low_pink),
                    ProductColor(productId = it.id,
                        colorName = "black",
                        image = R.drawable.blazer_low_black),
                )
            },

            )
        val adidasProducts = listOf(
            Product(
                id = 10,
                name = "Defiant Generation Green",
                image = R.drawable.defiant_generation_green,
                price = 149.0,
                description = description,
                manufacturerId = 2,
                basicColorName = "green",
            ).also {
                it.copies = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.basicColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "red",
                        image = R.drawable.defiant_generation_red),
                )
            },
            Product(
                id = 11,
                name = "Defiant Generation Red",
                image = R.drawable.defiant_generation_red,
                price = 155.0,
                description = description,
                manufacturerId = 2,
                basicColorName = "red",
            ).also {
                it.copies = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.basicColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "green",
                        image = R.drawable.defiant_generation_green),
                )
            },
            Product(
                id = 12,
                name = "Solarthon Primegreen Gray",
                image = R.drawable.solarthon_primegreen_gray,
                price = 159.0,
                description = description,
                manufacturerId = 2,
                basicColorName = "gray",
            ).also {
                it.copies = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.basicColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "black",
                        image = R.drawable.solarthon_primegreen_black),
                    ProductColor(productId = it.id,
                        colorName = "red",
                        image = R.drawable.solarthon_primegreen_red),
                )
            },
            Product(
                id = 13,
                name = "Solarthon Primegreen Black",
                image = R.drawable.solarthon_primegreen_black,
                price = 149.0,
                description = description,
                manufacturerId = 2,
                basicColorName = "black",
            ).also {
                it.copies = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.basicColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "gray",
                        image = R.drawable.solarthon_primegreen_gray),
                    ProductColor(productId = it.id,
                        colorName = "red",
                        image = R.drawable.solarthon_primegreen_red),
                )
            },
            Product(
                id = 14,
                name = "Solarthon Primegreen Red",
                image = R.drawable.solarthon_primegreen_red,
                price = 125.0,
                description = description,
                manufacturerId = 2,
                basicColorName = "red",
            ).also {
                it.copies = mutableListOf(
                    ProductColor(productId = it.id,
                        colorName = it.basicColorName,
                        image = it.image),
                    ProductColor(productId = it.id,
                        colorName = "black",
                        image = R.drawable.solarthon_primegreen_black),
                    ProductColor(productId = it.id,
                        colorName = "gray",
                        image = R.drawable.solarthon_primegreen_gray),
                )
            },
        )

        init {
            nikeProducts.onEach {
                it.sizes = mutableListOf(
                    ProductSize(it.id, 38),
                    ProductSize(it.id, 40),
                    ProductSize(it.id, 42),
                    ProductSize(it.id, 44),
                )
            }
            adidasProducts.onEach {
                it.sizes = mutableListOf(
                    ProductSize(it.id, 38),
                    ProductSize(it.id, 40),
                    ProductSize(it.id, 42),
                    ProductSize(it.id, 44),
                )
            }

            scope.launch {
                populateDatabase(dao = client.get().getDao(), scope = scope)
            }
        }

        private suspend fun populateDatabase(dao: RoomDao, scope: CoroutineScope) {
            /** insert manufacturers */
            scope.launch {
                manufacturers.forEach {
                    dao.insertManufacturer(it)
                }
            }
            /** insert advertisements */
            scope.launch {
                advertisements.forEach {
                    dao.insertAdvertisement(it)
                }
            }
            scope.launch {
                nikeProducts.plus(adidasProducts).forEach {
                    /** Insert the product itself */
                    dao.insertProduct(product = it)
                    /** Insert colors */
                    it.copies?.forEach { productColor ->
                        dao.insertOtherProductCopy(productColor)
                    }
                    /** Insert size */
                    it.sizes?.forEach { productSize ->
                        dao.insertSize(productSize)
                    }
                }
            }
        }
    }

}