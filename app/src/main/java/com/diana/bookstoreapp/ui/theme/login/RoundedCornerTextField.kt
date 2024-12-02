package com.diana.bookstoreapp.ui.theme.login

import android.widget.NumberPicker.OnValueChangeListener
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.diana.bookstoreapp.ui.theme.ButtonColor
import androidx.compose.ui.Modifier
import com.diana.bookstoreapp.data.Book
import com.diana.bookstoreapp.ui.theme.TrimColor

@Composable
fun RoundedCornerTextField (
    singleLine: Boolean = true,
    text: String,
    label: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = text, onValueChange = {
            onValueChange(it)
        },
        shape = RoundedCornerShape(25.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier.fillMaxWidth().border(3.dp, TrimColor, RoundedCornerShape(25.dp)),
        label = {
            Text(text = label, color = Color.Gray)
        },
        singleLine = singleLine
    )
}