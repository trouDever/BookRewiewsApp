package com.diana.bookstoreapp.ui.theme.login

import android.util.Log
import com.diana.bookstoreapp.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.diana.bookstoreapp.ui.theme.login.data.MainScreenDataObject
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

@Composable
fun LoginScreen(
    onNavigateToMainScreen: (MainScreenDataObject) -> Unit
) {

    val auth = remember {
        Firebase.auth
    }
    val errorState = remember {
        mutableStateOf("")
    }
    val emailState = remember {
        mutableStateOf("")
    }
    val passwordState = remember {
        mutableStateOf("")
    }

    Image(painter = painterResource(
        id = R.drawable.book_store_bg),
        contentDescription = "BG",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 50.dp,
                end = 50.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Books Rewiews App",
            color = Color.Black,
            fontWeight = FontWeight.Light,
            fontSize = 25.sp
            )
        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "Logo",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        RoundedCornerTextField(
            text = emailState.value,
            label = "Логин"
        ) {
            emailState.value = it
        }

        Spacer(modifier = Modifier.height(10.dp))
        RoundedCornerTextField(
            text = passwordState.value,
            label = "Пароль"
        ) {
            passwordState.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        if (errorState.value.isNotEmpty()) {
            Text(
                text = errorState.value,
                color = Color.Red,
            )
        }
        LoginButton(text = "Вход") {
            signIn(
                auth,
                emailState.value,
                passwordState.value,
                onSignInSucess = {   navData ->
                    onNavigateToMainScreen(navData)
                },
                onSignInFailure = { error ->
                    errorState.value = error
                }
            )
         }
        LoginButton(text = "Регистрация") {
            signUp(
                auth,
                emailState.value,
                passwordState.value,
                onSignUpSucess = { navData ->
                    onNavigateToMainScreen(navData)

                },
                onSignUpFailure = { error ->
                    errorState.value = error
                }
            )
        }
    }
}

fun signUp(
    auth: FirebaseAuth,
    email: String,
    password: String,
    onSignUpSucess: (MainScreenDataObject) -> Unit,
    onSignUpFailure: (String) -> Unit
) {
    if (email.isBlank() || password.isBlank()) {
        onSignUpFailure("Логин и пароль не могут быть пустыми")
        return
    }

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) onSignUpSucess(
                MainScreenDataObject(
                    task.result.user?.uid!!,
                    task.result.user?.email!!,
                )
            )
        }
        .addOnFailureListener {
            onSignUpFailure(it.message ?: "Ошибка Регистрации")
        }
}

fun signIn(
    auth: FirebaseAuth,
    email: String,
    password: String,
    onSignInSucess: (MainScreenDataObject) -> Unit,
    onSignInFailure: (String) -> Unit
)  {
    if (email.isBlank() || password.isBlank()) {
        onSignInFailure("Логин и пароль не могут быть пустыми")
        return
    }

    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) onSignInSucess(
                MainScreenDataObject(
                    task.result.user?.uid!!,
                    task.result.user?.email!!,
                )
            )
        }
        .addOnFailureListener {
            onSignInFailure(it.message ?: "Ошибка Регистрации")
        }
}

