package com.mustfaibra.shoesstore.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.unit.dp
import com.mustfaibra.shoesstore.R
import com.mustfaibra.shoesstore.sealed.MenuOption
import com.mustfaibra.shoesstore.sealed.Screen
import com.mustfaibra.shoesstore.ui.theme.Dimension
import com.mustfaibra.shoesstore.utils.mirror

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
    backgroundColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = MaterialTheme.colors.onPrimary,
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
        Icon(
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.Transparent)
                .clickable {
                    onBackClicked()
                }
                .padding(Dimension.elevation)
                .mirror(),
            imageVector = Icons.Rounded.KeyboardArrowLeft,
            tint = contentColor,
            contentDescription = "back",
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
                if(bottomNavDestinations.indexOf(it).plus(1) == bottomNavDestinations.size.div(2)){
                    Spacer(modifier = Modifier.width(Dimension.smIcon))
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = -(Dimension.md.plus(Dimension.mdIcon)).div(2))
                .border(
                    width = Dimension.sm,
                    color = MaterialTheme.colors.background,
                    shape = CircleShape,
                )
                .padding(Dimension.sm)
        ) {
            DrawableButton(
                painter = painterResource(id = R.drawable.ic_search),
                backgroundColor = MaterialTheme.colors.primary,
                iconSize = Dimension.mdIcon,
                iconTint = MaterialTheme.colors.onPrimary,
                onButtonClicked = {},
                shape = CircleShape,
                paddingValue = PaddingValues(Dimension.sm),
            )

        }
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
            .size(20.dp),
        tint = tint
    )
}