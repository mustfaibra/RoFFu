package com.mustfaibra.shoesstore.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarData
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import coil.compose.rememberImagePainter
import com.mustfaibra.shoesstore.R
import com.mustfaibra.shoesstore.sealed.MenuOption
import com.mustfaibra.shoesstore.sealed.Screen
import com.mustfaibra.shoesstore.ui.theme.Dimension
import com.mustfaibra.shoesstore.utils.getDiscountedValue
import com.mustfaibra.shoesstore.utils.getDp

@Composable
fun CustomInputField(
    modifier: Modifier = Modifier,
    value: String,
    placeholder: String,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    textColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.8f),
    backgroundColor: Color = MaterialTheme.colors.surface,
    requireSingleLine: Boolean = true,
    textShouldBeCentered: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    maxLength: Int = Int.MAX_VALUE,
    imeAction: ImeAction = ImeAction.Done,
    shape: Shape = MaterialTheme.shapes.small,
    padding: PaddingValues = PaddingValues(horizontal = Dimension.xs),
    leadingIcon: @Composable () -> Unit = {},
    trailingIcon: @Composable () -> Unit = {},
    onValueChange: (string: String) -> Unit,
    onFocusChange: (focused: Boolean) -> Unit,
    onKeyboardActionClicked: KeyboardActionScope.() -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    val inputService = LocalTextInputService.current

    Row(
        modifier = modifier
            .clip(shape = shape)
            .background(backgroundColor)
            .padding(paddingValues = padding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimension.pagePadding.div(2))
    ) {
        leadingIcon()
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged {
                    onFocusChange(it.isFocused)
                },
            value = value,
            onValueChange = {
                if (it.length <= maxLength) {
                    /** when the value change and maxLength is not reached yet then pass it up **/
                    onValueChange(it)
                }
            },
            decorationBox = { container ->
                Box(
                    contentAlignment = if (textShouldBeCentered) Alignment.Center else Alignment.CenterStart,
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = textStyle,
                            color = textColor.copy(alpha = 0.3f),
                            maxLines = if (requireSingleLine) 1 else Int.MAX_VALUE,
                        )
                    }
                    container()
                }
            },
            visualTransformation = visualTransformation,
            singleLine = requireSingleLine,
            textStyle = textStyle.copy(color = textColor),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            keyboardActions = KeyboardActions(
                onAny = {
                    focusRequester.freeFocus()
                    /** It doesn't has the focus now, hide the input keyboard */
                    inputService?.hideSoftwareKeyboard()
                    onKeyboardActionClicked()
                }
            ),
            cursorBrush = SolidColor(value = textColor),
        )
        trailingIcon()
    }
}

@Composable
fun SecondaryTopBar(
    title: String,
    backgroundColor: Color = MaterialTheme.colors.background,
    contentColor: Color = MaterialTheme.colors.onBackground,
    onBackClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(horizontal = Dimension.pagePadding, vertical = Dimension.pagePadding.div(2)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimension.pagePadding.times(1.5f)),
    ) {
        IconButton(
            icon = Icons.Rounded.KeyboardArrowLeft,
            backgroundColor = Color.Transparent,
            iconTint = contentColor,
            onButtonClicked = onBackClicked,
            shape = MaterialTheme.shapes.medium,
        )
        Text(
            text = title,
            style = MaterialTheme.typography.button,
            color = contentColor,
            maxLines = 1,
        )
    }
}

