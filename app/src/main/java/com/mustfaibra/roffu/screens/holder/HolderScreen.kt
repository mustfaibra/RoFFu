package com.mustfaibra.roffu.screens.holder

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.mustfaibra.roffu.components.AppBottomNav
import com.mustfaibra.roffu.components.CustomSnackBar
import com.mustfaibra.roffu.models.CartItem
import com.mustfaibra.roffu.models.User
import com.mustfaibra.roffu.providers.LocalNavHost
import com.mustfaibra.roffu.screens.bookmarks.BookmarksScreen
import com.mustfaibra.roffu.screens.cart.CartScreen
import com.mustfaibra.roffu.screens.checkout.CheckoutScreen
import com.mustfaibra.roffu.screens.home.HomeScreen
import com.mustfaibra.roffu.screens.locationpicker.LocationPickerScreen
import com.mustfaibra.roffu.screens.login.LoginScreen
import com.mustfaibra.roffu.screens.notifications.NotificationScreen
import com.mustfaibra.roffu.screens.onboard.OnboardScreen
import com.mustfaibra.roffu.screens.orderhistory.OrdersHistoryScreen
import com.mustfaibra.roffu.screens.productdetails.ProductDetailsScreen
import com.mustfaibra.roffu.screens.profile.ProfileScreen
import com.mustfaibra.roffu.screens.search.SearchScreen
import com.mustfaibra.roffu.screens.signup.SignupScreen
import com.mustfaibra.roffu.screens.splash.SplashScreen
import com.mustfaibra.roffu.sealed.Screen
import com.mustfaibra.roffu.utils.UserPref
import com.mustfaibra.roffu.utils.getDp
import com.skydoves.whatif.whatIfNotNull
import kotlinx.coroutines.launch

