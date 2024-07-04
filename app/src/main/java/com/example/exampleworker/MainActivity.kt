package com.example.exampleworker

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.exampleworker.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

private const val ID = "MyWORK"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val launcher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.values.isNotEmpty() && permissions.values.all { it }) {

        } else {
            Toast.makeText(this, "Need permission.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermissions()

        binding.buttonStart.setOnClickListener {

            val uploadWorkRequest: PeriodicWorkRequest = PeriodicWorkRequestBuilder<UploadWorker>(
                15, TimeUnit.MINUTES
            ).build()

            WorkManager.getInstance(this)
                .enqueueUniquePeriodicWork(
                    ID,
                    ExistingPeriodicWorkPolicy.KEEP,
                    uploadWorkRequest)
        }

        binding.buttonStop.setOnClickListener {
            WorkManager.getInstance(this).cancelUniqueWork(ID)
        }
    }

    private fun checkPermissions() {
        val isAllGranted = REQUEST_PERMISSIONS.all { permission ->
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
        }
        if (isAllGranted) {
            Toast.makeText(this, "permission is Granted", Toast.LENGTH_SHORT).show()
        } else {
            launcher.launch(REQUEST_PERMISSIONS)
        }

    }

    companion object {
        private val REQUEST_PERMISSIONS: Array<String> = buildList {
            add(android.Manifest.permission.FOREGROUND_SERVICE)
            add(android.Manifest.permission.POST_NOTIFICATIONS)
        }.toTypedArray()
    }
}