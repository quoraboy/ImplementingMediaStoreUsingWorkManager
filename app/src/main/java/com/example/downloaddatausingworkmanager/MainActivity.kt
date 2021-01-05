package com.example.downloaddatausingworkmanager

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.downloaddatausingworkmanager.databinding.ActivityMainBinding
import java.io.File


class MainActivity : AppCompatActivity() {
    private lateinit var url: String
    private lateinit var binding: ActivityMainBinding
    var  mWorkManager : WorkManager = WorkManager.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()

        StrictMode.setThreadPolicy(policy)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.downloadstart.setOnClickListener {
            url = binding.urlEditText.text.toString()
         makeDownloadRequest(url)

        }
        binding.seeFolder.setOnClickListener {

            var apkStorage =File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()+"/"+"Androhub Downloads")
                //File(Environment.DIRECTORY_DOWNLOADS + "/" + "Androhub Downloads")

            if (!apkStorage.exists())
                Toast.makeText(this, "Right now there is no directory. Please download some file first.", Toast.LENGTH_SHORT).show();
            else {

                //If directory is present Open Folder

                /** Note: Directory will open only if there is a app to open directory like File Manager, etc.  **/

                var intent = Intent(Intent.ACTION_GET_CONTENT);
                var uri = Uri.parse(apkStorage.absolutePath)
                    //Uri.parse(Environment.DIRECTORY_DOWNLOADS + "/" + "Androhub Downloads");
                intent.setDataAndType(uri, "file/*");
                startActivity(Intent.createChooser(intent, "Open Download Folder"));
            }
        }


    }

    fun makeDownloadRequest(url: String) {


        val data = Data.Builder()
        data.putString("file_path", url)
        // Create charging constraint
//        val constraints = Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.CONNECTED)
//            .setRequiresCharging(true)
//            .build()

        // Add WorkRequest to download the epub to the filesystem
        val save = OneTimeWorkRequest.Builder(DownloadWorker::class.java)
            .setInputData(data.build())
            .build()
        mWorkManager.enqueue(save)
    }

}