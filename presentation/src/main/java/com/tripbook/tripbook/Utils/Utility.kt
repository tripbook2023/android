package com.tripbook.tripbook.Utils

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.DisplayMetrics
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Context를 기반으로 한 extension 함수로

fun Context.convertPxToDp(px: Int): Float {
    return px / ((this.resources.displayMetrics.densityDpi.toFloat()) / DisplayMetrics.DENSITY_DEFAULT)
}

fun Context.getImagePathFromURI(uri: Uri): String? {
    val contentResolver: ContentResolver = this.contentResolver ?: return null

    // 파일 경로를 만들기
    val filePath: String =
        (this.applicationInfo.dataDir + File.separator + System.currentTimeMillis())
    val file = File(filePath)
    try {
        // 매개변수로 받은 uri 를 통해  이미지에 필요한 데이터 불러오기
        val inputStream = contentResolver.openInputStream(uri) ?: return null
        // 이미지 데이터를 다시 내보내면서 file 객체에  만들었던 경로 이용
        val outputStream: OutputStream = FileOutputStream(file)
        val buf = ByteArray(1024)
        var len: Int
        while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
        outputStream.close()
        inputStream.close()
    } catch (ignore: IOException) {
        return null
    }
    return file.absolutePath
}

fun Context.createImageFile(): Uri? {
    val now = SimpleDateFormat("yyMMdd-HHmmss", Locale.getDefault()).format(Date())
    val content = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "IMG_$now.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
    }
    return this.contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        content
    )
}
