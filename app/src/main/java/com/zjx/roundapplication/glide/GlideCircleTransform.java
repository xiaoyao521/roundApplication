package com.zjx.roundapplication.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

public class GlideCircleTransform extends BitmapTransformation {

    private Paint mBorderPaint;
    private float mBorderWidth;

    public GlideCircleTransform(Context context) {
        super();
    }

    public GlideCircleTransform(Context context, int borderWidth, int borderColor) {
        super();
        mBorderWidth = context.getResources().getDimensionPixelSize(borderWidth);

        mBorderPaint = new Paint();
        mBorderPaint.setDither(true);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(borderColor);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mBorderWidth);
    }


    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        float size = (Math.min(source.getWidth(), source.getHeight()) - (mBorderWidth / 2));
        int x = (int) ((source.getWidth() - size) / 2);
        int y = (int) ((source.getHeight() - size) / 2);
        Bitmap squared = Bitmap.createBitmap(source, x, y, (int) size + 1, (int) size + 1);
        Bitmap result = pool.get((int) size + 1, (int) size + 1, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap((int) size + 1, (int) size + 1, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        if (mBorderPaint != null) {
            float borderRadius = r - mBorderWidth / 2 + 0.8f;
            canvas.drawCircle(r, r, borderRadius, mBorderPaint);
        }
        return result;
    }

    public String getId() {
        return getClass().getName();
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }
}