package com.firmfreez.buildlogic.constants

import com.firmfreez.buildlogic.constants.BuildType.DEBUG
import com.firmfreez.buildlogic.constants.BuildType.RELEASE


/**
 * This object contains constants related to the build types of the Android application.
 *
 * @property DEBUG The name of the debug build type.
 * @property RELEASE The name of the release build type.
 */
enum class BuildType(val value: String) {

    DEBUG("debug"),
    RELEASE("release")
}
