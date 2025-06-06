package com.diplom.mediresult.presentation.pdf

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rizzi.bouquet.VerticalPDFReader

@Composable
fun PDF() {
    val context = LocalContext.current
    val viewModel: PDFViewModel = viewModel()
    viewModel.getPath(context)

    if(viewModel.path.value != "") {
        VerticalPDFReader(
            state = viewModel.pdf(context),
            modifier = Modifier
                .fillMaxSize()
        )
    }
}