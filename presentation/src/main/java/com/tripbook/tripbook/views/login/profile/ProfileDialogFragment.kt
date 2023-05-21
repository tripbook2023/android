package com.tripbook.tripbook.views.login.profile

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.tripbook.tripbook.R
import com.tripbook.tripbook.databinding.FragmentProfileBinding
import com.tripbook.tripbook.databinding.FragmentProfileDialogBinding
import com.tripbook.tripbook.viewmodel.ProfileViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProfileDialogFragment(binding: FragmentProfileBinding): DialogFragment() {

    private val viewModel: ProfileViewModel by viewModels()


    private var _binding: FragmentProfileDialogBinding? = null
    private val binding get() = _binding!!
    private val profileBinding = binding

    private lateinit var photoUri: Uri
    private val galleryLauncher = registerForActivityResult(PickVisualMedia()){ uri ->
        if(uri != null){
            Log.d("Photo Picker", uri.toString())
            profileBinding.profile.setImageURI(uri)
            viewModel.setProfileValid(true)
        }else{
            Log.d("Photo Picker", "No Media selected")
        }
        dismiss()
    }

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()){ isSuccess ->
        if(isSuccess) {
            profileBinding.profile.setImageURI(photoUri)
            viewModel.setProfileValid(true)
        }
        else
            Log.d("cameraLauncher", "Failed")
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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileBinding.viewModel = viewModel
        binding.album.setOnClickListener{
            galleryLauncher.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }
        binding.camera.setOnClickListener{
            createImgFile().let { uri ->
                photoUri = uri!!
                cameraLauncher.launch(uri)
            }
        }
        binding.basicImage.setOnClickListener{
            viewModel.setProfileValid(true)
            profileBinding.profile.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.tripbook_image))
            dismiss()
        }
        binding.cancelButton.setOnClickListener{
            dismiss()
        }
    }

    private fun createImgFile(): Uri?{
        val now = SimpleDateFormat("yyMMdd-HHmmss", Locale.getDefault()).format(Date())
        val content = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME,"IMG_$now.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        }
        return requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, content)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}