package com.mustfaibra.shoesstore.sealed

import androidx.annotation.StringRes
import com.mustfaibra.shoesstore.R

sealed class Language(
    val code: String,
    @StringRes val title: Int,
){
    object Arabic: Language(code = "ar",title = R.string.arabic)
    object English: Language(code = "en",title = R.string.english)
}