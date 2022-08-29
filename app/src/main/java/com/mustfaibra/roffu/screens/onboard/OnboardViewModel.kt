package com.mustfaibra.roffu.screens.onboard


import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustfaibra.roffu.utils.APP_LAUNCHED
import com.mustfaibra.roffu.utils.dataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A View model with hiltViewModel annotation that is used to access this view model everywhere needed
 */
@HiltViewModel
class OnboardViewModel @Inject constructor() : ViewModel() {

    fun updateLaunchState(context: Context) {
        /** Updating the flag that indicate to app being launched before */
        viewModelScope.launch {
            context.dataStore.edit {
                it[APP_LAUNCHED] = true
            }
        }
    }

}