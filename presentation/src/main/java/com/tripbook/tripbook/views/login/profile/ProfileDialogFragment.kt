package com.tripbook.tripbook.views.login.profile

import android.content.ContentResolver
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentProfileDialogBinding
import com.tripbook.tripbook.viewmodel.LoginViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class ProfileDialogFragment : DialogFragment() {

    private val viewModel: LoginViewModel by activityViewModels()

    private var _binding: FragmentProfileDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var photoUri: Uri
    private val galleryLauncher = registerForActivityResult(PickVisualMedia()) { uri ->
        uri?.let {
            val fullPath = getRealPathFromURI(uri)
            Log.d("Photo Picker fullPath", fullPath.toString())
            viewModel.setProfileUri(uri, fullPath, false)
        } ?: {
            Log.d("Photo Picker", "No Media selected")
        }
        dismiss()
    }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                val fullPath = getRealPathFromURI(photoUri)
                Log.d("Photo Picker fullPath", fullPath.toString())
                viewModel.setProfileUri(photoUri, fullPath, false)
            } else {
                Log.d("cameraLauncher", "Failed")
            }
            dismiss()
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setGravity(Gravity.BOTTOM)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.album.setOnClickListener {
            galleryLauncher.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }
        binding.camera.setOnClickListener {
            createImgFile().let { uri ->
                uri?.let { fileUri ->
                    cameraLauncher.launch(fileUri).also {
                        photoUri = fileUri
                    }
                }
            }
        }
        binding.basicImage.setOnClickListener {
            val uri: Uri = Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(resources.getResourcePackageName(R.drawable.tripbook_image))
                .appendPath(resources.getResourceTypeName(R.drawable.tripbook_image))
                .appendPath(resources.getResourceEntryName(R.drawable.tripbook_image))
                .build()
            val fullPath = getRealPathFromURI(uri)
            viewModel.setProfileUri(uri, fullPath, true)
            dismiss()
        }
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

    private fun createImgFile(): Uri? {
        val now = SimpleDateFormat("yyMMdd-HHmmss", Locale.getDefault()).format(Date())
        val content = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "IMG_$now.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        }
        return requireContext().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            content
        )
    }

    private fun getRealPathFromURI(contentURI: Uri): String? {
        val result: String?
        val cursor: Cursor? =
            requireContext().contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) {
            result = contentURI.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}