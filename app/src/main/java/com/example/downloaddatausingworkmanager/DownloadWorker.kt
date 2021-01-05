package com.example.downloaddatausingworkmanager

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.URL
import java.util.*


class DownloadWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    private lateinit var apkStorage: File
    private lateinit var outputFile: File

    /**
     * Override this method to do your actual background processing.  This method is called on a
     * background thread - you are required to **synchronously** do your work and return the
     * [androidx.work.ListenableWorker.Result] from this method.  Once you return from this
     * method, the Worker is considered to have finished what its doing and will be destroyed.  If
     * you need to do your work asynchronously on a thread of your own choice, see
     * [ListenableWorker].
     *
     *
     * A Worker is given a maximum of ten minutes to finish its execution and return a
     * [androidx.work.ListenableWorker.Result].  After this time has expired, the Worker will
     * be signalled to stop.
     *
     * @return The [androidx.work.ListenableWorker.Result] of the computation; note that
     * dependent work will not execute if you use
     * [androidx.work.ListenableWorker.Result.failure] or
     * [androidx.work.ListenableWorker.Result.failure]
     */
    override fun doWork(): Result {

        DownloadURLdata(inputData.getString("file_path").toString())
        Log.e("1", inputData.getString("file_path").toString());
        return Result.success()
    }

    private fun DownloadURLdata(url: String) {

//        var c: HttpURLConnection = URLL.openConnection() as HttpURLConnection
//        c.requestMethod
//        c.connect()

        lateinit var bitmap: Bitmap
        var inputstream: InputStream = URL(url).openStream()
        bitmap=BitmapFactory.decodeStream(inputstream)
        var fos: OutputStream
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q)
        {
            val resolver: ContentResolver = applicationContext.contentResolver
            val contentValues = ContentValues()
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "test" + ".jpg")
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            val imageUri: Uri? =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = resolver.openOutputStream(Objects.requireNonNull(imageUri)!!)!!
        }else{
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .toString()
            val image = File(imagesDir, "test" + ".jpg")
            fos = FileOutputStream(image)
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        Objects.requireNonNull(fos).close()


//        apkStorage = File(
//            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
//                .toString() + "/" + "Androhub Downloads"
//        )
//        if (!apkStorage.exists()) {
//            apkStorage.mkdir()
//            Log.e("1", "Directory Created.+++++++++++++++++++++++++++++++++++++");
//        }
//
//        outputFile = File(apkStorage, "child")//url.replace("http://androhub.com/demo/", "")
//        if (!outputFile.exists()) {
//            outputFile.createNewFile()
//            Log.e("1", "File Created++++++++++++++++++++++++++++++++++++++++++");
//        }

//        val mydir: File = applicationContext.getDir("mydir", Context.MODE_PRIVATE) //Creating an internal dir
//
//        val fileWithinMyDir = File(mydir, "myfile") //Getting a file within the dir.

//        var fos = FileOutputStream(outputFile)
//
//        var iss: InputStream = c.inputStream
//        val buffer = ByteArray(1024)
//        var len1: Int = 0
//
//        do {
//            len1 = iss.read(buffer)
//            if (len1 != -1)
//                fos.write(buffer, 0, len1)
//            else
//                break
//        } while (len1 != -1)

//
//        while ((iss.read(buffer).also { len1 = it }) != -1) {
//            fos.write(buffer, 0, len1) //Write new file
//        }

        //Close all connection after doing task
//        fos.close()
//        iss.close()

    }

}
