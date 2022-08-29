package com.mustfaibra.roffu.utils


import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import timber.log.Timber

sealed class DimensionsHelper {
    object Height : DimensionsHelper()
    object Width : DimensionsHelper()

    /** A sealed class that define the operations that we use to compare the dimensions */
    sealed class DimensionOperator {
        object LessThan : DimensionOperator()
        object GreaterThan : DimensionOperator()
        object EqualTo : DimensionOperator()
    }

    /** A class to validate the dimension */
    class DimensionValidator(
        val dimension: DimensionsHelper,
        val operator: DimensionOperator,
        val value: Dp,
    ) {
        fun compare(width: Dp, height: Dp): Boolean {
            /** Check what we want to compare */
            return when (dimension) {
                is Width -> {
                    /** Check what operator that we want to use */
                    when (operator) {
                        is DimensionOperator.LessThan -> width < value
                        is DimensionOperator.GreaterThan -> width > value
                        is DimensionOperator.EqualTo -> width == value
                    }
                }
                is Height -> {
                    /** Check what operator that we want to use */
                    when (operator) {
                        is DimensionOperator.LessThan -> height < value
                        is DimensionOperator.GreaterThan -> height > value
                        is DimensionOperator.EqualTo -> height == value
                    }
                }
            }
        }
    }
}

@Composable
infix fun DimensionsHelper.smallerThan(value: Dp): Boolean {
    val screenSize = LocalContext.current.getScreenSize()
    return DimensionsHelper.DimensionValidator(
        dimension = this,
        operator = DimensionsHelper.DimensionOperator.LessThan,
        value = value
    ).compare(
        width = screenSize.width,
        height = screenSize.height,
    )
}

@Composable
infix fun DimensionsHelper.largerThan(value: Dp): Boolean {
    val screenSize = LocalContext.current.getScreenSize()
    return DimensionsHelper.DimensionValidator(
        dimension = this,
        operator = DimensionsHelper.DimensionOperator.GreaterThan,
        value = value
    ).compare(
        width = screenSize.width,
        height = screenSize.height,
    )
}

@Composable
infix fun DimensionsHelper.equalTo(value: Dp): Boolean {
    val screenSize = LocalContext.current.getScreenSize()
    return DimensionsHelper.DimensionValidator(
        dimension = this,
        operator = DimensionsHelper.DimensionOperator.EqualTo,
        value = value
    ).compare(
        width = screenSize.width,
        height = screenSize.height,
    )
}

@Composable
fun Context.getScreenSize(): Size {
    val currentScreenWidth = this.resources.displayMetrics.widthPixels.getDp()
    val currentScreenHeight = this.resources.displayMetrics.heightPixels.getDp()
    return Size(width = currentScreenWidth, height = currentScreenHeight)
}

data class Size(
    val width: Dp,
    val height: Dp,
)

/** An extension function that is used to convert the px as Int values to a valid Dp */
@Composable
fun Int.getDp() : Dp{
    val px = this
    with(LocalDensity.current){
        Timber.d("density is ${this.density}")
        return px.toDp()
    }
}


/** An extension function that is used to convert the px as float values to a valid Dp */
@Composable
fun Float.getDp() : Dp{
    val px = this
    with(LocalDensity.current){
        Timber.d("density is ${this.density}")
        return px.toDp()
    }
}

val LocalScreenSize = compositionLocalOf<Size>{ error("Screen size is not specified") }