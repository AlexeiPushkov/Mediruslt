package com.diplom.mediresult.presentation.pdf

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diplom.mediresult.util.SharedPreferencesKey
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPdfReaderState
import kotlinx.coroutines.launch

class PDFViewModel: ViewModel() {
    val uriPolicy = "file://assets/policy.pdf".toUri()
    val path = mutableStateOf<String?>("")

    val pdfPolicyReaderState = VerticalPdfReaderState(
        resource = ResourceType.Local(uri = uriPolicy),
        isZoomEnable = true
    )

    fun pdf(context: Context): VerticalPdfReaderState{
        getPath(context = context)

        return VerticalPdfReaderState(
            resource = ResourceType.Remote("https://bdpapaqkxiujjrhowsvx.supabase.co/storage/v1/object/public/policy//${path.value}"),
            isZoomEnable = true
        )
    }



    fun savePath(context: Context, data: String){
        viewModelScope.launch {
            val sharedPref = SharedPreferencesKey(context)
            sharedPref.savePath("path", data)
        }
    }

    fun getPath(context: Context) {
        val sharedPref = SharedPreferencesKey(context)
        path.value = sharedPref.getPath("path")
    }
}