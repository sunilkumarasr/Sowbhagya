package com.biotech.sowbhagyabiotech.ui.profile

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.provider.Settings
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.biotech.sankalpleaders.utils.ApiConstants
import com.biotech.sowbhagyabiotech.Home_Activity
import com.biotech.sowbhagyabiotech.R
import com.biotech.sowbhagyabiotech.authentication.LoginViewModel
import com.biotech.sowbhagyabiotech.databinding.ProfileScreenBinding
import com.biotech.sowbhagyabiotech.sharedPreferences.SecuredSharedPreferenceWrapper
import com.biotech.sowbhagyabiotech.utils.CommonMethods
import com.biotech.sowbhagyabiotech.utils.ImageUtil
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class ProfileFragment : Fragment() {

    private var _binding: ProfileScreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var profileViewModel: LoginViewModel
    lateinit var securedSharedPreferenceWrapper: SecuredSharedPreferenceWrapper
    private var profileURI: File? = null
    var isProfilePicEdited = false
    var profile_image_name="";
    var profile_image_file="";
    var hno = ""
    var landmark = ""
    var locality = ""
    var city = ""
    var state = ""
    var pincode = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = ProfileScreenBinding.inflate(inflater, container, false)
        val root: View = binding.root
        securedSharedPreferenceWrapper =
            SecuredSharedPreferenceWrapper(requireActivity(), "sowbhagya")
        profileViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        addObservers()
        profileViewModel.callgetProfileApi(
            requireContext(), securedSharedPreferenceWrapper.getString(ApiConstants.USER_ID, "")
        )
        binding.updateBT.setOnClickListener {

            validateAndCallprofileAPI()
        }
        binding.profle.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                check13CameraPermissions()
            } else {
                checkCameraAndGalleryPermissions()
            }
        }
        /*try {

            val fullnames = securedSharedPreferenceWrapper.getString("fullname", "").toString()
            val mnum = securedSharedPreferenceWrapper.getString("mnum", "").toString()
            val hn = securedSharedPreferenceWrapper.getString("hn", "").toString()
            val lmark = securedSharedPreferenceWrapper.getString("lmark", "").toString()
            val loc = securedSharedPreferenceWrapper.getString("loc", "").toString()
            val cit = securedSharedPreferenceWrapper.getString("cit", "").toString()
            val stat = securedSharedPreferenceWrapper.getString("stat", "").toString()
            val pin = securedSharedPreferenceWrapper.getString("pin", "").toString()


            if (hn.isEmpty()) {
                //binding.adressdata.text = "Choose Address"

            } else {
                binding.tieAddress.setText(hn + "," + lmark + "," + loc + "," + cit + "," + stat + "," + pin + ".")


            }



        } catch (e: Exception) {
            e.printStackTrace()
        }*/



        return root
    }

    private fun validateAndCallprofileAPI() {
        val firstname = binding.usernameET.text.toString()
        val lastname = binding.lastnameET.text.toString()
        val email = binding.emailET.text.toString()
        val mobile = binding.mobileET.text.toString()

        if (validateEditprofile(firstname = firstname, lastname = lastname, emailId = email, mobile = mobile)) {
            CommonMethods.showProgress(requireActivity())
            if(imageSelectedFlag) {
                profileViewModel.callProfileApi(
                    requireActivity(),
                    securedSharedPreferenceWrapper.getString(ApiConstants.USER_ID, ""),
                    name = firstname + lastname,
                    email_id = email,
                    mobile = mobile,
                    profile_image_name, profile_image_file, hno,landmark,locality,city,state,pincode
                )
            }else{
                profileViewModel.callProfileApi(
                    requireActivity(),
                    securedSharedPreferenceWrapper.getString(ApiConstants.USER_ID, ""),
                    name = firstname + lastname,
                    email_id = email, mobile, "", "", hno,landmark,locality,city,state,pincode
                )
            }
        }
    }

    private fun validateEditprofile(
        firstname: String,
        lastname: String,
        emailId: String,
        mobile: String,


        ): Boolean {

        var flag = true

        if (firstname.isEmpty()) {
            binding.usernameET.error = "Enter your First Name"

            flag = false
        } else if (mobile.isEmpty()) {
            binding.emailET.error = "Enter your Mobile Number"

            flag = false

        }
        return flag
    }

    private fun addObservers() {

        profileViewModel.profileModelResponse.observe(requireActivity()) {

            if (it.Status == true) {
                CommonMethods.hideProgress()
                Toast.makeText(requireActivity(), "" + it.Message, Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.navigation_account)

            } else {
                CommonMethods.hideProgress()
                Toast.makeText(requireActivity(), "" + it.Message, Toast.LENGTH_SHORT).show()
            }
        }
        profileViewModel.getprofileModelResponse.observe(requireActivity()) {

            if (it.Status == true) {
                CommonMethods.hideProgress()
                binding.usernameET.setText("" + it.Response?.name)
                binding.mobileET.setText("" + it.Response?.mobileNumber)
                binding.emailET.setText("" + it.Response?.emailId)
                Glide.with(requireActivity()).load(it.Response?.profile_image_full_url)
                    .placeholder(R.drawable.baseline_account_circle_24)
                    .into(binding.profle)
                hno= it.Response?.hno.toString()
                landmark= it.Response?.landmark.toString()
                locality= it.Response?.locality.toString()
                city = it.Response?.city.toString()
                state= it.Response?.state.toString()
                pincode = it.Response?.pincode.toString()
                binding.tieAddress.setText(""+it.Response?.hno+", "+it.Response?.landmark+", "+it.Response?.locality+", "+it.Response?.city+", "+it.Response?.state+", "+it.Response?.pincode+".")

            } else {
                CommonMethods.hideProgress()
                Toast.makeText(requireActivity(), "" + it.Message, Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        (activity as Home_Activity).setHeaderTitleVisible("Profile")
    }

    override fun onPause() {
        super.onPause()
        (activity as Home_Activity).setHeaderTitleGone()

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun check13CameraPermissions() {
        when {
            ContextCompat.checkSelfPermission(
                binding.root.context, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                check13StoragePermissions()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                Snackbar.make(
                    binding.root,
                    "please provide Storage permissions from settings to take pictures",
                    Snackbar.LENGTH_LONG
                ).show()
            }

            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                cameraLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            check13StoragePermissions()
        } else {
            Snackbar.make(
                binding.root,
                "please provide Storage permissions from settings to take pictures",
                Snackbar.LENGTH_LONG
            ).show()

        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun check13StoragePermissions() {
        when {
            ContextCompat.checkSelfPermission(
                binding.root.context, Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED -> {
                selectImage()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_IMAGES) -> {
                Snackbar.make(
                    binding.root,
                    "please provide Storage permissions from settings to access image files",
                    Snackbar.LENGTH_LONG
                ).show()
            }

            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                storageLauncher.launch(
                    Manifest.permission.READ_MEDIA_IMAGES
                )

            }
        }
    }

    val storageLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            selectImage()
        } else {
            Snackbar.make(
                binding.root,
                "please provide Storage permissions from settings to access image files",
                Snackbar.LENGTH_LONG
            ).show()

        }
    }


    private val CAMERA_AND_STORAGE_PERMISSION_REQUEST_CODE = 2001
    var permissionsGranted: ((granted: Boolean) -> Unit)? = null
    fun addEvent(permissionsGranted: ((granted: Boolean) -> Unit)?) {
        this.permissionsGranted = permissionsGranted
    }

    private fun checkCameraAndGalleryPermissions() {
        val requiredPermissions = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
        )
        addEvent {
            if (it) {
                selectImage()
            } else {
                showSnackBarPermissionsMessage("please provide camera and storage permissions to take pictures and files from app settings")
            }
        }
        checkAndRequestPermissions(requiredPermissions)
    }

    fun checkAndRequestPermissions(permissions: Array<String>) {
        val permissionsToRequest = mutableListOf<String>()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    requireActivity(), permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsToRequest.add(permission)
            }
        }

        if (permissionsToRequest.isNotEmpty()) {
            permissionsLauncher.launch(permissionsToRequest.toTypedArray())
        } else {
            permissionsGranted?.invoke(true)
            // All permissions are granted, proceed with your logic
        }
    }

    fun showSnackBarPermissionsMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).setAction("okay") {
            openSettings()
        }.show()
    }

    fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", requireActivity().packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    private val permissionsLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            // Check if all permissions are granted after the request
            val allPermissionsGranted = permissions.all { it.value }
            if (allPermissionsGranted) {
                permissionsGranted?.invoke(true) // All permissions are granted, proceed with your logic
            } else {
                permissionsGranted?.invoke(false) // Some or all permissions are denied, handle this case or show a message
            }
        }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_AND_STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage()

                // ImagePicker.Companion.with(this).crop(16F, 9F).compress(1024).start(1)
            } else {
                Toast.makeText(
                    requireActivity(),
                    "please, allow camera and storage permissions from app settings to upload pictures",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun selectImage() {
        try {
            val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
            val builder = AlertDialog.Builder(requireActivity())
            builder.setTitle("Add Photo")

            builder.setItems(options) { dialog, item ->
                if (options[item] == "Take Photo") {

                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    //startActivityForResult(intent, CAMERA_REQUEST_CODE)
                    startActivityForResult(intent, REQUEST_TAKE_PHOTO)

                } else if (options[item] == "Choose from Gallery") {
                    val intent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    //startActivityForResult(intent, STORAGE_REQUEST_CODE)
                    startActivityForResult(intent, REQUEST_CHOOSE_FROM_GALLERY)
                } else if (options[item] == "Cancel") {
                    dialog.dismiss()
                }
            }
            builder.show()
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    var REQUEST_TAKE_PHOTO = 1
    var REQUEST_CHOOSE_FROM_GALLERY = 2
    var thumbnailUploadedImage: Pair<Bitmap, File>? = null

    var imageSelectedFlag = false
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (data != null) {

                if (resultCode == Activity.RESULT_OK) {
                    when (requestCode) {
                        REQUEST_TAKE_PHOTO -> try {
                            data.apply {
                                profileURI = onCaptureImageResult(this)?.second
                                Glide.with(requireActivity()).load(profileURI)
                                    .into(binding.profle)
                                imageSelectedFlag = true


                                profileURI = onSelectImageResult(data)?.second
                                val uri: Uri? = data?.getData()


                                val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)

                                val stream = ByteArrayOutputStream()

                                // compress Bitmap
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                                // Initialize byte array
                                val bytes = stream.toByteArray()

                                profile_image_file = Base64.encodeToString(bytes, Base64.DEFAULT)


                                val returnCursor: Cursor = requireActivity().contentResolver.query(uri!!, null, null, null, null)!!
                                val nameIndex = returnCursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                                returnCursor!!.moveToFirst()
                                profile_image_name = returnCursor!!.getString(nameIndex)

                                returnCursor!!.close()

                            }
                        } catch (e: java.lang.Exception) {
                            e.printStackTrace()
                        }

                        REQUEST_CHOOSE_FROM_GALLERY -> try {
                            profileURI = onSelectImageResult(data)?.second
                            val uri: Uri? = data?.getData()
                            Glide.with(requireActivity()).load(profileURI).into(binding.profle)
                            imageSelectedFlag = true

                            val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)

                            val stream = ByteArrayOutputStream()

                            // compress Bitmap
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                            // Initialize byte array
                            val bytes = stream.toByteArray()

                            profile_image_file = Base64.encodeToString(bytes, Base64.DEFAULT)


                            val returnCursor: Cursor = requireActivity().contentResolver.query(uri!!, null, null, null, null)!!
                            val nameIndex = returnCursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                            returnCursor!!.moveToFirst()
                            profile_image_name = returnCursor!!.getString(nameIndex)

                            returnCursor!!.close()


                        } catch (ex: Exception) {
                            ex.printStackTrace()
                        }
                    }
                }
            } else {
//                Toast.makeText(activity, "Select Shop Image...", Toast.LENGTH_LONG).show()

            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    fun onCaptureImageResult(data: Intent): Pair<Bitmap, File>? {
        var pair: Pair<Bitmap, File>? = null
        if (data.hasExtra("data")) {
            val thumbnail = data.extras?.get("data") as Bitmap
            pair = onBitmapFileResult(thumbnail)
        }
        return pair
    }

    fun onBitmapFileResult(thumbnail: Bitmap): Pair<Bitmap, File>? {
        val bytes = ByteArrayOutputStream()
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val destination = File(requireActivity().externalCacheDir, System.currentTimeMillis().toString() + ".jpg")
        val fo: FileOutputStream
        try {
            destination.createNewFile()
            fo = FileOutputStream(destination)
            fo.write(bytes.toByteArray())
            fo.close()
            val compressedImagePath = ImageUtil.compressImage(requireActivity(), destination.absolutePath)
            return Pair(thumbnail, File(compressedImagePath))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun onSelectImageResult(data: Intent): Pair<Bitmap, File>? {
        val selectedImage = data.data
        if (selectedImage != null) {
            val file = getFileFromUri(requireActivity(), selectedImage)
            val compressImage = ImageUtil.compressImage(requireActivity(), file?.absolutePath ?: "")
            if (compressImage != null) {
                val bmp = BitmapFactory.decodeFile(compressImage)
                return Pair(bmp, File(compressImage))
            }
        }
        return null
    }

    fun getFileFromUri(context: Context, uri: Uri): File? {
        var inputStream: InputStream? = null
        var outputStream: FileOutputStream? = null
        var file: File? = null
        try {
            inputStream = context.contentResolver.openInputStream(uri)
            file = createTemporalFile(context)
            outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
            outputStream?.close()
        }
        return file
    }

    @Throws(IOException::class)
    private fun createTemporalFile(context: Context): File {
        val timeStamp: String = System.currentTimeMillis().toString()
        val fileName = "IMG_$timeStamp"
        val storageDir: File = context.externalCacheDir ?: context.cacheDir
        return File.createTempFile(fileName, ".jpg", storageDir)
    }
}