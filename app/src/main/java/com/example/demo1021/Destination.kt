package com.example.demo1021

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink

/**
 * Contract for information needed on every Rally navigation destination
 */

interface Destination {
    val icon: ImageVector
    val route: String
}

/**
 * Rally app navigation destinations
 */
object Offer : Destination {
    override val icon = Icons.Filled.Wallet
    override val route = "offer"
}

object Task : Destination {
    override val icon = Icons.Filled.Task
    override val route = "task"
}

object Timer : Destination {
    override val icon = Icons.Filled.AccessTime
    override val route = "timer"
}

object Record : Destination {
    override val icon = Icons.Filled.Print
    override val route = "record"
}
// Screens to be displayed in the top RallyTabRow
val MyTabRowScreens = listOf(Offer, Task, Timer, Record)
