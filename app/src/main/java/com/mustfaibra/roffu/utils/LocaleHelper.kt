package com.mustfaibra.roffu.utils


import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import java.util.*

object LocaleHelper {
    fun applyLanguage(context: Context, language: String) {
        val locale = Locale(language)
        val res: Resources = context.resources
        val conf: Configuration = res.configuration
        Locale.setDefault(locale)
        conf.setLocale(locale)
        res.updateConfiguration(conf, null)
    }
}