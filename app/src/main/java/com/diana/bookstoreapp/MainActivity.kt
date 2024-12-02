package com.diana.bookstoreapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import com.diana.bookstoreapp.ui.theme.login.LoginScreen
import com.diana.bookstoreapp.ui.theme.login.data.LoginScreenObject
import com.diana.bookstoreapp.ui.theme.main_screen.MainScreen
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.diana.bookstoreapp.ui.theme.add_book_screen.AddBookScreen
import com.diana.bookstoreapp.ui.theme.add_book_screen.data.AddScreenObject
import com.diana.bookstoreapp.ui.theme.login.data.MainScreenDataObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = LoginScreenObject) {

                composable<LoginScreenObject> {
                    LoginScreen { navData ->
                        navController.navigate(navData)
                    }
                }

                composable<MainScreenDataObject> { navEntry ->
                    val navData = navEntry.toRoute<MainScreenDataObject>()
                    MainScreen(navData)
                }
                composable<AddScreenObject> { navEntry ->
                    AddBookScreen()
                }
            }
            }
        }

    }


