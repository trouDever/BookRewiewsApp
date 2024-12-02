package com.diana.bookstoreapp.ui.theme.main_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.diana.bookstoreapp.ui.theme.login.data.MainScreenDataObject
import com.diana.bookstoreapp.ui.theme.main_screen.bottom_menu.BottomMenu
import com.diana.bookstoreapp.ui.theme.main_screen.bottom_menu.BottomMenuItem


@Composable
fun MainScreen(navData: MainScreenDataObject) {
    val drawerState = rememberDrawerState(DrawerValue.Open)
    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = Modifier.fillMaxWidth(),
        drawerContent = {
            Column(Modifier.fillMaxSize(0.7f)) {
                DrawerHeader(navData.email)
                DrawerBody()
            }
    }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomMenu()
            }
        ) {

        }
    }
}