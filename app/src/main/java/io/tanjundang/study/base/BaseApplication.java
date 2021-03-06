package io.tanjundang.study.base;

import android.app.Application;

import com.liulishuo.filedownloader.FileDownloader;

import cn.jpush.android.api.JPushInterface;
import io.tanjundang.study.common.tools.Functions;
import io.tanjundang.study.common.tools.ImageLoaderTool;

/**
 * Developer: TanJunDang
 * Email: TanJunDang324@gmail.com
 * Date: 2016/8/5
 */

public class BaseApplication extends Application {

    private final String QQ_ID = "1105742570";
    private final String QQ_KEY = "WbVyNv1fGXenRLoz";
    private final String WEIXIN_ID = "wx49b2fe6f13b67083";
    private final String WEIXIN_KEY = "21fbc78e0d7f1990198b37640845b5d3";
    private final String WEIBO_ID = "3688550464";
    private final String WEIBO_KEY = "570706d7dd026e57e4bffea3e7c94938";


    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderTool.initImageLoader(getApplicationContext());
        Functions.init(getApplicationContext());
        FileDownloader.setup(this);
        initJPush();
    }

    private void initJPush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