@Composable
fun AppBottomNav(
    activeRoute: String,
    bottomNavDestinations: List<Screen>,
    backgroundColor: Color,
    onActiveRouteChange: (route: String) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = Dimension.pagePadding,
                    vertical = Dimension.pagePadding.div(2),
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            bottomNavDestinations.forEach {
                val isActive = activeRoute.equals(other = it.route, ignoreCase = true)
                AppBottomNavItem(
                    active = isActive,
                    title = stringResource(id = it.title ?: R.string.home),
                    icon = it.icon ?: R.drawable.ic_home_empty,
                    onRouteClicked = {
                        if (!isActive) {
                            onActiveRouteChange(it.route)
                        }
                    }
                )
                if (bottomNavDestinations.indexOf(it)
                        .plus(1) == bottomNavDestinations.size.div(2)
                ) {
                    Spacer(modifier = Modifier.width(Dimension.smIcon))
                }
            }
        }
        DrawableButton(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = -(Dimension.md.plus(Dimension.mdIcon)).div(2))
                .border(
                    width = Dimension.sm,
                    color = MaterialTheme.colors.background,
                    shape = CircleShape,
                )
                .padding(Dimension.sm),
            painter = painterResource(id = R.drawable.ic_shopping_bag),
            backgroundColor = if (activeRoute == Screen.Cart.route) MaterialTheme.colors.primary
            else MaterialTheme.colors.background,
            iconSize = Dimension.mdIcon.times(0.8f),
            iconTint = if (activeRoute == Screen.Cart.route) MaterialTheme.colors.onPrimary
            else MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
            onButtonClicked = { onActiveRouteChange(Screen.Cart.route) },
            shape = CircleShape,
            paddingValue = PaddingValues(Dimension.md),
        )
    }
}

@Composable
fun AppBottomNavItem(
    modifier: Modifier = Modifier,
    active: Boolean,
    title: String,
    icon: Int,
    onRouteClicked: () -> Unit,
) {
    Column(
        modifier = modifier
            .width(IntrinsicSize.Min)
            .clickable {
                onRouteClicked()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(
            modifier = Modifier
                .padding(bottom = Dimension.sm)
                .fillMaxWidth()
                .height(Dimension.xs)
                .clip(MaterialTheme.shapes.medium)
                .background(
                    if (active) MaterialTheme.colors.primary
                    else Color.Transparent,
                )
        )
        Icon(
            painter = painterResource(id = icon),
            contentDescription = title,
            tint = if (active) MaterialTheme.colors.primary
            else MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
            modifier = Modifier
                .size(Dimension.smIcon)
        )
    }
}

@Composable
fun PopupOptionsMenu(
    icon: Painter,
    iconSize: Dp = Dimension.smIcon,
    iconBackgroundColor: Color = MaterialTheme.colors.background,
    menuBackgroundColor: Color = MaterialTheme.colors.surface,
    menuContentColor: Color = MaterialTheme.colors.onSurface,
    options: List<MenuOption>,
    onOptionsMenuExpandChanges: () -> Unit,
    onMenuOptionSelected: (option: MenuOption) -> Unit,
    optionsMenuExpanded: Boolean,
) {
    Box {
        DrawableButton(
            painter = icon,
            onButtonClicked = onOptionsMenuExpandChanges,
            iconTint = menuContentColor,
            backgroundColor = iconBackgroundColor,
            iconSize = iconSize,
        )
        DropdownMenu(
            modifier = Modifier
                .width(intrinsicSize = IntrinsicSize.Max)
                .background(menuBackgroundColor),
            expanded = optionsMenuExpanded,
            onDismissRequest = onOptionsMenuExpandChanges,
        ) {
            options.forEach { option ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(menuBackgroundColor)
                        .clickable { onMenuOptionSelected(option) }
                        .padding(
                            horizontal = Dimension.pagePadding,
                            vertical = Dimension.xs
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Dimension.pagePadding.div(2)),
                ) {

                    option.icon?.let { icon ->
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = stringResource(id = option.title),
                            modifier = Modifier.size(Dimension.smIcon),
                            tint = menuContentColor,
                        )
                    }
                    Text(
                        text = stringResource(id = option.title),
                        color = menuContentColor,
                        style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Medium),
                    )
                }
            }
        }
    }
}

