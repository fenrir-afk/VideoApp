package com.example.furniturestore.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object VideoGraph: Route

    @Serializable
    data object VideoList: Route

    @Serializable
    data object VideoFullScreen: Route
}