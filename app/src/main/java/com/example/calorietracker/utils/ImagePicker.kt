package com.example.calorietracker.utils

import android.net.Uri
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LifecycleOwner

class ImagePicker(
    private val activityResultRegistry: ActivityResultRegistry,
    private val lifecycleOwner: LifecycleOwner
) {
    private val REGISTRY_KEY_GALLERY = "GalleryPickerKey"
    private val REGISTRY_KEY_CAMERA = "CameraPickerKey"
    private val REGISTRY_KEY_GALLERY_PERMISSION = "Permission"

    private lateinit var loadCallback: (imageUri: Uri?) -> Unit

    fun pickImageFromGallery(loadCallback: (imageUri: Uri?) -> Unit) {
        this.loadCallback = loadCallback
        getGalleryImage.launch("image/*")
    }

    private val getGalleryImage = activityResultRegistry.register(REGISTRY_KEY_GALLERY, ActivityResultContracts.GetContent(), loadCallback)
//    private val getCameraImage = activityResultRegistry.register(REGISTRY_KEY_CAMERA, ActivityResultContracts.TakePicturePreview(), loadCallback)

//       private val cameraPermission = activityResultRegistry.register(REGISTRY_KEY_GALLERY_PERMISSION, ActivityResultContracts.RequestPermission()) { granted ->
//        when {
//            granted -> camera.launch()
//            !shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA) -> {
//                Toast.makeText(context, "cannot proceed without permission", Toast.LENGTH_SHORT).show()
//            }
//            else -> {}
//        }
}
