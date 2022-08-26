package com.mustfaibra.shoesstore.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.mustfaibra.shoesstore.R
import com.mustfaibra.shoesstore.components.DrawableButton
import com.mustfaibra.shoesstore.components.IconButton
import com.mustfaibra.shoesstore.models.User
import com.mustfaibra.shoesstore.sealed.Screen
import com.mustfaibra.shoesstore.ui.theme.Dimension

@Composable
fun ProfileScreen(
    user: User,
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {
    val generalOptions = remember {
        listOf(Screen.Settings, Screen.OrderHistory)
    }
    val personalOptions = remember {
        listOf(Screen.PrivacyPolicies, Screen.TermsConditions)
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = Dimension.pagePadding),
        verticalArrangement = Arrangement.spacedBy(Dimension.pagePadding),
    ) {
        item {
            Text(
                text = stringResource(id = R.string.your_profile),
                style = MaterialTheme.typography.button,
                color = MaterialTheme.colors.onBackground,
            )
        }
        /** Header section */
        item {
            ProfileHeaderSection(
                image = user.profile,
                name = user.name,
                email = user.email,
                phone = user.phone,
            )
        }
        /** Add virtual card section */
        item {
            Card(
                modifier = Modifier.clickable {  },
                shape = MaterialTheme.shapes.medium,
                backgroundColor = MaterialTheme.colors.secondary,
                contentColor = MaterialTheme.colors.onSecondary,
            ) {
                Column(
                    modifier = Modifier.padding(Dimension.pagePadding),
                    verticalArrangement = Arrangement.spacedBy(Dimension.md),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Add virtual card",
                            style = MaterialTheme.typography.button,
                        )
                        IconButton(
                            icon = Icons.Rounded.KeyboardArrowRight,
                            backgroundColor = MaterialTheme.colors.background,
                            iconTint = MaterialTheme.colors.onBackground,
                            onButtonClicked = {},
                            iconSize = Dimension.smIcon,
                            paddingValue = PaddingValues(Dimension.xs),
                            shape = MaterialTheme.shapes.medium,
                        )
                    }
                    Text(
                        text = "Virtual cards allow you to purchase products on the store.",
                        style = MaterialTheme.typography.body2,
                    )
                }
            }
        }
        /** General options */
        item {
            Text(
                text = "General",
                style = MaterialTheme.typography.body1,
            )
        }
        items(generalOptions) { option ->
            ProfileOptionItem(
                icon = option.icon,
                title = option.title,
                onOptionClicked = {},
            )
        }
        /** Personal options */
        item {
            Text(
                text = "Personal",
                style = MaterialTheme.typography.body1,
            )
        }
        items(personalOptions) { option ->
            ProfileOptionItem(
                icon = option.icon,
                title = option.title,
                onOptionClicked = {},
            )
        }
    }
}

@Composable
fun ProfileOptionItem(icon: Int?, title: Int?, onOptionClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .clip(CircleShape)
            .fillMaxWidth()
            .clickable { onOptionClicked() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimension.pagePadding),
    ) {
        DrawableButton(
            painter = rememberImagePainter(data = icon),
            backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.4f),
            iconTint = MaterialTheme.colors.primary,
            onButtonClicked = {},
            iconSize = Dimension.smIcon,
            paddingValue = PaddingValues(Dimension.md),
            shape = CircleShape,
        )
        title?.let {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colors.onBackground.copy(alpha = 0.7f),
            )
        }
        IconButton(
            icon = Icons.Rounded.KeyboardArrowRight,
            backgroundColor = MaterialTheme.colors.background,
            iconTint = MaterialTheme.colors.onBackground,
            onButtonClicked = {},
            iconSize = Dimension.smIcon,
            paddingValue = PaddingValues(Dimension.md),
            shape = CircleShape,
        )
    }
}

@Composable
fun ProfileHeaderSection(image: Int?, name: String, email: String?, phone: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimension.pagePadding),
    ) {
        Image(
            painter = rememberImagePainter(data = image) {
                crossfade(true)
            },
            contentDescription = null,
            modifier = Modifier
                .size(Dimension.xlIcon)
                .clip(CircleShape),
        )
        Column {
            Text(
                text = name,
                style = MaterialTheme.typography.h5,
            )
            Text(
                text = email ?: "",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground.copy(alpha = 0.7f),
            )
            Text(
                text = phone ?: "",
                style = MaterialTheme.typography.caption
                    .copy(fontWeight = FontWeight.Medium),
            )
        }
    }
}