@Composable
fun HolderScreen(
    onStatusBarColorChange: (color: Color) -> Unit,
    holderViewModel: HolderViewModel = hiltViewModel(),
) {
    val destinations = remember {
        listOf(Screen.Home, Screen.Notifications, Screen.Bookmark, Screen.Profile)
    }

    /** Our navigation controller that the MainActivity provides */
    val controller = LocalNavHost.current

    /** The current active navigation route */
    val currentRouteAsState = getActiveRoute(navController = controller)

    /** The cart items list */
    val cartItems = holderViewModel.cartItems

    /** The ids of all the products on user's cart */
    val productsOnCartIds = holderViewModel.productsOnCartIds

    /** The ids of all the bookmarked products on user's bookmarks */
    val productsOnBookmarksIds = holderViewModel.productsOnBookmarksIds

    /** The current logged user, which is null by default */
    val user by UserPref.user

    /** The main app's scaffold state */
    val scaffoldState = rememberScaffoldState()

    /** The coroutine scope */
    val scope = rememberCoroutineScope()

    /** Dynamic snack bar color */
    val (snackBarColor, setSnackBarColor) = remember {
        mutableStateOf(Color.White)
    }

    /** SnackBar appear/disappear transition */
    val snackBarTransition = updateTransition(
        targetState = scaffoldState.snackbarHostState,
        label = "SnackBarTransition"
    )

    /** SnackBar animated offset */
    val snackBarOffsetAnim by snackBarTransition.animateDp(
        label = "snackBarOffsetAnim",
        transitionSpec = {
            TweenSpec(
                durationMillis = 300,
                easing = LinearEasing,
            )
        }
    ) {
        when (it.currentSnackbarData) {
            null -> {
                100.getDp()
            }
            else -> {
                0.getDp()
            }
        }
    }

    Box {
        /** Cart offset on the screen */
        val (cartOffset, setCartOffset) = remember {
            mutableStateOf(IntOffset(0, 0))
        }
        ScaffoldSection(
            controller = controller,
            scaffoldState = scaffoldState,
            cartOffset = cartOffset,
            user = user,
            cartItems = cartItems,
            productsOnCartIds = productsOnCartIds,
            productsOnBookmarksIds = productsOnBookmarksIds,
            onStatusBarColorChange = onStatusBarColorChange,
            bottomNavigationContent = {
                if (
                    currentRouteAsState in destinations.map { it.route }
                    || currentRouteAsState == Screen.Cart.route
                ) {
                    AppBottomNav(
                        activeRoute = currentRouteAsState,
                        backgroundColor = MaterialTheme.colors.surface,
                        bottomNavDestinations = destinations,
                        onCartOffsetMeasured = { offset ->
                            setCartOffset(offset)
                        },
                        onActiveRouteChange = {
                            if (it != currentRouteAsState) {
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
            onBackRequested = {
                controller.popBackStack()
            },
            onNavigationRequested = { route, removePreviousRoute ->
                if (removePreviousRoute) {
                    controller.popBackStack()
                }
                controller.navigate(route)
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
            onUserNotAuthorized = { removeCurrentRoute ->
                if (removeCurrentRoute) {
                    controller.popBackStack()
                }
                controller.navigate(Screen.Login.route)
            },
            onToastRequested = { message, color ->
                scope.launch {
                    /** dismiss the previous one if its exist */
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                    /** Update the snack bar color */
                    setSnackBarColor(color)
                    scaffoldState.snackbarHostState
                        .showSnackbar(
                            message = message,
                            duration = SnackbarDuration.Short,
                        )
                }
            }
        )

        /** The snack bar UI */
        CustomSnackBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = snackBarOffsetAnim),
            snackHost = scaffoldState.snackbarHostState,
            backgroundColorProvider = { snackBarColor },
        )
    }
}

@Composable
fun ScaffoldSection(
    controller: NavHostController,
    scaffoldState: ScaffoldState,
    cartOffset: IntOffset,
    user: User?,
    cartItems: List<CartItem>,
    productsOnCartIds: List<Int>,
    productsOnBookmarksIds: List<Int>,
    onStatusBarColorChange: (color: Color) -> Unit,
    onSplashFinished: (nextDestination: Screen) -> Unit,
    onBoardFinished: () -> Unit,
    onNavigationRequested: (route: String, removePreviousRoute: Boolean) -> Unit,
    onBackRequested: () -> Unit,
    onUpdateCartRequest: (productId: Int) -> Unit,
    onUpdateBookmarkRequest: (productId: Int) -> Unit,
    onShowProductRequest: (productId: Int) -> Unit,
    onUserNotAuthorized: (removeCurrentRoute: Boolean) -> Unit,
    onToastRequested: (message: String, color: Color) -> Unit,
    bottomNavigationContent: @Composable () -> Unit,
) {
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            scaffoldState.snackbarHostState
        },
    ) { paddingValues ->
        Column(
            Modifier.padding(paddingValues)
        ) {
            NavHost(
                modifier = Modifier.weight(1f),
                navController = controller,
                startDestination = Screen.Splash.route
            ) {
                composable(Screen.Splash.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
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
                    LoginScreen(
                        onUserAuthenticated = onBackRequested,
                        onToastRequested = onToastRequested,
                    )
                }
                composable(Screen.Home.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    HomeScreen(
                        cartOffset = cartOffset,
                        cartProductsIds = productsOnCartIds,
                        bookmarkProductsIds = productsOnBookmarksIds,
                        onProductClicked = onShowProductRequest,
                        onCartStateChanged = onUpdateCartRequest,
                        onBookmarkStateChanged = onUpdateBookmarkRequest,
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
                        cartOffset = cartOffset,
                        cartProductsIds = productsOnCartIds,
                        onProductClicked = onShowProductRequest,
                        onCartStateChanged = onUpdateCartRequest,
                        onBookmarkStateChanged = onUpdateBookmarkRequest,
                    )
                }
                composable(Screen.Cart.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    CartScreen(
                        user = user,
                        cartItems = cartItems,
                        onProductClicked = onShowProductRequest,
                        onUserNotAuthorized = { onUserNotAuthorized(false) },
                        onCheckoutRequest = {
                            onNavigationRequested(Screen.Checkout.route, false)
                        },
                    )
                }
                composable(Screen.Checkout.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    user.whatIfNotNull(
                        whatIf = {
                            CheckoutScreen(
                                cartItems = cartItems,
                                onBackRequested = onBackRequested,
                                onCheckoutSuccess = {
                                    onNavigationRequested(Screen.OrderHistory.route, true)
                                },
                                onToastRequested = onToastRequested,
                                onChangeLocationRequested = {
                                    onNavigationRequested(Screen.LocationPicker.route, false)
                                }
                            )
                        },
                        whatIfNot = {
                            LaunchedEffect(key1 = Unit) {
                                onUserNotAuthorized(true)
                            }
                        },
                    )
                }
                composable(Screen.LocationPicker.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    LocationPickerScreen(
                        onLocationRequested = {

                        },
                        onLocationPicked = {

                        }
                    )
                }
                composable(Screen.Profile.route) {
                    user.whatIfNotNull(
                        whatIf = {
                            onStatusBarColorChange(MaterialTheme.colors.background)
                            ProfileScreen(
                                user = it,
                                onNavigationRequested = onNavigationRequested,
                            )
                        },
                        whatIfNot = {
                            LaunchedEffect(key1 = Unit) {
                                onUserNotAuthorized(false)
                            }
                        },
                    )
                }
                composable(Screen.OrderHistory.route) {
                    user.whatIfNotNull(
                        whatIf = {
                            onStatusBarColorChange(MaterialTheme.colors.background)
                            OrdersHistoryScreen(
                                onBackRequested = onBackRequested,
                            )
                        },
                        whatIfNot = {
                            LaunchedEffect(key1 = Unit) {
                                onUserNotAuthorized(true)
                            }
                        },
                    )
                }
                composable(
                    route = Screen.ProductDetails.route,
                    arguments = listOf(
                        navArgument(name = "productId") { type = NavType.IntType }
                    ),
                ) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    val productId = it.arguments?.getInt("productId")
                        ?: throw IllegalArgumentException("Product id is required")

                    ProductDetailsScreen(
                        productId = productId,
                        cartItemsCount = cartItems.size,
                        isOnCartStateProvider = { productId in productsOnCartIds },
                        isOnBookmarksStateProvider = { productId in productsOnBookmarksIds },
                        onUpdateCartState = onUpdateCartRequest,
                        onUpdateBookmarksState = onUpdateBookmarkRequest,
                        onBackRequested = onBackRequested,
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
