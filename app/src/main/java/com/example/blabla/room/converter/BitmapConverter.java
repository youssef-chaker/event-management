package com.example.blabla.room.converter;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverter;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class BitmapConverter {
    @TypeConverter
    public static byte[] fromBitmapToByteArray(Bitmap bm) {
        if(bm == null) return null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG,100,outputStream);
        return outputStream.toByteArray();
    }

    @TypeConverter
    public static Bitmap fromByteArrayToBitmap(byte[] barr) {
        if(barr == null) return null;
        return BitmapFactory.decodeByteArray(barr,0,barr.length);
    }
}
