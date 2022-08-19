package com.mustfaibra.shoesstore.screens.cart

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.mustfaibra.shoesstore.R
import com.mustfaibra.shoesstore.components.CustomButton
import com.mustfaibra.shoesstore.components.DrawableButton
import com.mustfaibra.shoesstore.components.IconButton
import com.mustfaibra.shoesstore.components.PopupOptionsMenu
import com.mustfaibra.shoesstore.components.SimpleLoadingDialog
import com.mustfaibra.shoesstore.sealed.MenuOption
import com.mustfaibra.shoesstore.ui.theme.Dimension
import com.mustfaibra.shoesstore.utils.LocalScreenSize
import com.mustfaibra.shoesstore.utils.getDiscountedValue
import com.mustfaibra.shoesstore.utils.getDp
import kotlin.math.roundToInt

@Composable
fun CartScreen(
    cartViewModel: CartViewModel = hiltViewModel(),
    onProductClicked: (productId: Int) -> Unit,
) {
    val context = LocalContext.current
    val uiState by remember { cartViewModel.cartState }
    val totalPrice by remember { cartViewModel.totalPrice }
    val cartItems = cartViewModel.cartItems
    val cartOptionsMenuExpanded by remember { cartViewModel.cartOptionsMenuExpanded }
    val isSyncingCart by remember { cartViewModel.isSyncingCart }
    if (isSyncingCart) {
        SimpleLoadingDialog(title = "Please wait while we sync your cart")
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        var cardHeight by remember { mutableStateOf(0) }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background),
            verticalArrangement = Arrangement.spacedBy(Dimension.pagePadding.div(2)),
            contentPadding = PaddingValues(
                bottom = cardHeight.getDp(),
            )
        ) {
            /** Page header */
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.background)
                        .padding(horizontal = Dimension.pagePadding),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = stringResource(id = R.string.cart),
                        style = MaterialTheme.typography.h3,
                    )

                    PopupOptionsMenu(
                        icon = painterResource(id = R.drawable.ic_more_vertical),
                        iconSize = Dimension.smIcon,
                        iconBackgroundColor = MaterialTheme.colors.background,
                        menuContentColor = MaterialTheme.colors.onBackground.copy(alpha = 0.8f),
                        options = listOf(
                            MenuOption.ClearCart,
                        ),
                        onOptionsMenuExpandChanges = { cartViewModel.toggleOptionsMenuExpandState() },
                        onMenuOptionSelected = {
                            cartViewModel.toggleOptionsMenuExpandState()
                            when (it) {
                                is MenuOption.ClearCart -> cartViewModel.clearCart()
                                else -> {}
                            }
                        },
                        optionsMenuExpanded = cartOptionsMenuExpanded
                    )
                }
            }
            items(cartItems, key = { it.cartId ?: 0 }) { cartItem ->
                cartItem.product?.let { product ->
                    CartItemLayout(
                        productName = product.name,
                        productImage = product.image,
                        productPrice = product.price,
                        currentQty = cartItem.quantity,
                        discount = cartItem.product?.discount,
                        onProductClicked = { onProductClicked(product.id) },
                        onQuantityChanged = { newQuantity ->
                            cartViewModel.updateQuantity(
                                productId = product.id,
                                quantity = newQuantity,
                            )
                        },
                        onProductRemoved = {
                            cartViewModel.removeCartItem(productId = product.id)
                        },
                    )
                }
            }
        }
        /** Floating overall price with checkout section */
        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .onGloballyPositioned {
                    cardHeight = it.size.height
                },
            shape = RoundedCornerShape(
                topStartPercent = 25,
                topEndPercent = 25,
            ),
            backgroundColor = MaterialTheme.colors.background,
            elevation = Dimension.xl,
        ) {
            Column(
                modifier = Modifier
                    .shadow(
                        elevation = Dimension.elevation.div(2),
                        shape = RoundedCornerShape(
                            topStartPercent = 25,
                            topEndPercent = 25,
                        ),
                        clip = false,
                        spotColor = MaterialTheme.colors.primary,
                    )
                    .padding(
                        start = Dimension.pagePadding,
                        end = Dimension.pagePadding,
                        top = Dimension.pagePadding,
                        bottom = Dimension.sm
                    ),
                verticalArrangement = Arrangement.spacedBy(Dimension.sm),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Text(
                        text = stringResource(R.string.total),
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                    )
                    Spacer(
                        modifier = Modifier
                            .width(Dimension.lg)
                            .height(Dimension.elevation)
                            .clip(CircleShape)
                            .background(MaterialTheme.colors.primary.copy(alpha = 0.7f))
                    )
                    Text(
                        text = "$$totalPrice",
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface,
                    )
                }
                CustomButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.checkout),
                    textStyle = MaterialTheme.typography.body1,
                    buttonColor = MaterialTheme.colors.primary,
                    shape = RoundedCornerShape(percent = 35),
                    padding = PaddingValues(
                        all = Dimension.md.times(0.8f),
                    ),
                    onButtonClicked = {

                    },
                    contentColor = MaterialTheme.colors.onPrimary,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CartItemLayout(
    productName: String,
    productImage: Int,
    productPrice: Double,
    currentQty: Int,
    discount: Int?,
    onProductClicked: () -> Unit,
    onQuantityChanged: (qty: Int) -> Unit,
    onProductRemoved: () -> Unit,
) {
    val screenSize = LocalScreenSize.current
    val swipeState = rememberSwipeableState(0)
    val swipeAnchors = mapOf(
        0f to 0,
        -(screenSize.width.value.times(0.8f)) to 1
    )
    if (swipeState.offset.value == swipeAnchors.keys.last()) {
        onProductRemoved()
    }

    val infiniteTransition = rememberInfiniteTransition()
    val animatedOffset by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 20f,
        animationSpec = InfiniteRepeatableSpec(
            animation = TweenSpec(
                durationMillis = 1500,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Reverse,
        ),
    )
    val swipeTransition = updateTransition(
        targetState = swipeState.offset.value,
        label = "swipe-transition"
    )
    val productBackground by swipeTransition.animateColor(
        label = "product-background",
        targetValueByState = {
            when {
                it > swipeAnchors.keys.last()
                    .div(2) -> MaterialTheme.colors.primary.copy(alpha = 0.5f)
                else -> Color.Red.copy(alpha = 0.5f)
            }
        },
        transitionSpec = {
            TweenSpec(
                durationMillis = 1000,
                easing = LinearEasing,
            )
        }
    )
    Box(
        modifier = Modifier
            .swipeable(
                state = swipeState,
                anchors = swipeAnchors,
                orientation = Orientation.Horizontal,
            )
            .offset { IntOffset(x = swipeState.offset.value.roundToInt(), 0) }
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colors.background)
            .padding(horizontal = Dimension.pagePadding),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight()
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colors.surface),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .background(Color.Transparent)
                .padding(Dimension.pagePadding.div(2)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            /** Product's info */
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = productName,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.large)
                        .clickable { onProductClicked() },
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "$${(productPrice * currentQty).getDiscountedValue(discount = discount ?: 0)}",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.8f)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Dimension.pagePadding),
                ) {
                    Row(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colors.secondary.copy(alpha = 0.3f))
                            .padding(Dimension.xs),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Dimension.pagePadding.div(2)),
                    ) {
                        DrawableButton(
                            painter = painterResource(id = R.drawable.ic_round_remove_24),
                            enabled = currentQty > 1,
                            onButtonClicked = { onQuantityChanged(currentQty.dec()) },
                            backgroundColor = if (currentQty > 1) MaterialTheme.colors.secondary
                            else MaterialTheme.colors.background,
                            iconTint = if (currentQty > 1) MaterialTheme.colors.background
                            else MaterialTheme.colors.onBackground,
                            iconSize = Dimension.smIcon.times(0.8f),
                            shape = CircleShape,
                        )
                        Text(
                            text = "$currentQty",
                            style = MaterialTheme.typography.caption.copy(
                                fontWeight = FontWeight.Medium,
                            ),
                        )
                        IconButton(
                            icon = Icons.Rounded.Add,
                            onButtonClicked = { onQuantityChanged(currentQty.inc()) },
                            backgroundColor = MaterialTheme.colors.secondary,
                            iconSize = Dimension.smIcon.times(0.8f),
                            iconTint = MaterialTheme.colors.background,
                            shape = CircleShape,
                        )
                    }
                }
            }
            /** Product's image */
            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.large)
                    .background(productBackground)
                    .padding(Dimension.xs)
            ) {
                Image(
                    painter = rememberImagePainter(data = productImage),
                    contentDescription = null,
                    modifier = Modifier
                        .offset(x = 0.getDp(), y = animatedOffset.getDp())
                        .size(Dimension.xlIcon)
                        .rotate(-45f),
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}