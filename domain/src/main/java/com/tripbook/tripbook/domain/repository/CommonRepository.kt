package com.tripbook.tripbook.domain.repository

import com.tripbook.tripbook.domain.model.Image
import kotlinx.coroutines.flow.Flow
import java.io.File

interface CommonRepository {
    fun uploadImage(
        image: File
    ): Flow<Image?>
}