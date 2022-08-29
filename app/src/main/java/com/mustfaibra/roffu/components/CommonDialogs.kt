package com.mustfaibra.roffu.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mustfaibra.roffu.ui.theme.Dimension

@Composable
fun SimpleLoadingDialog(
    title: String,
    background: Color = MaterialTheme.colors.surface,
    titleColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
) {
    Dialog(
        onDismissRequest = { /*TODO*/ },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colors.surface)
                .padding(Dimension.pagePadding),
            horizontalArrangement = Arrangement.spacedBy(Dimension.pagePadding.times(2)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                strokeWidth = Dimension.elevation,
            )
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.body1,
            )
        }
    }
}

@Composable
fun ConfirmationDialog(
    modifier: Modifier = Modifier,
    dialogShape: Shape = MaterialTheme.shapes.medium,
    background: Color = MaterialTheme.colors.surface,
    paddingValues: PaddingValues = PaddingValues(Dimension.sm),
    messageTextStyle: TextStyle = MaterialTheme.typography.body1,
    messagePaddingFromOptions: Dp = Dimension.pagePadding,
    message: String,
    cancelText: String,
    confirmText: String,
    onConfirmed: () -> Unit,
    onCanceled: () -> Unit,
) {
    Dialog(
        onDismissRequest = onCanceled,
    ) {
        Column(
            modifier = modifier
                .clip(shape = dialogShape)
                .fillMaxWidth()
                .background(color = background)
                .padding(paddingValues = paddingValues),
            verticalArrangement = Arrangement.spacedBy(messagePaddingFromOptions)
        ) {
            Text(text = message, textAlign = TextAlign.Center, style = messageTextStyle)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CustomButton(
                    text = cancelText,
                    onButtonClicked = onCanceled,
                    buttonColor = Color.Transparent,
                    elevationEnabled = false,
                    contentColor = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
                    padding = PaddingValues(horizontal = Dimension.sm,
                        vertical = Dimension.pagePadding)
                )
                CustomButton(
                    text = confirmText,
                    onButtonClicked = onConfirmed,
                    buttonColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onPrimary,
                    padding = PaddingValues(horizontal = Dimension.sm,
                        vertical = Dimension.pagePadding)
                )
            }
        }
    }
}

