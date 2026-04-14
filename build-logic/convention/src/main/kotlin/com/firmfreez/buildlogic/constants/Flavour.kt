package com.firmfreez.buildlogic.constants


enum class Flavour(val value: String, val apkName: String) {
    DEV(value = "dev", apkName = "dev"),
    PROD(value = "prod", apkName = "prod");

    companion object {
        @OptIn(ExperimentalStdlibApi::class)
        fun getByName(name: String): Flavour? = values().find { it.value == name }
    }
}

const val FLAVOUR_DIMENSION_NAME = "VARIANT"
const val FLAVOUR_VARIANT_FIELD = "VARIANT"
