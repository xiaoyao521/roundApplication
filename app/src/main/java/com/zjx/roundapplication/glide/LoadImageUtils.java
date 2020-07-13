package com.zjx.roundapplication.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.security.MessageDigest;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * <p> 图片加载工具类</p>
 *
 * @name LoadImageUtils
 */
public class LoadImageUtils {

    /**
     * 默认加载
     * placeholder(占位符)
     * error (错误符)
     * fallback(后备服务符)
     */

    public static void loadImageView(String path, ImageView mImageView) {
        try {
            GlideApp.with(mImageView.getContext()).load(path).into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadBitmapImageView(String path, ImageView mImageView) {
        try {
            GlideApp.with(mImageView.getContext()).asBitmap().load(path).into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadGifImageView(int path, ImageView mImageView) {
        try {
            GlideApp.with(mImageView.getContext()).load(path)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadImageView(String path, ImageView mImageView, RequestListener<Drawable> requestListener) {
        try {
            GlideApp.with(mImageView.getContext()).load(path).addListener(requestListener).into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadImageView(File file, ImageView mImageView) {
        try {
            GlideApp.with(mImageView.getContext()).load(file)
                    .skipMemoryCache(true) // 不使用内存缓存
                    .diskCacheStrategy(DiskCacheStrategy.NONE) // 不使用磁盘缓存
                    .into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void loadImageView(File file, ImageView mImageView, int width, int height, RequestListener<Drawable> requestListener) {
        try {
            GlideApp.with(mImageView.getContext()).load(file)
                    .skipMemoryCache(true) // 不使用内存缓存
                    .override(width, height)
                    .diskCacheStrategy(DiskCacheStrategy.NONE) // 不使用磁盘缓存
                    .transition(withCrossFade())
                    .addListener(requestListener)
                    .into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void loadBulrImageView(File file, ImageView mImageView) {
        GlideApp.with(mImageView.getContext()).load(file)
                .skipMemoryCache(true) // 不使用内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE) // 不使用磁盘缓存
                .into(mImageView);
    }

    public static void loadImageView(int imgId, int width, int height, ImageView mImageView) {
        try {
            GlideApp.with(mImageView.getContext()).load(imgId)
                    .override(width, height)
                    .into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void loadImageView(String path, int width, int height, ImageView mImageView) {
        try {
            GlideApp.with(mImageView.getContext()).load(path)
                    .override(width, height)
                    .into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void loadImageView(String path, int width, int height, ImageView mImageView, RequestListener<Drawable> requestListener) {
        try {
            GlideApp.with(mImageView.getContext()).load(path)
                    .override(width, height)
                    .transition(withCrossFade())
                    .listener(requestListener)
                    .into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void loadImageView(int path, int width, int height, ImageView mImageView, RequestListener<Drawable> requestListener) {
        GlideApp.with(mImageView.getContext()).load(path)
                .override(width, height)
                .listener(requestListener)
                .into(mImageView);
    }

    /**
     * 带圆角重写宽高
     *
     * @param mContext
     * @param path
     * @param width
     * @param height
     * @param roundingRadius
     * @param mImageView
     */
    public static void loadRoundImageHolderView(Context mContext, String path, int width, int height, int roundingRadius, ImageView mImageView) {
        try {
            RoundedCorners roundedCorners = new RoundedCorners(roundingRadius);//数字为圆角度数
            RequestOptions coverRequestOptions = new RequestOptions()
                    .transforms(roundedCorners)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            GlideApp.with(mContext)
                    .load(path)
                    .override(width, height)
                    .apply(coverRequestOptions)
                    .into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void loadImageView(int imgId, ImageView mImageView) {
        GlideApp.with(mImageView.getContext()).load(imgId).override(Target.SIZE_ORIGINAL).into(mImageView);
    }

    public static void loadIvByBitmap(Bitmap bitmap, ImageView mImageView) {
        GlideApp.with(mImageView.getContext()).load(bitmap).into(mImageView);
    }

    public static void loadImageWithError(String path, int errorRes, ImageView mImageView) {
        GlideApp.with(mImageView.getContext())
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(errorRes)
                .into(mImageView);

    }

    public static void loadImageWithPlaceHolder(String path, int holderRes, ImageView mImageView) {
        GlideApp.with(mImageView.getContext())
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(holderRes)
                .placeholder(holderRes)
                .into(mImageView);

    }


    public static void loadRoundCornerImage(Context mContext, String path, int errorPath, int roundingRadius, ImageView mImageView) {
        // path = UnicomUtils.getLoadImageIsFree(path, mImageView);
        RoundedCorners roundedCorners = new RoundedCorners(roundingRadius);//数字为圆角度数
        RequestOptions coverRequestOptions = new RequestOptions()
                .transforms(roundedCorners)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        GlideApp.with(mContext)
                .asBitmap()
                .load(path)
                .error(errorPath)
                .apply(coverRequestOptions)
                .into(mImageView);
    }

    public static void loadRoundCornerImage(Context mContext, int path, int roundingRadius, ImageView mImageView) {
        RoundedCorners roundedCorners = new RoundedCorners(roundingRadius);//数字为圆角度数
        RequestOptions coverRequestOptions = new RequestOptions()
                .transforms(roundedCorners)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        GlideApp.with(mContext)
                .asBitmap()
                .load(path)
                .apply(coverRequestOptions)
                .into(mImageView);
    }

    public static class GlideRoundTransform extends BitmapTransformation {
        private static float radius = 0f;

        public GlideRoundTransform() {
            this(4);
        }

        public GlideRoundTransform(int dp) {
            super();
//            this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
            this.radius = dp;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return roundCrop(pool, toTransform);
        }

        private static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }

        @Override
        public void updateDiskCacheKey(MessageDigest messageDigest) {

        }
    }


    public static void loadCircleCropImage(Context mContext, String path, int errorPath, ImageView mImageView) {
        try {
            if (TextUtils.isEmpty(path)) {
                GlideApp.with(mContext)
                        .load(path)
                        .error(errorPath)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .transform(new CircleCrop())
                        .into(mImageView);

            } else {
                GlideApp.with(mContext)
                        .load(path)
                        .transform(new CircleCrop())
                        .into(mImageView);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void loadCircleCropImage(Context mContext, int resId, ImageView mImageView) {
        try {
            GlideApp.with(mContext)
                    .load(resId)
                    .transform(new CircleCrop())
                    .into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void loadGenderCircleCropFramoImage(final Context mContext, Integer id, ImageView mImageView, int borderWidth, int borderColor) {
        try {
            GlideApp.with(mContext)
                    .load(id)
                    .transform(new GlideCircleTransform(mContext, borderWidth, borderColor))
                    .into(mImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}