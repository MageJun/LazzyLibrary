package com.lazzy.common.lib.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lazzy.common.lib.GlobalInit;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * View视图帮助类
 */
public class ViewHelper {


    private static final String TAG = ViewHelper.class.getSimpleName();

    public static void setViewTouchRect(final View view, final int width, final int height){
        View parent = (View) view.getParent();
        parent.post(new Runnable() {
            @Override
            public void run() {
                Rect delegateArea = new Rect();
                view.getHitRect(delegateArea);
                delegateArea.left-=width;
                delegateArea.bottom+=height;
                delegateArea.top-=height;
                delegateArea.right+=width;

                TouchDelegate delegate = new TouchDelegate(delegateArea,view);
                if(View.class.isInstance(view.getParent())){
                    ((View)view.getParent()).setTouchDelegate(delegate);
                }

            }
        });
    }


    /**
     * 禁止EditText输入特殊字符
     *
     * @param editText EditText输入框
     */
    public static void setEditTextInputSpeChat(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
                Pattern pattern = Pattern.compile(speChat);
                Matcher matcher = pattern.matcher(source.toString());
                if (matcher.find()) {
                    return "";
                } else {
                    return null;
                }
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    /**
     * 禁止EditText输入换行符
     *
     */
    public static InputFilter getEditTextFilterEnter() {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.toString().contentEquals("\n")) {
                    return "";
                } else {
                    return null;
                }
            }
        };
       return filter;
    }

    public static void setText(TextView tv, String text){
        if(tv!=null&&!TextUtils.isEmpty(text)){
            tv.setText(text);
        }
    }

    public static void setText(TextView tv,int rid){
        setText(tv,ResourceHelper.getStr(rid));
    }

    public static void setImgview(ImageView img, String url){
        if(!TextUtils.isEmpty(url)&&img!=null){
            Glide.with(GlobalInit.appContext()).load(url).apply(RequestOptions.centerCropTransform()).into(img);
        }
    }

    public static void setImgviewCenterInside(ImageView img,String filePath){
        if(!TextUtils.isEmpty(filePath)&&img!=null){
            Glide.with(GlobalInit.appContext()).load(filePath).apply(RequestOptions.centerInsideTransform()).into(img);
        }
    }
    public static void setImgview(ImageView img,int rid){
        if(img!=null){
            Glide.with(GlobalInit.appContext()).load(rid).into(img);
        }
    }

    public static void setImgViewAdjust(final ImageView img, String filePath,boolean isCache){
        if(!TextUtils.isEmpty(filePath)&&img!=null){
            RequestOptions options = RequestOptions.bitmapTransform(new RoundedCorners(15));
            if(!isCache){
                options.diskCacheStrategy(DiskCacheStrategy.NONE);//不使用磁盘缓存
                options.skipMemoryCache(true);//不适用内存缓存
            }
            int width = img.getWidth();
            int height = img.getHeight();
            Log.i(TAG,"setImgVIewAdjust width = "+width+",height = "+height);
//            options.transform(new RoundedCorners(5));
            Glide.with(GlobalInit.appContext()).asBitmap().load(filePath).apply(options).into(new SimpleTarget<Bitmap>(width,height) {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    int width = resource.getWidth();
                    int height = resource.getHeight();
                    if(img.getScaleType() != ImageView.ScaleType.FIT_XY) {
                        img.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                    /* ViewGroup.LayoutParams params = img.getLayoutParams();
                    params.width = img.getWidth()-img.getPaddingLeft()-img.getPaddingRight();
                    params.height = img.getWidth() *(height/width);*/
                    img.setImageBitmap(resource);
                }
            });
        }
    }

    public static void setImgViewAdjustWidth(final ImageView img, int rid,boolean isCache){
        if(img!=null){
            RequestOptions options = RequestOptions.bitmapTransform(new RoundedCorners(100));
            if(!isCache){
                options.diskCacheStrategy(DiskCacheStrategy.NONE);//不使用磁盘缓存
                options.skipMemoryCache(true);//不适用内存缓存
            }

//            options.transform(new RoundedCorners(5));
            Glide.with(GlobalInit.appContext()).asBitmap().load(rid).apply(options).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    int width = resource.getWidth();
                    int height = resource.getHeight();
                    ViewGroup.LayoutParams params = img.getLayoutParams();
                    params.width = img.getWidth()-img.getPaddingLeft()-img.getPaddingRight();
                    params.height = img.getWidth() *(height/width);
                    img.setImageBitmap(resource);
                }
            });
        }
    }

    public static void setImgViewAdjustWidth(final ImageView img, String url,boolean isCache){
        if(!TextUtils.isEmpty(url)&&img!=null){
            RequestOptions options = new RequestOptions();
            if(!isCache){
                options.diskCacheStrategy(DiskCacheStrategy.NONE);//不使用磁盘缓存
                options.skipMemoryCache(true);//不适用内存缓存
            }
            int width = img.getWidth();
            int height = img.getHeight();
            Log.i(TAG,"setImgViewAdjustWidth() width = "+width+",height = "+height);
//            options.transform(new RoundedCorners(5));
            Glide.with(GlobalInit.appContext()).asBitmap().load(url).apply(options).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    int width = resource.getWidth();
                    int height = resource.getHeight();
                    Log.i(TAG,"setImgViewAdjustWidth()#onResourceReady width = "+img.getWidth()+",height = "+img.getHeight()+", img = "+img.hashCode());
                    ViewGroup.LayoutParams params = img.getLayoutParams();
                    params.height =img.getWidth()*(height/width);
                    params.width = img.getWidth();
                    img.setImageBitmap(resource);
                }
            });
        }
    }

    public static void setImgViewAdjust(final ImageView img, final String url,int placeRid, final int width, final int height, boolean isCache){
        if(!TextUtils.isEmpty(url)&&img!=null){
            RequestOptions options = new RequestOptions();
            if(!isCache){
                options.diskCacheStrategy(DiskCacheStrategy.NONE);//不使用磁盘缓存
                options.skipMemoryCache(true);//不适用内存缓存
            }
            if(placeRid>0){
                options.placeholder(placeRid);
            }
            Log.i(TAG,"setImgViewAdjust() width = "+width+",height = "+height+", img = "+img.hashCode());
            Glide.with(GlobalInit.appContext()).asBitmap().load(url).apply(options).into(new SimpleTarget<Bitmap>(width,height) {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    Log.i(TAG,"setImgViewAdjust()#onResourceReady width = "+resource.getWidth()+",height = "+resource.getHeight()+", img = "+img.hashCode());
                    if(img.getScaleType()!= ImageView.ScaleType.FIT_XY){
                        img.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                    double radio = ResourceHelper.getRadio(height,width);
                    ViewGroup.LayoutParams params = img.getLayoutParams();
                    params.height= (int) (img.getWidth()*radio);
                    img.setImageBitmap(resource);
                }
            });
        }
    }

    public static void setCircleImgView(ImageView img,String url){
        if(!TextUtils.isEmpty(url)&&img!=null){
            Glide.with(GlobalInit.appContext()).load(url).apply(RequestOptions.bitmapTransform((new CircleCrop()))).into(img);
        }
    }


    public static boolean checkEmail(String string){
        String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        if (TextUtils.isEmpty(strPattern)) {
            return false;
        } else {
            return string.matches(strPattern);
        }
    }


    /**
     * 获取状态栏高度
     * @return
     */
    public static int getStatusBar(){
        /**
         * 获取状态栏高度
         * */
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = GlobalInit.appContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = GlobalInit.appContext().getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }



    private void settab(TabLayout mDetailTablayout) throws NoSuchFieldException, IllegalAccessException {
        Class<?> tablayout = mDetailTablayout.getClass();
        Field tabStrip = tablayout.getDeclaredField("mTabStrip");
        tabStrip.setAccessible(true);
        LinearLayout ll_tab = (LinearLayout) tabStrip.get(mDetailTablayout);
        for (int i = 0; i < ll_tab.getChildCount(); i++) {
            View child = ll_tab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
//            params.setMarginStart(UiUtils.dip2px(mActivity, 25f));
//            params.setMarginEnd(UiUtils.dip2px(mActivity, 25f));
            child.setLayoutParams(params);
            child.invalidate();
        }
    }


    public static BitmapFactory.Options getBitmapOptions(String url){
        if(TextUtils.isEmpty(url)){
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            URL urll = new URL(url);
            try {
                BitmapFactory.decodeStream(urll.openStream(),null,options);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        return options;
    }
}
