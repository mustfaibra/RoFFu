package com.mustfaibra.roffu.screens.orderhistory

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.mustfaibra.roffu.R
import com.mustfaibra.roffu.components.SecondaryTopBar
import com.mustfaibra.roffu.models.Location
import com.mustfaibra.roffu.models.PaymentProvider
import com.mustfaibra.roffu.sealed.UiState
import com.mustfaibra.roffu.ui.theme.Dimension

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OrdersHistoryScreen(
    ordersHistoryViewModel: OrdersHistoryViewModel = hiltViewModel(),
    onBackRequested: () -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        ordersHistoryViewModel.getOrders()
    }
    val orders = ordersHistoryViewModel.orders
    val historyUiState by remember { ordersHistoryViewModel.historyUiState }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.spacedBy(Dimension.pagePadding),
        contentPadding = PaddingValues(bottom = Dimension.pagePadding)
    ) {
        stickyHeader {
            SecondaryTopBar(
                title = stringResource(id = R.string.orders_history),
                onBackClicked = onBackRequested
            )
        }
        when (historyUiState) {
            is UiState.Loading -> {}
            is UiState.Success -> {
                items(orders) { order ->
                    OrderItemLayout(
                        orderedAt = order.order.createdAt,
                        total = "$${order.order.total}",
                        isDelivered = order.order.isDelivered,
                        location = order.location,
                        payment = order.orderPayment.userPaymentProviderDetails.paymentProvider,
                        onOrderClicked = {},
                    )

                }
            }
            is UiState.Error -> {}
            else -> {}
        }
    }
}

@Composable
fun OrderItemLayout(
    orderedAt: String,
    total: String,
    isDelivered: Boolean,
    location: Location,
    payment: PaymentProvider,
    onOrderClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = Dimension.pagePadding)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colors.surface)
            .clickable { onOrderClicked() }
            .padding(Dimension.pagePadding),
        verticalArrangement = Arrangement.spacedBy(Dimension.sm),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            IconWithText(
                modifier = Modifier.weight(1f),
                icon = R.drawable.ic_map_pin,
                text = "${location.address}, ${location.city} - ${location.country}",
                textStyle = MaterialTheme.typography.body1,
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_check_square),
                contentDescription = null,
                tint = if (isDelivered) MaterialTheme.colors.primary
                else MaterialTheme.colors.secondary,
            )
        }
        IconWithText(icon = R.drawable.ic_calendar, text = orderedAt)

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            IconWithText(
                icon = payment.icon,
                text = stringResource(payment.title),
                textStyle = MaterialTheme.typography.caption
                    .copy(fontWeight = FontWeight.Medium),
                iconTint = Color.Unspecified,
            )
            Text(
                text = total,
                style = MaterialTheme.typography.body1,
            )
        }
    }
}

@Composable
fun IconWithText(
    modifier: Modifier = Modifier,
    icon: Int,
    iconTint: Color = MaterialTheme.colors.primary,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.caption,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(Dimension.sm),
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(Dimension.smIcon),
            tint = iconTint,
        )
        Text(
            text = text,
            style = textStyle
        )
    }
}