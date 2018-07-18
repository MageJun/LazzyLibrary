package com.android.kotlindemo.view.activity

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import com.android.kotlindemo.R
import com.lazzy.common.lib.utils.*
import kotlinx.android.synthetic.main.activity_image_show.*

class ImageShowActivity : BaseActivity() {
    override fun onActivityCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_image_show)
    }

    override fun onActivityDestory() {
    }

    override fun initView() {
        val url = intent?.extras?.get("img_url") as String
        val task = BitmapTask(url)
        val screenWidth = ScreenUtils.getScreenWidth(this)
        task.setCallBack(Handler.Callback { msg ->
            if (msg != null) {
                if (msg.obj != null && msg.obj is BitmapFactory.Options) {
                    val optionsl = msg.obj as BitmapFactory.Options
                    val urll = msg.data.getString("url")
                        Log.i("ViewHelper", "options.outHeight = " + optionsl.outHeight + ",options.outWidth = " + optionsl.outWidth)
                        val height = (screenWidth * ResourceHelper.getRadio(optionsl?.outHeight!!.toDouble(), optionsl?.outWidth!!.toDouble())).toInt()
                        ViewHelper.setImgViewAdjust(img, urll, 0,screenWidth, height, true)
                }
            }
            false
        })
        ThreadPoolProxyFactory.getNormalThreadPoolProxy().execute(task)
    }
}
