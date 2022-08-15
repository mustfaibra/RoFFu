package com.mustfaibra.shoesstore.components


import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mustfaibra.shoesstore.ui.theme.Dimension

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.primary,
    iconTint: Color,
    onButtonClicked: () -> Unit,
    icon: ImageVector,
    iconSize: Dp = Dimension.mdIcon,
    shape: Shape = MaterialTheme.shapes.medium,
    elevation: Dp = Dimension.zero,
    paddingValue: PaddingValues = PaddingValues(Dimension.xs),
) {
    Box(
        modifier = modifier
            .shadow(elevation = elevation, shape = shape)
            .clip(shape)
            .background(backgroundColor)
            .clickable {
                onButtonClicked()
            }
            .padding(paddingValues = paddingValue)
    ) {
        Icon(
            modifier = Modifier
                .size(iconSize)
                .align(Alignment.Center),
            imageVector = icon,
            contentDescription = "icon",
            tint = iconTint
        )
    }
}

@Composable
fun DrawableButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    backgroundColor: Color = MaterialTheme.colors.primary,
    iconTint: Color,
    onButtonClicked: () -> Unit,
    painter: Painter,
    shape: Shape = MaterialTheme.shapes.medium,
    iconSize: Dp = Dimension.mdIcon,
    elevation: Dp = Dimension.zero,
    paddingValue: PaddingValues = PaddingValues(Dimension.xs),
) {
    Box(
        modifier = modifier
            .shadow(elevation = elevation, shape = shape)
            .clip(shape)
            .background(backgroundColor)
            .clickable(
                onClick = {
                    if(enabled) onButtonClicked()
                }
            )
            .padding(paddingValues = paddingValue)
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.Center)
                .size(iconSize),
            painter = painter,
            contentDescription = "icon next",
            tint = iconTint,
        )
    }
}

@Composable
fun DrawableButtonWithBadge(
    modifier: Modifier = Modifier,
    size: Dp = Dimension.md,
    backgroundColor: Color = MaterialTheme.colors.primary,
    iconTint: Color,
    onButtonClicked: () -> Unit,
    painter: Int,
    shape: Shape = MaterialTheme.shapes.medium,
    badge: @Composable (modifier: Modifier) -> Unit = {},
) {
    Box(
        modifier = modifier
            .shadow(elevation = Dimension.elevation, shape = shape)
            .clip(shape)
            .background(backgroundColor)
            .clickable {
                onButtonClicked()
            }
    ) {
        Icon(
            modifier = Modifier
                .padding(Dimension.xs)
                .size(size)
                .align(Alignment.Center),
            painter = painterResource(id = painter),
            contentDescription = "icon next",
            tint = iconTint
        )
        /** show the badge, it will appear only if it got passed */
        badge(
            modifier = Modifier.align(Alignment.TopEnd)
        )
    }
}

@Composable
fun BrushedIconButton(
    modifier: Modifier = Modifier,
    brush: Brush = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colors.primary,
            MaterialTheme.colors.secondary,
        ),
    ),
    iconTint: Color,
    onButtonClicked: () -> Unit,
    icon: ImageVector,
    shape: Shape = MaterialTheme.shapes.medium,
) {
    Box(
        modifier = modifier
            .shadow(elevation = Dimension.elevation,
                shape = shape)
            .clip(shape = shape)
            .background(brush = brush)
            .clickable {
                onButtonClicked()
            }
            .padding(Dimension.xs)
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.Center),
            imageVector = icon,
            contentDescription = "icon next",
            tint = iconTint
        )
    }
}

@Composable
fun BrushedCustomButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    brush: Brush = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colors.primary,
            MaterialTheme.colors.primaryVariant,
        ),
    ),
    shape: Shape = MaterialTheme.shapes.medium,
    contentColor: Color,
    padding: PaddingValues = PaddingValues(horizontal = Dimension.sm, vertical = Dimension.sm),
    text: String,
    leadingIcon: @Composable () -> Unit = {},
    trailingIcon: @Composable () -> Unit = {},
    onButtonClicked: () -> Unit,
) {
    Button(
        modifier = modifier
            .shadow(
                elevation = Dimension.elevation,
                shape = shape,
            )
            .clip(shape)
            .background(brush = brush),
        onClick = onButtonClicked,
        shape = shape,
        enabled = enabled,
        contentPadding = padding,
        elevation = ButtonDefaults.elevation(
            defaultElevation = Dimension.zero,
            pressedElevation = Dimension.zero,
        ),
    ) {
        leadingIcon()
        Text(
            text = text,
            style = MaterialTheme.typography.button,
            color = contentColor,
        )
        /** Add trailing icon */
        trailingIcon()
    }
}

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    elevationEnabled: Boolean = true,
    buttonColor: Color,
    contentColor: Color,
    padding: PaddingValues = PaddingValues(vertical = Dimension.sm, horizontal = Dimension.sm),
    shape: Shape = MaterialTheme.shapes.medium,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.button,
    leadingIcon: @Composable () -> Unit = {},
    trailingIcon: @Composable () -> Unit = {},
    onButtonClicked: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = {
            onButtonClicked()
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = buttonColor,
            contentColor = contentColor,
            disabledBackgroundColor = MaterialTheme.colors.secondaryVariant.copy(alpha = 0.2f),
            disabledContentColor = MaterialTheme.colors.background,
        ),
        enabled = enabled,
        shape = shape,
        contentPadding = padding,
        elevation = if (elevationEnabled) ButtonDefaults.elevation()
        else ButtonDefaults.elevation(
            defaultElevation = Dimension.zero,
            pressedElevation = Dimension.elevation
        ),
    ) {
        leadingIcon()
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = textStyle,
            color = contentColor,
        )
        /** Add trailing icon */
        trailingIcon()
    }
}

@Composable
fun CloseButton(
    modifier: Modifier = Modifier,
    onClose: () -> Unit,
) {
    Icon(
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colors.secondary)
            .clickable { onClose() }
            .padding(Dimension.xs)
            .size(Dimension.lg),
        imageVector = Icons.Rounded.Close,
        contentDescription = "close",
    )
}

/** Color & Rotation are reactive */
@Composable
fun ToggleableReactiveIcon(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent,
    iconWhenTrue: ImageVector,
    iconWhenFalse: ImageVector,
    iconTintWhenTrue: Color = MaterialTheme.colors.primary,
    iconTintWhenFalse: Color = MaterialTheme.colors.secondary,
    currentState: Boolean,
    iconSize: Dp = 20.dp,
    onStateChanges: () -> Unit,
) {
    val transition =
        updateTransition(targetState = currentState, label = "icon state")

    val icon = if (currentState) iconWhenTrue else iconWhenFalse
    val tint by transition.animateColor(label = "tint") {
        when (it) {
            true -> iconTintWhenTrue
            false -> iconTintWhenFalse
        }
    }
    val rotation by transition.animateFloat(label = "rotation") { if (it) 360f else -360f }
    Icon(
        imageVector = icon,
        contentDescription = null,
        modifier = modifier
            .rotate(rotation)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable { onStateChanges() }
            .padding(Dimension.elevation)
            .size(iconSize),
        tint = tint
    )
}