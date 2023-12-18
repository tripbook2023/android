package com.tripbook.tripbook.domain.usecase

import com.tripbook.tripbook.domain.repository.CommonRepository
import java.io.File
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val repository: CommonRepository
) {

    operator fun invoke(image: File) = repository.uploadImage(image)
}