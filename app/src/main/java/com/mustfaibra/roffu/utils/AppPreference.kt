package com.mustfaibra.roffu.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

/** Our preferences keys */
val APP_LANGUAGE = stringPreferencesKey("language")
val APP_LAUNCHED = booleanPreferencesKey("launched")
val LOGGED_USER_ID = intPreferencesKey("userId")

