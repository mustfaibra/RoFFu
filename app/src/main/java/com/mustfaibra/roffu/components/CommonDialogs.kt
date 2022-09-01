package com.mustfaibra.roffu.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
                .background(background)
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
                style = MaterialTheme.typography.body1
                    .copy(color = titleColor),
            )
        }
    }
}