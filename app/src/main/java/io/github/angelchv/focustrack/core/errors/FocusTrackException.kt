package io.github.angelchv.focustrack.core.errors

import androidx.annotation.StringRes

/**
 * Base class for exceptions used throughout the FocusTrack application.
 *
 * @param messageResId Resource ID pointing to a localized error message.
 * @param originalError The original exception that caused this error, if available.
 */
open class FocusTrackException(
    @StringRes val messageResId: Int,
    val originalError: Throwable? = null,
) : Exception()