package com.mustfaibra.shoesstore.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.mustfaibra.shoesstore.models.User
import com.mustfaibra.shoesstore.sealed.UiState
import com.mustfaibra.shoesstore.ui.theme.Dimension

@Composable
fun ProfileScreen(
    user: User,
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val uiState by remember { profileViewModel.uiState }

        when (uiState) {
            is UiState.Idle -> {}
            is UiState.Loading -> {}
            is UiState.Success -> {
                /** Header section */
                ProfileHeaderSection(
                    image = user.profile,
                    name = user.name,
                    email = user.email,
                    phone = user.phone,
                )

            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun ProfileHeaderSection(image: Int?, name: String, email: String?, phone: String?) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimension.pagePadding),
    ) {
        Image(
            painter = rememberImagePainter(data = image) {
                crossfade(true)
            },
            contentDescription = null,
            modifier = Modifier
                .size(Dimension.xlIcon.times(2))
                .clip(CircleShape),
        )
        Text(
            text = name,
            style = MaterialTheme.typography.h3,
        )
        Text(
            text = email ?: "",
            style = MaterialTheme.typography.h3,
        )
        Text(
            text = phone ?: "",
            style = MaterialTheme.typography.h3,
        )

    }
}
