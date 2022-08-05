package com.mustfaibra.shoesstore.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.mustfaibra.shoesstore.sealed.UiState
import com.mustfaibra.shoesstore.ui.theme.Dimension

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(Dimension.pagePadding)
            .verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(Dimension.pagePadding)
    ) {
        val uiState by remember { homeViewModel.uiState }
        when (uiState) {
            is UiState.Idle -> {}
            is UiState.Loading -> {}
            is UiState.Success -> {}
            is UiState.Error -> {}
        }
    }
}
