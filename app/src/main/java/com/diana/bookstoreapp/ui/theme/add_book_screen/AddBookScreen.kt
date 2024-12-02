package com.diana.bookstoreapp.ui.theme.add_book_screen

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.net.Uri
import coil3.compose.rememberAsyncImagePainter
import com.diana.bookstoreapp.R
import com.diana.bookstoreapp.data.Book
import com.diana.bookstoreapp.ui.theme.login.LoginButton
import com.diana.bookstoreapp.ui.theme.login.RoundedCornerTextField
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

import com.google.firebase.storage.ktx.storage


@SuppressLint("RememberReturnType")
@Preview(showBackground = true)
@Composable
fun AddBookScreen() {

    var selectedCategory = "Утопии"
    val title = remember {
        mutableStateOf("")
    }
    val description = remember {
        mutableStateOf("")
    }
    val grade = remember {
        mutableStateOf("")
    }

    val selectedImageUri = remember {
            mutableStateOf<Uri?>(null)
    }

    val firestore = remember {
        Firebase.firestore
    }

    val storage = remember {
        Firebase.storage
    }

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedImageUri.value = uri
    }

    Image(painter = rememberAsyncImagePainter(
        model = selectedImageUri.value),
        contentDescription = "BG",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds,
        alpha = 0.2f
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

        Image(
            painter = painterResource(id = R.drawable.book),
            contentDescription = "Book",
            modifier = Modifier.size(160.dp)
        )

        Text(
            text = "Добавить книгу",
            color = Color.Black,
            fontWeight = FontWeight.Light,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        RoundedCornerDropDownMenu { selectedItem ->
            selectedCategory = selectedItem
        }

        Spacer(modifier = Modifier.height(20.dp))
        RoundedCornerTextField(
            text = title.value,
            label = "Заголовок"
        ) {
            title.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        RoundedCornerTextField(
            singleLine = false,
            text = description.value,
            label = "Отзыв"
        ) {
            description.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        RoundedCornerTextField(
            singleLine = false,
            text = description.value,
            label = "Оценка"
        ) {
            grade.value = it
        }
        LoginButton(text = "Добавить изображение") {
            imageLauncher.launch("image/*")
        }
        LoginButton(text = "Сохранить") {

        }
    }
}

