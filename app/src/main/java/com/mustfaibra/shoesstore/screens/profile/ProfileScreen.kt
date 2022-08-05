package com.mustfaibra.shoesstore.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.mustfaibra.shoesstore.sealed.UiState

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val uiState by remember { profileViewModel.uiState }
        when (uiState) {
            is UiState.Idle -> {}
            is UiState.Loading -> {}
            is UiState.Success -> {}
            is UiState.Error -> {}
        }
    }
}