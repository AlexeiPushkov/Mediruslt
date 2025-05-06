package com.diplom.mediresult.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    hint: String = "Search...",
    onQueryChange: (String) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val backgroundGray = Color(0xFFF5F5F9)
    val textGray = Color(0xFF7E7E9A)

    TextField(
        value = query,
        onValueChange = { newQuery ->
            query = newQuery
            onQueryChange(newQuery)
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        placeholder = {
            Text(
                text = hint,
                color = Color.Gray
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color.Gray
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(
                    onClick = {
                        query = ""
                        onQueryChange("")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear Icon",
                        tint = Color.Gray
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = textGray,
            focusedTextColor = textGray,
            focusedContainerColor = backgroundGray,
            unfocusedContainerColor = backgroundGray,
            focusedLabelColor = textGray,
            unfocusedLabelColor = textGray,
            focusedPlaceholderColor = textGray,
            unfocusedPlaceholderColor = textGray,
            cursorColor = textGray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        )
    )
}