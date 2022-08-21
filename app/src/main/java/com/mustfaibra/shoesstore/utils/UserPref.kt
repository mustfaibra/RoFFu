package com.mustfaibra.shoesstore.utils

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.mustfaibra.shoesstore.models.User

object UserPref {
    private val _user = mutableStateOf<User?>(null)
    val user: State<User?> = _user

    fun updateUser(user: User){
        _user.value = user
    }
}