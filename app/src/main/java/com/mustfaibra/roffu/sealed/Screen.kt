package com.mustfaibra.roffu.sealed

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.mustfaibra.roffu.R

sealed class Screen(
    val route: String,
    @StringRes val title: Int? = null,
    @DrawableRes val icon: Int? = null,
) {
    object Splash : Screen(
        route = "splash",
    )
    object Onboard : Screen(
        route = "onboard",
        title = R.string.onboard,
    )
    object Signup : Screen(
        route = "signup",
        title = R.string.signup,
    )
    object Login : Screen(
        route = "login",
        title = R.string.login,
    )
    object Home : Screen(
        route = "home",
        title = R.string.home,
        icon = R.drawable.ic_home_empty,
    )
    object Bookmark : Screen(
        route = "bookmark",
        title = R.string.bookmarks,
        icon = R.drawable.ic_bookmark,
    )

    object Profile : Screen(
        route = "profile",
        title = R.string.profile,
        icon = R.drawable.ic_profile_empty,
    )

    object Notifications : Screen(
        route = "notifications",
        title = R.string.notifications,
        icon = R.drawable.ic_notifications,
    )

    object Search : Screen(
        route = "search",
        title = R.string.search,
        icon = R.drawable.ic_search,
    )
    object Cart : Screen(
        route = "cart",
        title = R.string.cart,
        icon = R.drawable.ic_shopping_bag,
    )
    object Checkout : Screen(
        route = "checkout",
        title = R.string.checkout,
    )
    object ProductDetails : Screen(
        route = "product-details/{productId}",
        title = R.string.product_details,
    )

    object LocationPicker : Screen(
        route = "location-picker",
        title = R.string.delivery_address,
    )

    object Settings : Screen(
        route = "settings",
        title = R.string.settings,
        icon = R.drawable.ic_settings,
    )

    object OrderHistory : Screen(
        route = "orders",
        title = R.string.orders_history,
        icon = R.drawable.ic_history,
    )

    object PrivacyPolicies : Screen(
        route = "privacy-policies",
        title = R.string.privacy_and_policies,
        icon = R.drawable.ic_lock,
    )

    object TermsConditions : Screen(
        route = "terms-conditions",
        title = R.string.terms_and_conditions,
        icon = R.drawable.ic_terms,
    )
}
