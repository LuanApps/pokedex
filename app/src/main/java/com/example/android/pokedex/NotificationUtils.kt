package com.example.android.pokedex

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import com.example.android.pokedex.model.Pokemon

private const val NOTIFICATION_CHANNEL_ID = BuildConfig.APPLICATION_ID + ".channel"
private const val NOTIFICATION_ID = 1
private const val PENDING_INTENT_REQUEST_CODE = 100

fun sendNotification(context: Context, pokemon: Pokemon) {
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    // We need to create a NotificationChannel associated with our CHANNEL_ID before sending a notification.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID) == null) {
        val name = context.getString(R.string.app_name)
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            name,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }

    val pendingIntent = createPendingIntent(context, pokemon)
    val notification = createNotification(context, pokemon, pendingIntent)

    notificationManager.notify(NOTIFICATION_ID, notification)
}

private fun createPendingIntent(context: Context, pokemon: Pokemon): PendingIntent {
    val bundle = bundleOf("pokemon" to pokemon)

    return NavDeepLinkBuilder(context)
        .setGraph(R.navigation.main_navigation)
        .setDestination(R.id.pokemonDetailsFragment)
        .setArguments(bundle)
        .createPendingIntent()
}

private fun createNotification(context: Context, pokemon: Pokemon, pendingIntent: PendingIntent): Notification {
    val notificationBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_pokeball)
        .setContentTitle(context.getString(R.string.notification_title))
        .setContentText("Your first pokemon is:  ${pokemon.name}!")
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

    return notificationBuilder.build()
}