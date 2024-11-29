package com.diana.bookstoreapp

import android.graphics.Bitmap
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.compose.ui.unit.dp
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.diana.bookstoreapp.data.Book
import com.diana.bookstoreapp.ui.theme.BookStoreAppTheme

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }

    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val fs = Firebase.firestore
    val storage = Firebase.storage.reference.child("images")
    val list = remember {
        mutableStateOf(emptyList<Book>())
    }
    fs.collection("books").addSnapshotListener { snapShot, exception ->
        list.value = snapShot?.toObjects(Book::class.java) ?: emptyList()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
        ) {
            items(list.value) { book ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        AsyncImage(
                            model = book.imageUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .height(100.dp)
                                .width(100.dp)
                        )

                    }

                    Text(
                        text = book.name, modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth()
                            .padding(15.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            onClick = {

                val task = storage.child("we.jpg").putBytes(
                    bitmapToByteArray(context)
                )
                task.addOnSuccessListener { uploadTask ->
                    uploadTask.metadata?.reference?.downloadUrl?.addOnCompleteListener { uriTask ->
                        saveBook(fs, uriTask.result.toString())
                    }

                }

            }) {
            Text(
                text = "Add Book"
            )
        }

    }

}

private fun bitmapToByteArray(context: Context): ByteArray {
    val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.we)
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
    return baos.toByteArray()
}

private fun saveBook(fs: FirebaseFirestore, url: String) {
    fs.collection("books")
        .document().set(
            Book(
                "My book",
                "bla bla",
                "10",
                "fiction",
                url
            )
        )
}