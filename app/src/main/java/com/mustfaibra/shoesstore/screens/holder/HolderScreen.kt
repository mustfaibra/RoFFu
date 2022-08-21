package com.mustfaibra.shoesstore.screens.holder

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mustfaibra.shoesstore.components.AppBottomNav
import com.mustfaibra.shoesstore.models.User
import com.mustfaibra.shoesstore.providers.LocalNavHost
import com.mustfaibra.shoesstore.screens.bookmarks.BookmarksScreen
import com.mustfaibra.shoesstore.screens.cart.CartScreen
import com.mustfaibra.shoesstore.screens.home.HomeScreen
import com.mustfaibra.shoesstore.screens.login.LoginScreen
import com.mustfaibra.shoesstore.screens.notifications.NotificationScreen
import com.mustfaibra.shoesstore.screens.onboard.OnboardScreen
import com.mustfaibra.shoesstore.screens.productdetails.ProductDetailsScreen
import com.mustfaibra.shoesstore.screens.profile.ProfileScreen
import com.mustfaibra.shoesstore.screens.search.SearchScreen
import com.mustfaibra.shoesstore.screens.signup.SignupScreen
import com.mustfaibra.shoesstore.screens.splash.SplashScreen
import com.mustfaibra.shoesstore.sealed.Screen
import com.mustfaibra.shoesstore.utils.UserPref
import com.skydoves.whatif.whatIfNotNull

@Composable
fun HolderScreen(
    onStatusBarColorChange: (color: Color) -> Unit,
    holderViewModel: HolderViewModel = hiltViewModel(),
) {
    val destinations = remember {
        listOf(Screen.Home, Screen.Notifications, Screen.Bookmark, Screen.Profile)
    }
    val controller = LocalNavHost.current
    val currentDestinationAsState = getActiveRoute(navController = controller)
    val productsOnCartIds = holderViewModel.productsOnCartIds
    val productsOnBookmarksIds = holderViewModel.productsOnBookmarksIds
    val user by remember { UserPref.user }


    ScaffoldSection(
        controller = controller,
        user = user,
        productsOnCartIds = productsOnCartIds,
        productsOnBookmarksIds = productsOnBookmarksIds,
        onStatusBarColorChange = onStatusBarColorChange,
        bottomNavigationContent = {
            if (
                currentDestinationAsState in destinations.map { it.route }
                || currentDestinationAsState == Screen.Cart.route
            ) {
                AppBottomNav(
                    activeRoute = currentDestinationAsState,
                    backgroundColor = MaterialTheme.colors.surface,
                    bottomNavDestinations = destinations,
                    onActiveRouteChange = {
                        if (it != currentDestinationAsState) {
                            /** We should navigate to that new route */
                            controller.navigate(it) {
                                popUpTo(Screen.Home.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        },
        onSplashFinished = { nextDestination ->
            controller.navigate(nextDestination.route) {
                popUpTo(Screen.Splash.route) {
                    inclusive = true
                }
            }
        },
        onBoardFinished = {
            controller.navigate(Screen.Home.route) {
                popUpTo(Screen.Onboard.route) {
                    inclusive = true
                }
            }
        },
        onShowProductRequest = { productId ->
            controller.navigate(
                Screen.ProductDetails.route
                    .replace("{productId}", "$productId")
            )
        },
        onUpdateCartRequest = { productId ->
            holderViewModel.updateCart(
                productId = productId,
                currentlyOnCart = productId in productsOnCartIds,
            )
        },
        onUpdateBookmarkRequest = { productId ->
            holderViewModel.updateBookmarks(
                productId = productId,
                currentlyOnBookmarks = productId in productsOnBookmarksIds,
            )
        },
        onUserNotAuthorized = {
            controller.navigate(Screen.Login.route)
        }
    )
}

@Composable
fun ScaffoldSection(
    controller: NavHostController,
    user: User?,
    productsOnCartIds: List<Int>,
    productsOnBookmarksIds: List<Int>,
    onStatusBarColorChange: (color: Color) -> Unit,
    onSplashFinished: (nextDestination: Screen) -> Unit,
    onBoardFinished: () -> Unit,
    onUpdateCartRequest: (productId: Int) -> Unit,
    onUpdateBookmarkRequest: (productId: Int) -> Unit,
    onShowProductRequest: (productId: Int) -> Unit,
    onUserNotAuthorized: () -> Unit,
    bottomNavigationContent: @Composable () -> Unit,
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
        ) {
            NavHost(
                modifier = Modifier.weight(1f),
                navController = controller,
                startDestination = Screen.Home.route
            ) {
                composable(Screen.Splash.route) {
                    onStatusBarColorChange(MaterialTheme.colors.primary)
                    SplashScreen(onSplashFinished = onSplashFinished)
                }
                composable(Screen.Onboard.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    OnboardScreen(onBoardFinished = onBoardFinished)
                }
                composable(Screen.Signup.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    SignupScreen()
                }
                composable(Screen.Login.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    LoginScreen()
                }
                composable(Screen.Home.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    HomeScreen(
                        cartProductsIds = productsOnCartIds,
                        bookmarkProductsIds = productsOnBookmarksIds,
                        onProductClicked = onShowProductRequest,
                        onCartStateChanged = onUpdateCartRequest,
                        onBookmarkStateChanged = onUpdateBookmarkRequest,
                    )
                }
                composable(Screen.ProductDetails.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    val productId = it.arguments?.getInt("productId")
                        ?: throw IllegalArgumentException("Product id is required")

                    ProductDetailsScreen(
                        productId = productId,
                        isOnCartStateProvider = { productId in productsOnCartIds },
                        isOnBookmarksStateProvider = {},
                        onUpdateCartState = onUpdateCartRequest,
                        onUpdateBookmarksState = onUpdateBookmarkRequest,
                    )
                }
                composable(Screen.Notifications.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    NotificationScreen()
                }
                composable(Screen.Search.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    SearchScreen()
                }
                composable(Screen.Bookmark.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    BookmarksScreen(
                        cartProductsIds = productsOnCartIds,
                        onProductClicked = onShowProductRequest,
                        onCartStateChanged = onUpdateCartRequest,
                        onBookmarkStateChanged = onUpdateBookmarkRequest,
                    )
                }
                composable(Screen.Cart.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    CartScreen(onProductClicked = onShowProductRequest)
                }
                composable(Screen.Profile.route) {
                    user.whatIfNotNull(
                        whatIf = {
                            onStatusBarColorChange(MaterialTheme.colors.background)
                            ProfileScreen(user = it)
                        },
                        whatIfNot = {
                            LaunchedEffect(key1 = Unit) {
                                onUserNotAuthorized()
                            }
                        },
                    )
                }

            }
            /** Now we lay down our bottom navigation component */
            bottomNavigationContent()
        }
    }
}

/**
 * A function that is used to get the active route in our Navigation Graph , should return the splash route if it's null
 */
@Composable
fun getActiveRoute(navController: NavHostController): String {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route ?: "splash"
}
