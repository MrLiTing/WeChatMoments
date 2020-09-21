package com.lee.wechat.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;



/**
 * @Author: Lee
 * @CreateDate: 2020/9/13 21:35
 * @company:LeeStudio
 * @email: ltms2013@outlook.com
 * @Description: Glide 二次封装类
 */
public class ImageLoader {

    private static final String TAG = ImageLoader.class.getSimpleName();
    /**
     * 单例获取唯一的imageloader实例
     */
    private static volatile ImageLoader instanse = null;

    private ImageLoader() {
    }

    public static ImageLoader getInstanse() {
        if (instanse == null) {
            synchronized (ImageLoader.class) {
                if (instanse == null) {
                    instanse = new ImageLoader();
                }
            }
        }
        return instanse;
    }

    /**
     * 默认最基础的形式显示图片
     *
     * @param context   上下文，可与对应生命周期绑定
     * @param url       可以是 url,SDCard,assets,raw,drawable,ContentProvider...
     * @param imageView 需要填充的imageview
     */
    public void display(Context context, Object url, ImageView imageView) {
        GlideApp.with(context)
                .asBitmap()
                .load(url)
                .into(imageView);
    }

    /**
     * 高质量显示图片
     *
     * @param context
     * @param resizeX
     * @param resizeY
     * @param imageView
     * @param uri
     */
    public void displayHigh(Context context, int resizeX, int resizeY, ImageView imageView, Object uri) {
        GlideApp.with(context)
                .load(uri)
                .override(resizeX, resizeY)
                .priority(Priority.HIGH)
                .fitCenter()
                .into(imageView);
    }

    /**
     * 自定义设置占位图,错误图,远端空图
     *
     * @param context
     * @param url
     * @param imageView
     * @param placeResource    占位图
     * @param errorResource    访问错误显示图片
     * @param fallbackResource 访问远端model为空时显示图片
     */
    public void display(Context context, Object url, ImageView imageView, int placeResource,
                        int errorResource, int fallbackResource) {
        GlideApp.with(context)
                .load(url)
                .placeholder(placeResource)
                .error(errorResource)
                .fallback(fallbackResource)
                .into(imageView);
    }

    /**
     * 自定义设置占位图,错误图
     *
     * @param context
     * @param url
     * @param imageView
     * @param placeResource 占位图
     * @param errorResource 访问错误显示图片
     */
    public void display(Context context, Object url, ImageView imageView, int placeResource,
                        int errorResource) {
        GlideApp.with(context)
                .load(url)
                .placeholder(placeResource)
                .error(errorResource)
                .into(imageView);
    }


    /**
     * 加载带圆角的图片(带错误，占位，空等图片)
     *
     * @param context
     * @param url
     * @param radius
     * @param imageView
     * @param fallbackResource
     */
    public void displayRadiusCircle(Context context, Object url, int radius, ImageView imageView, int fallbackResource) {
        GlideApp.with(context)
                .asDrawable()
                .load(url)
                .error(fallbackResource)
                .placeholder(fallbackResource)
                .fallback(fallbackResource)
                .transform(new RoundedCorners(radius))
                .into(imageView);
    }


}
