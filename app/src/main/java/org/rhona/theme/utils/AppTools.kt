package org.rhona.theme.utils

import android.content.Context

class AppTools {

    companion object {
        @JvmStatic
        fun getStatusBarHeight(context: Context): Int {
            var statusBarHeight = 0
            val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
            }
            return statusBarHeight
        }
    }
}