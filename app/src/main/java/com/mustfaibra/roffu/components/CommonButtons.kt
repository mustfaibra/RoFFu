package com.mustfaibra.roffu.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.mustfaibra.roffu.ui.theme.Dimension

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
    iconTint: Color = Color.Unspecified,
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
                    if (enabled) onButtonClicked()
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
            disabledBackgroundColor = buttonColor.copy(alpha = 0.7f),
            disabledContentColor = contentColor.copy(alpha = 0.7f),
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