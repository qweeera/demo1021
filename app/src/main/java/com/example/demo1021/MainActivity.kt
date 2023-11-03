package com.example.demo1021

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import com.example.demo1021.MyViewModel
import com.example.demo1021.MyViewModelFactory
import OfferScreen
import android.annotation.SuppressLint
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.demo1021.db.TaskApplication
import com.example.demo1021.db.TimeDbItem
import com.example.demo1021.record.RecordScreen
import com.example.demo1021.task.TaskScreen
import com.example.demo1021.task.TimeControl
import com.example.demo1021.task.formatTime
import com.example.demo1021.time.TimeScreen
import com.example.demo1021.ui.theme.demo1021Theme
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        val myViewModel by viewModels<MyViewModel> {
            MyViewModelFactory(
                (application as TaskApplication).database.offerDao(),
                (application as TaskApplication).database.timeDao(),
                (application as TaskApplication).database.labelDao(),
            )
        }

        setContent {
            demo1021Theme {
                val navController = rememberNavController()
                var currentScreen: Destination by remember { mutableStateOf(Offer) }

                Scaffold(
                    bottomBar = {
                        BottomNavigation(
                            allScreens = MyTabRowScreens,
                            onTabSelected = { newScreen ->
                                navController.navigate(newScreen.route)
                            },
                            currentScreen = currentScreen,
                        )
                    }
                ) {PaddingValues->
                    NavigationGraph(navController = navController, myViewModel, PaddingValues)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NavigationGraph(navController: NavHostController, myViewModel: MyViewModel, paddingValues:PaddingValues) {
    NavHost(navController, startDestination = Offer.route) {
        composable(route = Offer.route) {
            OfferScreen(myViewModel = myViewModel, paddingValues = paddingValues)
        }
        composable(route = Task.route) {
            TaskScreen(myViewModel = myViewModel, paddingValues = paddingValues)
        }
        composable(route = Timer.route) {
            TimeScreen(myViewModel = myViewModel, paddingValues = paddingValues)
        }
        composable(route = Record.route) {
            RecordScreen(myViewModel = myViewModel, paddingValues = paddingValues)
        }
    }
}



@Composable
fun BottomNavigation(
    allScreens: List<Destination>,
    onTabSelected: (Destination) -> Unit,
    currentScreen: Destination,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier.height(50.dp)
    ) {
        NavigationBarItem(
            selected = currentScreen == allScreens[0],
            onClick = { onTabSelected(allScreens[0]) },
            icon = {
                Icon(
                    imageVector = allScreens[0].icon,
                    contentDescription = null
                )
            }
        )
        NavigationBarItem(
            selected = currentScreen == allScreens[1],
            onClick = { onTabSelected(allScreens[1]) },
            icon = {
                Icon(
                    imageVector = allScreens[1].icon,
                    contentDescription = null
                )
            }
        )
        NavigationBarItem(
            selected = currentScreen == allScreens[2],
            onClick = { onTabSelected(allScreens[2]) },
            icon = {
                Icon(
                    imageVector = allScreens[2].icon,
                    contentDescription = null
                )
            }
        )
        NavigationBarItem(
            selected = currentScreen == allScreens[3],
            onClick = { onTabSelected(allScreens[3]) },
            icon = {
                Icon(
                    imageVector = allScreens[3].icon,
                    contentDescription = null
                )
            }
        )
    }
}
