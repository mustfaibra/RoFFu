package com.mustfaibra.roffu.sealed

import androidx.annotation.StringRes
import com.mustfaibra.roffu.R

sealed class Error(@StringRes var title: Int, @StringRes var message: Int){
    object Network: Error(
        title = R.string.net_conn_err_title,
        message = R.string.net_conn_err_message,
    )
    object Empty: Error(
        title = R.string.no_avail_data_err_title,
        message = R.string.no_avail_data_err_body,
    )
    class Custom(
        title: Int,
        message: Int = R.string.unknown_err_body,
    ): Error(
        title = title,
        message = message,
    )
    object Unknown: Error(
        title = R.string.unknown_err_title,
        message = R.string.unknown_err_body,
    )
}
