package com.practice.daily_task

import android.window.SplashScreen
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.practice.daily_task.database.TodoViewModel
import com.practice.daily_task.routes.DetailScreen
import com.practice.daily_task.todoUI.AddNewTask
import com.practice.daily_task.todoUI.DetailScreen
import com.practice.daily_task.todoUI.HomeScreen
import com.practice.daily_task.todoUI.ProfilePage

@Composable

fun MyAppNavigation(viewModel: TodoViewModel) {

    val navController = rememberNavController()
    NavHost(navController= navController, startDestination = routes.HomeScreen, builder = {

        composable( routes.HomeScreen){
            HomeScreen(navController, viewModel = viewModel)
        }


        composable(
         route="{routes.DetailScreen}/{todoId}",
         arguments = listOf(navArgument("todoId"){type = NavType.IntType})
    ){   backStackEntry ->
            val todoId = backStackEntry.arguments?.getInt("todoId") ?: -1
            DetailScreen(todoId = todoId,viewModel = viewModel,navController )
        }


        composable( routes.AddTask){
            AddNewTask(navController, viewModel = viewModel)
        }
        composable( routes.Profile){
           ProfilePage(navController,
               viewModel = viewModel,
           )
        }
    })

}