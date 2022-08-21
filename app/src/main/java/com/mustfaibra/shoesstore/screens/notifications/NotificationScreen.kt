package com.mustfaibra.shoesstore.screens.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.mustfaibra.shoesstore.R
import com.mustfaibra.shoesstore.sealed.UiState
import com.mustfaibra.shoesstore.ui.theme.Dimension

@Composable
fun NotificationScreen(
    notificationViewModel: NotificationViewModel = hiltViewModel(),
) {
    val uiState by remember { notificationViewModel.uiState }
    LazyColumn(
        modifier = Modifier.background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.spacedBy(Dimension.pagePadding),
        contentPadding = PaddingValues(vertical = Dimension.pagePadding),
    ) {
        when (uiState) {
            is UiState.Loading -> {}
            is UiState.Success -> {
                item {
                    Text(
                        text = stringResource(id = R.string.bookmarks),
                        style = MaterialTheme.typography.h3,
                    )
                }
            }
            is UiState.Idle -> {}
            is UiState.Error -> {}
        }
    }
}