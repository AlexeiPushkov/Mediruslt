package com.diplom.mediresult.presentation.nvgraph

sealed class Route(
    val route: String
) {
    object OnBoardingScreen : Route(route = "OnBoarding")

    object LoginScreen : Route(route = "Login")

    object SinUpScreen : Route(route = "SinUp")

    object CheckCodeScreen : Route(route = "CheckCode")

    object MainScreen : Route(route = "Main")

    object FormOrder : Route(route = "FormOrder")

    object OrderSuccess : Route(route = "OrderSuccess")

    object CheckScreen : Route(route = "Check")

    object SupportScreen : Route(route = "Support")

    object PDFScreen : Route(route = "PDF")
}