@Composable
fun ReactiveCartIcon(
    modifier: Modifier = Modifier,
    isOnCart: Boolean,
    onCartChange: () -> Unit,
) {
    val transition =
        updateTransition(targetState = isOnCart, label = "cart")

    val tint by transition.animateColor(label = "tint") {
        when (it) {
            true -> MaterialTheme.colors.primary
            false -> MaterialTheme.colors.onBackground.copy(alpha = 0.5f)
        }
    }
    val rotation by transition.animateFloat(label = "rotation") { if (it) 360f else -360f }
    Icon(
        painter = painterResource(id = R.drawable.ic_shopping_bag),
        contentDescription = null,
        modifier = modifier
            .rotate(rotation)
            .clip(CircleShape)
            .clickable { onCartChange() }
            .padding(Dimension.elevation)
            .size(Dimension.smIcon),
        tint = tint
    )
}

@Composable
fun ReactiveBookmarkIcon(
    modifier: Modifier = Modifier,
    iconSize: Dp = Dimension.smIcon,
    isOnBookmarks: Boolean,
    onBookmarkChange: () -> Unit,
) {
    val transition =
        updateTransition(targetState = isOnBookmarks, label = "bookmark")

    val tint by transition.animateColor(label = "tint") {
        when (it) {
            true -> MaterialTheme.colors.secondary
            false -> MaterialTheme.colors.onBackground.copy(alpha = 0.5f)
        }
    }
    val rotation by transition.animateFloat(label = "rotation") { if (it) 360f else -360f }
    Icon(
        painter = painterResource(id = if (isOnBookmarks) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark),
        contentDescription = null,
        modifier = modifier
            .rotate(rotation)
            .clip(CircleShape)
            .clickable { onBookmarkChange() }
            .padding(Dimension.elevation)
            .size(iconSize),
        tint = tint
    )
}

@Composable
fun ProductItemLayout(
    modifier: Modifier = Modifier,
    image: Int,
    price: Double,
    title: String,
    discount: Int,
    onCart: Boolean = false,
    onBookmark: Boolean = false,
    onProductClicked: () -> Unit,
    onChangeCartState: () -> Unit,
    onChangeBookmarkState: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.small)
            .clickable(
                indication = null,
                interactionSource = MutableInteractionSource(),
                onClick = onProductClicked
            ),
        verticalArrangement = Arrangement.spacedBy(Dimension.pagePadding)
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(MaterialTheme.shapes.medium)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(this.constraints.maxHeight
                        .div(2)
                        .getDp())
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colors.surface),
            )
            Image(
                painter = rememberImagePainter(data = image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.medium)
                    .rotate(-35f),
            )
            ReactiveBookmarkIcon(
                modifier = Modifier.padding(Dimension.xs),
                isOnBookmarks = onBookmark,
                onBookmarkChange = onChangeBookmarkState
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(Dimension.xs)
        ) {
            /** Product's interactions */
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                /** Price */
                val cost = stringResource(
                    id = R.string.x_dollar,
                    price.getDiscountedValue(discount = discount)
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = cost,
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colors.onBackground.copy(alpha = 0.7f),
                    ),
                )
                ReactiveCartIcon(
                    isOnCart = onCart,
                    onCartChange = onChangeCartState,
                )
            }
            /** Product's name */
            Text(
                modifier = Modifier,
                text = title,
                style = MaterialTheme.typography.caption,
                maxLines = 2,
            )
        }

    }
}

@Composable
fun CustomSnackBar(
    modifier: Modifier = Modifier,
    snackHost: SnackbarHostState,
    content: @Composable (data: SnackbarData) -> Unit = {
        Text(
            text = it.message,
            style = MaterialTheme.typography.body1,
        )
    },
    backgroundColorProvider: () -> Color = { Color.White },
    contentColor: Color = MaterialTheme.colors.onPrimary,
) {
    SnackbarHost(hostState = snackHost, modifier = modifier) { data ->
        Snackbar(
            modifier = Modifier
                .padding(Dimension.pagePadding)
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            backgroundColor = backgroundColorProvider(),
            contentColor = contentColor,
            content = {
                content(data)
            }
        )
    }
}

@Composable
fun SummaryRow(
    title: String,
    value: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
        )

        Text(
            text = value,
            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.SemiBold),
            color = MaterialTheme.colors.secondary,
        )
    }
}