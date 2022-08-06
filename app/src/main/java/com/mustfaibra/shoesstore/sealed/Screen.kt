package com.mustfaibra.shoesstore.sealed

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.mustfaibra.shoesstore.R

sealed class Screen(
    val route: String,
    @StringRes val title: Int? = null,
    @DrawableRes val icon: Int? = null,
) {
    object Splash : Screen(route = "splash")
    object Onboard : Screen(route = "onboard")
    object Signup : Screen(route = "signup")
    object Login : Screen(route = "login")
    object Home : Screen(route = "home", title = R.string.home, icon = R.drawable.ic_home_empty,)
    object Profile : Screen(route = "profile", title = R.string.profile, icon = R.drawable.ic_profile_empty,)
    object Notifications : Screen(route = "notifications", title = R.string.notifications, icon = R.drawable.ic_notifications)
    object Search : Screen(route = "search", title = R.string.search, icon = R.drawable.ic_search)
    object Cart : Screen(route = "cart", title = R.string.cart, icon = R.drawable.ic_shopping_bag)

    object ProductDetails : Screen(route = "product-details/{productId}", title = R.string.product_details)
}
