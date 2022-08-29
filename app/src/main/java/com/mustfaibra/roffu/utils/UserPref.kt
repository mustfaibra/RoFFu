package com.mustfaibra.roffu.utils

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.mustfaibra.roffu.models.User

object UserPref {
    private val _user = mutableStateOf<User?>(null)
    val user: State<User?> = _user

    fun updateUser(user: User){
        _user.value = user
    }
}