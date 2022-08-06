package com.mustfaibra.shoesstore.screens.home

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.mustfaibra.shoesstore.R
import com.mustfaibra.shoesstore.components.CustomInputField
import com.mustfaibra.shoesstore.models.Advertisement
import com.mustfaibra.shoesstore.models.Manufacturer
import com.mustfaibra.shoesstore.sealed.UiState
import com.mustfaibra.shoesstore.ui.theme.Dimension
import com.mustfaibra.shoesstore.ui.theme.StoreTheme
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val advertisementsUiState by remember { homeViewModel.homeAdvertisementsUiState }
    val searchQuery by remember { homeViewModel.searchQuery }

    /** Now we configure the pager to auto scroll each 2 seconds, using Handler */
    val mainHandler = Handler(Looper.getMainLooper())
    val autoPagerScrollCallback = remember {
        object : Runnable {
            override fun run() {
                /** Handle where to scroll */
                val currentPage = pagerState.currentPage
                val pagesCount = pagerState.pageCount
                Timber.d("Current pager page is $currentPage and count is $pagesCount")
                when {
                    currentPage < (pagesCount - 1) -> {
                        /** go to next page */
                        scope.launch {
                            pagerState.animateScrollToPage(currentPage.inc())
                        }
                    }
                    else -> {
                        /** Start from beginning */
                        scope.launch {
                            pagerState.animateScrollToPage(0)
                        }
                    }
                }
                mainHandler.postDelayed(this, 2000)
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        mainHandler.post(autoPagerScrollCallback)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.spacedBy(Dimension.pagePadding),
    ) {


        when (advertisementsUiState) {
            is UiState.Idle -> {}
            is UiState.Loading -> {
            }
            is UiState.Success -> {
                /** Search field section */
                item {
                    SearchField(
                        value = searchQuery,
                        onValueChange = {
                            homeViewModel.updateSearchInputValue(it)
                        },
                        onFocusChange = {

                        },
                        onImeActionClicked = {
                            /** We should run the search now */
                        }
                    )
                }
                /** Advertisements section */
                item {
                    AdvertisementsPager(
                        pagerState = pagerState,
                        advertisements = listOf(
                            Advertisement(1, R.drawable.air_huarache_gold_black_ads, 1, 0),
                            Advertisement(2, R.drawable.pegasus_trail_gortex_ads, 2, 0),
                            Advertisement(3, R.drawable.blazer_low_black_ads, 3, 0),
                        ),
                        onAdvertiseClicked = {}
                    )
                }
                /** Shoes companies row */
                item {
                    ManufacturersSection()
                }
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun SearchField(
    value: String,
    onValueChange: (value: String) -> Unit,
    onFocusChange: (hadFocus: Boolean) -> Unit,
    onImeActionClicked: KeyboardActionScope.() -> Unit,
) {
    CustomInputField(
        modifier = Modifier
            .padding(horizontal = Dimension.pagePadding)
            .fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        placeholder = "What are you looking for?",
        textStyle = MaterialTheme.typography.caption.copy(fontWeight = FontWeight.Medium),
        padding = PaddingValues(
            horizontal = Dimension.pagePadding,
            vertical = Dimension.pagePadding.times(0.7f),
        ),
        backgroundColor = MaterialTheme.colors.surface,
        textColor = MaterialTheme.colors.onBackground,
        imeAction = ImeAction.Search,
        shape = MaterialTheme.shapes.large,
        leadingIcon = {
            Icon(
                modifier = Modifier
                    .padding(end = Dimension.pagePadding.div(2))
                    .size(Dimension.mdIcon.times(0.7f)),
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                tint = MaterialTheme.colors.onBackground.copy(alpha = 0.4f),
            )
        },
        onFocusChange = onFocusChange,
        onKeyboardActionClicked = onImeActionClicked,
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AdvertisementsPager(
    pagerState: PagerState,
    advertisements: List<Advertisement>,
    onAdvertiseClicked: (advertisement: Advertisement) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Dimension.pagePadding.div(2)),
    ) {
        /** Horizontal pager section */
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth(),
            count = advertisements.size,
            state = pagerState,
            itemSpacing = Dimension.pagePadding.times(2),
            contentPadding = PaddingValues(horizontal = Dimension.pagePadding),
        ) {
            val advertisement = advertisements[this.currentPage]
            Image(
                painter = rememberImagePainter(data = advertisement.image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f)
                    .clip(MaterialTheme.shapes.medium)
                    .clickable(
                        indication = null,
                        interactionSource = MutableInteractionSource(),
                        onClick = { onAdvertiseClicked(advertisement) }
                    ),
                contentScale = ContentScale.Crop,
            )
        }
        /** Horizontal pager indicators */
        LazyRow(
            contentPadding = PaddingValues(horizontal = Dimension.pagePadding.times(2)),
            horizontalArrangement = Arrangement.spacedBy(Dimension.sm),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items(pagerState.pageCount) { index ->
                Box(
                    modifier = Modifier
                        .width(
                            if (pagerState.currentPage == index) Dimension.sm.times(3)
                            else Dimension.sm
                        )
                        .height(Dimension.sm)
                        .clip(CircleShape)
                        .background(
                            if (pagerState.currentPage == index) MaterialTheme.colors.secondary
                            else MaterialTheme.colors.secondary.copy(alpha = 0.2f)
                        )
                )
            }
        }
    }
}


@Composable
fun ManufacturersSection() {

}

@Composable
fun Previewer() {
    StoreTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            verticalArrangement = Arrangement.spacedBy(Dimension.pagePadding),
        ) {

        }
    }
}
