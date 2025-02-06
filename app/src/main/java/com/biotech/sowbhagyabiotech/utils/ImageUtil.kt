package com.biotech.sowbhagyabiotech.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.util.Log
import okhttp3.OkHttpClient
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

/**
 * The type Image util.
 */
object ImageUtil {
    /**
     * Create image file file.
     *
     * @return the file
     * @throws IOException the io exception
     */
    @Throws(IOException::class)
    fun createImageFile(imgNamePrefix: String): File { // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = imgNamePrefix + timeStamp + "_"
        val dir =
            Environment.getExternalStorageDirectory().absolutePath + File.separator + "VTS"
        val storageDir = File(dir)
        if (storageDir.exists() && !storageDir.isDirectory) {
            storageDir.deleteOnExit()
        }

        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }
        val file = File(storageDir.absolutePath, "$imageFileName.jpg")
        file.createNewFile()
        return file
//        return File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",  /* suffix */
//                storageDir /* directory */
//        )
    }

    /**
     * Create image file file.
     *
     * @return the file
     * @throws IOException the io exception
     */
    @Throws(IOException::class)
    fun createImageFile(
        context: Context,
        imgNamePrefix: String
    ): File { // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = imgNamePrefix + timeStamp + "_"

        val storageDir = context.externalCacheDir
        if (storageDir?.exists() == true && !storageDir.isDirectory) {
            storageDir.deleteOnExit()
        }

        if (storageDir?.exists() == false) {
            storageDir.mkdirs()
        }
        val file = File(storageDir?.absolutePath ?: "", "$imageFileName.jpg")
        file.createNewFile()
        return file
    }

    /**
     * Gets image from camera.
     *
     * @param mContext          the m context
     * @param IMAGE_CAPTURE_URI the image capture uri
     * @return the image from camera
     */
    fun getImageFromCamera(mContext: Context, IMAGE_CAPTURE_URI: Uri): Bitmap? {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        /*return if (manufacturer.equals("samsung", ignoreCase = true) || model.equals("samsung", ignoreCase = true)) {*/
        val rotation: Int = getCameraPhotoOrientation(
            mContext, IMAGE_CAPTURE_URI, IMAGE_CAPTURE_URI.path
                ?: ""
        )
        val matrix = Matrix()
        matrix.postRotate(rotation.toFloat())
        val options = BitmapFactory.Options()
        options.inSampleSize = 8
        val bitmap = BitmapFactory.decodeFile(IMAGE_CAPTURE_URI.path, options)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        /*} else {
            val options = BitmapFactory.Options()
            options.inSampleSize = 8
            BitmapFactory.decodeFile(IMAGE_CAPTURE_URI.path, options)
        }*/
    }

    /**
     * This method is used get orientation of camera photo
     *
     * @param context
     * @param imageUri  This parameter is Uri type
     * @param imagePath This parameter is String type
     * @return rotate
     */
    private fun getCameraPhotoOrientation(
        context: Context,
        imageUri: Uri?,
        imagePath: String
    ): Int {
        var rotate = 0
        try {
            try {
                if (imageUri != null) context.contentResolver.notifyChange(imageUri, null)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            val imageFile = File(imagePath)
            val exif = ExifInterface(imageFile.absolutePath)
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_270 -> rotate = 270
                ExifInterface.ORIENTATION_ROTATE_180 -> rotate = 180
                ExifInterface.ORIENTATION_ROTATE_90 -> rotate = 90
                ExifInterface.ORIENTATION_NORMAL -> rotate = 0
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return rotate
    }

    /**
     * Bitmap to file file.
     *
     * @param context       the context
     * @param bitmap        the bitmap
     * @param imgNamePrefix the img name prefix
     * @return the file
     */
    fun bitmapToFile(
        context: Context,
        bitmap: Bitmap,
        imgNamePrefix: String?
    ): File? { //create a file to write bitmap data
        val f: File
        try {
            f = createImageFile(context, imgNamePrefix!!)
            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos)
            val bitmapdata = bos.toByteArray()
            //write the bytes in file
            val fos = FileOutputStream(f)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return f
    }


    fun compressImage(context: Context, filePath: String): String? {

        //val filePath = getRealPathFromURI(imageUri)
        var scaledBitmap: Bitmap? = null

        val options = BitmapFactory.Options()

        // by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
        // you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true
        var bmp = BitmapFactory.decodeFile(filePath, options)

        var actualHeight = options.outHeight
        var actualWidth = options.outWidth

        //max Height and width values of the compressed image is taken as 816x612

        /*val maxHeight = 816.0f
        val maxWidth = 612.0f*/

        val maxHeight = 1080.0f
        val maxWidth = 1080.0f

        var imgRatio = (actualWidth / actualHeight).toFloat()
        val maxRatio = maxWidth / maxHeight

        //width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight
                actualWidth = (imgRatio * actualWidth).toInt()
                actualHeight = maxHeight.toInt()
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth
                actualHeight = (imgRatio * actualHeight).toInt()
                actualWidth = maxWidth.toInt()
            } else {
                actualHeight = maxHeight.toInt()
                actualWidth = maxWidth.toInt()

            }
        }

        // setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight)

        // inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false

        // this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true
        options.inInputShareable = true
        options.inTempStorage = ByteArray(16 * 1024)

        try {
            // load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options)
        } catch (exception: OutOfMemoryError) {
            exception.printStackTrace()

        }

        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888)
        } catch (exception: OutOfMemoryError) {
            exception.printStackTrace()
        }

        val ratioX = actualWidth / options.outWidth.toFloat()
        val ratioY = actualHeight / options.outHeight.toFloat()
        val middleX = actualWidth / 2.0f
        val middleY = actualHeight / 2.0f

        val scaleMatrix = Matrix()
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)

        val canvas = Canvas(scaledBitmap!!)
        canvas.setMatrix(scaleMatrix)
        canvas.drawBitmap(
            bmp,
            middleX - bmp.width / 2,
            middleY - bmp.height / 2,
            Paint(Paint.FILTER_BITMAP_FLAG)
        )

        // check the rotation of the image and display it properly
        val exif: ExifInterface
        try {
            exif = ExifInterface(filePath)

            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED
            )
            Log.d("EXIF", "Exif: $orientation")
            val matrix = Matrix()
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                matrix.postRotate(90f)
                Log.d("EXIF", "Exif: $orientation")
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                matrix.postRotate(180f)
                Log.d("EXIF", "Exif: $orientation")
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                matrix.postRotate(270f)
                Log.d("EXIF", "Exif: $orientation")
            }
            scaledBitmap = Bitmap.createBitmap(
                scaledBitmap, 0, 0,
                scaledBitmap.width, scaledBitmap.height, matrix,
                true
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }

        var out: FileOutputStream? = null
        var outPath: File? = null
        try {

            outPath = createImageFile(context, "JPEG_")
            out = FileOutputStream(outPath)

            // write the compressed bitmap at the destination specified by filename.
            scaledBitmap!!.compress(Bitmap.CompressFormat.JPEG, 80, out)

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        return outPath?.absolutePath

    }

    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val heightRatio = (height.toFloat() / reqHeight.toFloat()).roundToInt()
            val widthRatio = (width.toFloat() / reqWidth.toFloat()).roundToInt()
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }
        val totalPixels = (width * height).toFloat()
        val totalReqPixelsCap = (reqWidth * reqHeight * 2).toFloat()
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++
        }

        return inSampleSize
    }

    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    private var httpClient: OkHttpClient? = null


//    fun loadSVG(mContext: Context, singleImagePath: String?, ivSignature: ImageView) {
//        val imageLoader = ImageLoader.Builder(mContext)
//            .components {
//                add(SvgDecoder.Factory())
//            }
//            .build()
//        val request = ImageRequest.Builder(mContext)
//            .data(singleImagePath)
//            .crossfade(true)
//            .target(ivSignature)
//            .build()
//        imageLoader.enqueue(request)
//    }


}