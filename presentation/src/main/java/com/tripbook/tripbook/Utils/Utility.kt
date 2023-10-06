package com.tripbook.tripbook.Utils

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.DisplayMetrics
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun convertPxToDp(px: Int, context: Context): Float{
    return px / ((context.resources.displayMetrics.densityDpi.toFloat()) / DisplayMetrics.DENSITY_DEFAULT)
}

fun getImagePathFromURI(uri: Uri, context: Context): String?{
    val result: String?
    val cursor: Cursor? =
        context.contentResolver.query(uri, null, null, null, null)
    if (cursor == null) {
        result = uri.path
    } else {
        cursor.moveToFirst()
        val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        result = cursor.getString(idx)
        cursor.close()
    }
    return result
}

fun createImageFile(context: Context): Uri?{
    val now = SimpleDateFormat("yyMMdd-HHmmss", Locale.getDefault()).format(Date())
    val content = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "IMG_$now.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
    }
    return context.contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        content
    )
}
