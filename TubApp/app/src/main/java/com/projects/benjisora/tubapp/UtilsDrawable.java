package com.projects.benjisora.tubapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

/**
 * UtilsDrawable class used to handle the drawables
 */
public class UtilsDrawable {

    /**
     * Converts a Drawable to a Bitmap
     *
     * @param drawable The Drawable to convert
     * @return The converted Bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * Changes the Bitmap's color
     *
     * @param context      The context of the call source
     * @param sourceBitmap The Bitmap to modify
     * @param color        The color to apply
     * @return The new coloured Bitmap
     */
    public static Bitmap changeBitmapColor(Context context, Bitmap sourceBitmap, int color) {
        Bitmap mutableBitmap = sourceBitmap.copy(Bitmap.Config.ARGB_8888, true);
        int[] allPixels = new int[mutableBitmap.getHeight() * mutableBitmap.getWidth()];
        mutableBitmap.getPixels(allPixels, 0, mutableBitmap.getWidth(), 0, 0, mutableBitmap.getWidth(), mutableBitmap.getHeight());
        for (int i = 0; i < allPixels.length; i++) {
            if (allPixels[i] == Color.BLACK) {
                allPixels[i] = color;
            }
        }
        try {
            mutableBitmap.setPixels(allPixels, 0, mutableBitmap.getWidth(), 0, 0, mutableBitmap.getWidth(), mutableBitmap.getHeight());
        } catch (Exception e) {
            Log.e(context.getString(R.string.utils_drawable), context.getString(R.string.log_error), e);
            Toast.makeText(context, R.string.error_fetching_data, Toast.LENGTH_SHORT).show();
        }
        return mutableBitmap;
    }
}
