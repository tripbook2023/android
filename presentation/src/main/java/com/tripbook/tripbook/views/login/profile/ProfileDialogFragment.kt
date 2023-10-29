package com.tripbook.tripbook.views.login.profile

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import android.view.Gravity
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.fragment.app.activityViewModels
import com.tripbook.base.BaseDialogFragment
import com.tripbook.tripbook.R
import com.tripbook.tripbook.Utils.createImageFile
import com.tripbook.tripbook.Utils.getImagePathFromURI
import com.tripbook.tripbook.databinding.FragmentProfileDialogBinding
import com.tripbook.tripbook.viewmodel.LoginViewModel


class ProfileDialogFragment :
    BaseDialogFragment<FragmentProfileDialogBinding, LoginViewModel>(R.layout.fragment_profile_dialog) {

    override val viewModel: LoginViewModel by activityViewModels()
    private lateinit var photoUri: Uri
    private val galleryLauncher = registerForActivityResult(PickVisualMedia()) { uri ->
        uri?.let {
            Log.d("Photo Picker Uri", uri.toString())
            val fullPath = requireContext().getImagePathFromURI(uri)
            viewModel.setProfileUri(uri, fullPath, false)
        } ?: {
            Log.d("Photo Picker", "No Media selected")
        }
        dismiss()
    }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                val fullPath = requireContext().getImagePathFromURI(photoUri)
                viewModel.setProfileUri(photoUri, fullPath, false)
            } else {
                Log.d("cameraLauncher", "Failed")
            }
            dismiss()
        }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setGravity(Gravity.BOTTOM)
    }

    override fun init() {
        binding.album.setOnClickListener {
            galleryLauncher.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }
        binding.camera.setOnClickListener {
            requireContext().createImageFile().let { uri ->
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
            val fullPath = requireContext().getImagePathFromURI(uri)
            viewModel.setProfileUri(uri, fullPath, true)
            dismiss()
        }
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }
}