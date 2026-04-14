package com.firmfreez.buildlogic.extensions

import org.gradle.kotlin.dsl.DependencyHandlerScope

internal fun DependencyHandlerScope.api(dependency: Any) {
    add("api", dependency)
}


internal fun DependencyHandlerScope.implementation(dependency: Any) {
    add("implementation", dependency)
}

internal fun DependencyHandlerScope.debugImplementation(dependency: Any) {
    add("debugImplementation", dependency)
}

internal fun DependencyHandlerScope.testImplementation(dependency: Any) {
    add("testImplementation", dependency)
}

internal fun DependencyHandlerScope.androidTestImplementation(dependency: Any) {
    add("androidTestImplementation", dependency)
}

internal fun DependencyHandlerScope.ksp(dependency: Any) {
    add("ksp", dependency)
}
