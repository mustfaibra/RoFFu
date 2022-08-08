package com.mustfaibra.shoesstore.screens.holder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mustfaibra.shoesstore.components.AppBottomNav
import com.mustfaibra.shoesstore.providers.LocalNavHost
import com.mustfaibra.shoesstore.screens.home.HomeScreen
import com.mustfaibra.shoesstore.screens.login.LoginScreen
import com.mustfaibra.shoesstore.screens.notifications.NotificationScreen
import com.mustfaibra.shoesstore.screens.onboard.OnboardScreen
import com.mustfaibra.shoesstore.screens.profile.ProfileScreen
import com.mustfaibra.shoesstore.screens.search.SearchScreen
import com.mustfaibra.shoesstore.screens.signup.SignupScreen
import com.mustfaibra.shoesstore.screens.splash.SplashScreen
import com.mustfaibra.shoesstore.sealed.Screen
import com.mustfaibra.shoesstore.ui.theme.Dimension
import timber.log.Timber

@Composable
fun HolderScreen(
    onStatusBarColorChange: (color: Color) -> Unit,
    holderViewModel: HolderViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = Unit){
        holderViewModel.getCartItems()
    }

    val destinations = remember {
        listOf(Screen.Home, Screen.Notifications, Screen.Search, Screen.Cart, Screen.Profile)
    }
    val controller = LocalNavHost.current
    val currentDestinationAsState = getActiveRoute(navController = controller)

    val productsOnCartIds = holderViewModel.productsOnCartIds

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
        ) {
            NavHost(
                modifier = Modifier.weight(1f),
                navController = controller,
                startDestination = Screen.Splash.route
            ) {
                composable(Screen.Splash.route) {
                    onStatusBarColorChange(MaterialTheme.colors.primary)
                    SplashScreen(
                        onSplashFinished = { nextDestination ->
                            controller.navigate(nextDestination.route) {
                                popUpTo(Screen.Splash.route) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
                composable(Screen.Onboard.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    OnboardScreen(
                        onBoardFinished = {
                            controller.navigate(Screen.Home.route) {
                                popUpTo(Screen.Onboard.route) {
                                    inclusive = true
                                }
                            }
                        }
                    )
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
                        onProductClicked = { productId ->
                            controller.navigate(
                                Screen.ProductDetails.route
                                    .replace("{productId}","$productId")
                            )
                        },
                        onCartStateChanged = { productId ->
                            holderViewModel.updateCart(
                                productId = productId,
                                currentlyOnCart = productId in productsOnCartIds,
                            )
                        }
                    )
                }
                composable(Screen.ProductDetails.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    val productId = it.arguments?.getInt("productId")
                        ?: throw IllegalArgumentException("Product id is required")

                    Timber.d("Going to product's details which it's id is $productId")
                }

                composable(Screen.Notifications.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    NotificationScreen()
                }
                composable(Screen.Search.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    SearchScreen()
                }
                composable(Screen.Cart.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)

                }
                composable(Screen.Profile.route) {
                    onStatusBarColorChange(MaterialTheme.colors.background)
                    ProfileScreen()
                }

            }

            if (currentDestinationAsState in destinations.map { it.route }) {
                AppBottomNav(
                    activeRoute = currentDestinationAsState,
                    backgroundColor = MaterialTheme.colors.background,
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
