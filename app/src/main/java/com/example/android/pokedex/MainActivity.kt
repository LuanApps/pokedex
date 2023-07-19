package com.example.android.pokedex

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import com.example.android.pokedex.model.Pokemon
import com.example.android.pokedex.view.MainFragmentDirections

class MainActivity : AppCompatActivity() {

    val PERMISSION_REQUEST_CODE = 117

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT > 32 && !shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
            requestNotificationPermission()
        }

        // Handle intent data from notification
        handleNotificationIntent(intent)
    }

    private fun handleNotificationIntent(intent: Intent?) {
        if (intent?.action == "com.example.android.pokedex.NOTIFICATION_ACTION") {
            val pokemon = intent.getParcelableExtra<Pokemon>("pokemon")
            if (pokemon != null) {
                val navController = findNavController(R.id.nav_host_fragment)
                val action = MainFragmentDirections.actionMainFragmentToPokemonDetailsFragment(pokemon)
                navController.navigate(action)
            }
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT > 32) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                } else {
                    // Permission denied
                    showPermissionRequiredMessage()
                }
            }
        }
    }

    private fun showPermissionRequiredMessage() {
        val message = "This permission is required for the app to work. Please enable the permission in the app settings."
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Permission Required")
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setPositiveButton("Allow Permission needed") { _, _ ->
            requestNotificationPermission()
        }
        alertDialogBuilder.setNegativeButton("Do not allow", null)
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}