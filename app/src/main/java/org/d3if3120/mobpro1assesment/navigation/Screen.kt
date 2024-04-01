package org.d3if3120.mobpro1assesment.navigation

sealed class Screen (val route: String) {
    data object Home: Screen("mainScreen")

    data object About: Screen("aboutScreen")
}