package com.example.apotek.Model;

import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class Notifikasi {

    String message;
    Context ctx;
    String title;
    public Notifikasi(String message, Context ctx, String title) {
        this.message = message;
        this.ctx = ctx;
        this.title = title;
        
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx)
                .setContentTitle(title).setContentText(message).setAutoCancel(true);

        Intent i = new Intent(ctx,getClass());
    }
}
