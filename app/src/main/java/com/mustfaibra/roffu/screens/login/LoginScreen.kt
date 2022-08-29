package com.mustfaibra.roffu.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mustfaibra.roffu.R
import com.mustfaibra.roffu.components.CustomButton
import com.mustfaibra.roffu.components.CustomInputField
import com.mustfaibra.roffu.components.DrawableButton
import com.mustfaibra.roffu.sealed.UiState
import com.mustfaibra.roffu.ui.theme.Dimension

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    onUserAuthenticated: () -> Unit,
    onToastRequested: (message: String, color: Color) -> Unit,
) {
    val uiState by remember { loginViewModel.uiState }
    val emailOrPhone by remember { loginViewModel.emailOrPhone }
    val password by remember { loginViewModel.password }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimension.pagePadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.h1.copy(fontSize = 48.sp),
            color = MaterialTheme.colors.primary,
            fontFamily = FontFamily.Cursive,
        )
        Spacer(modifier = Modifier.height(Dimension.pagePadding.times(2)))
        /** Login info input section */
        CustomInputField(
            modifier = Modifier
                .shadow(
                    elevation = Dimension.elevation,
                    shape = MaterialTheme.shapes.large,
                )
                .fillMaxWidth(),
            value = emailOrPhone ?: "",
            onValueChange = {
                loginViewModel.updateEmailOrPhone(value = it.ifBlank { null })
            },
            placeholder = "Email or Phone ...",
            textStyle = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.Medium),
            padding = PaddingValues(
                horizontal = Dimension.pagePadding,
                vertical = Dimension.pagePadding.times(0.7f),
            ),
            backgroundColor = MaterialTheme.colors.surface,
            textColor = MaterialTheme.colors.onBackground,
            imeAction = ImeAction.Next,
            shape = MaterialTheme.shapes.large,
            leadingIcon = {
                Icon(
                    modifier = Modifier
                        .padding(end = Dimension.pagePadding.div(2))
                        .size(Dimension.mdIcon.times(0.7f)),
                    painter = painterResource(id = R.drawable.ic_profile_empty),
                    contentDescription = null,
                    tint = MaterialTheme.colors.onBackground.copy(alpha = 0.4f),
                )
            },
            onFocusChange = { },
            onKeyboardActionClicked = { },
        )
        Spacer(modifier = Modifier.height(Dimension.pagePadding))
        CustomInputField(
            modifier = Modifier
                .shadow(
                    elevation = Dimension.elevation,
                    shape = MaterialTheme.shapes.large,
                )
                .fillMaxWidth(),
            value = password ?: "",
            onValueChange = {
                loginViewModel.updatePassword(value = it.ifBlank { null })
            },
            placeholder = "Password ...",
            visualTransformation = PasswordVisualTransformation(),
            textStyle = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.Medium),
            padding = PaddingValues(
                horizontal = Dimension.pagePadding,
                vertical = Dimension.pagePadding.times(0.7f),
            ),
            backgroundColor = MaterialTheme.colors.surface,
            textColor = MaterialTheme.colors.onBackground,
            imeAction = ImeAction.Done,
            shape = MaterialTheme.shapes.large,
            leadingIcon = {
                Icon(
                    modifier = Modifier
                        .padding(end = Dimension.pagePadding.div(2))
                        .size(Dimension.mdIcon.times(0.7f)),
                    painter = painterResource(id = R.drawable.ic_lock),
                    contentDescription = null,
                    tint = MaterialTheme.colors.onBackground.copy(alpha = 0.4f),
                )
            },
            onFocusChange = { },
            onKeyboardActionClicked = { },
        )
        /** The login button */
        Spacer(modifier = Modifier.height(Dimension.pagePadding))
        CustomButton(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = if (uiState !is UiState.Loading) Dimension.elevation else Dimension.zero,
                    shape = MaterialTheme.shapes.large,
                ),
            shape = MaterialTheme.shapes.large,
            padding = PaddingValues(Dimension.pagePadding.div(2)),
            buttonColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary,
            text = stringResource(id = R.string.login),
            enabled = uiState !is UiState.Loading,
            textStyle = MaterialTheme.typography.button,
            onButtonClicked = {
                /** Handle the click event of the login button */
                loginViewModel.authenticateUser(
                    emailOrPhone = emailOrPhone ?: "",
                    password = password ?: "",
                    onAuthenticated = {
                        /** When user is authenticated, go home or back */
                        onUserAuthenticated()
                    },
                    onAuthenticationFailed = {
                        /** Do whatever you want when it failed */
                        onToastRequested("Make sure you fill the form!", Color.Red)
                    }
                )
            },
            leadingIcon = {
                if (uiState is UiState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(end = Dimension.pagePadding)
                            .size(Dimension.smIcon),
                        color = MaterialTheme.colors.onPrimary,
                        strokeWidth = Dimension.xs
                    )
                }
            }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimension.pagePadding),
            contentAlignment = Alignment.Center,
        ) {
            Divider()
            Text(
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .padding(horizontal = Dimension.pagePadding.div(2)),
                text = "Or using",
                style = MaterialTheme.typography.caption
                    .copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colors.onBackground.copy(alpha = 0.7f)
                    ),
            )
        }

        /** Another signing options */
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimension.pagePadding),
        ) {
            DrawableButton(
                paddingValue = PaddingValues(Dimension.sm),
                elevation = Dimension.elevation,
                painter = painterResource(id = R.drawable.ic_google),
                onButtonClicked = {},
                backgroundColor = MaterialTheme.colors.background,
                shape = MaterialTheme.shapes.medium,
                iconSize = Dimension.mdIcon.times(0.8f),
            )
            DrawableButton(
                paddingValue = PaddingValues(Dimension.sm),
                elevation = Dimension.elevation,
                painter = painterResource(id = R.drawable.ic_facebook),
                onButtonClicked = {},
                backgroundColor = MaterialTheme.colors.background,
                shape = MaterialTheme.shapes.medium,
                iconSize = Dimension.mdIcon.times(0.8f),
            )
            DrawableButton(
                paddingValue = PaddingValues(Dimension.sm),
                elevation = Dimension.elevation,
                painter = painterResource(id = R.drawable.ic_twitter),
                onButtonClicked = {},
                backgroundColor = MaterialTheme.colors.background,
                shape = MaterialTheme.shapes.medium,
                iconSize = Dimension.mdIcon.times(0.8f),
            )
            DrawableButton(
                paddingValue = PaddingValues(Dimension.sm),
                elevation = Dimension.elevation,
                painter = painterResource(id = R.drawable.ic_apple),
                onButtonClicked = {},
                backgroundColor = MaterialTheme.colors.background,
                shape = MaterialTheme.shapes.medium,
                iconSize = Dimension.mdIcon.times(0.8f),
            )
        }
        Divider(Modifier.padding(vertical = Dimension.pagePadding))
        CustomButton(
            modifier = Modifier,
            shape = MaterialTheme.shapes.large,
            elevationEnabled = false,
            padding = PaddingValues(
                horizontal = Dimension.pagePadding,
                vertical = Dimension.xs
            ),
            buttonColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.primary,
            text = stringResource(id = R.string.privacy_and_policies),
            enabled = uiState !is UiState.Loading,
            textStyle = MaterialTheme.typography.caption
                .copy(fontWeight = FontWeight.SemiBold),
            onButtonClicked = {
                /** Handle the click event of the policies & terms button */

            },
        )
    }
}