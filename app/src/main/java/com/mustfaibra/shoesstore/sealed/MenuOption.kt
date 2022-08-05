package com.mustfaibra.shoesstore.sealed

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class MenuOption(
    @StringRes open val title: Int,
    @DrawableRes open val icon: Int? = null
){
    /** All the possible menu options goes here */
}
