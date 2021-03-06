package io.tanjundang.study.common.tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.tanjundang.study.BuildConfig;
import io.tanjundang.study.MainActivity;
import io.tanjundang.study.R;
import io.tanjundang.study.base.BaseApplication;


/**
 * Developer: TanJunDang
 * Email: TanJunDang324@gmail.com
 */
public class Functions {

    public static int MALE = 0;//男
    public static int FEMALE = 1;//女
    public static int REQ_PICTURE = 0XFF;
    private static Context appContext;

    public static void init(Context context) {
        appContext = context;
    }

    public static void toast(CharSequence text) {
        if (appContext == null) {
            return;
        }
        Toast.makeText(appContext, text, Toast.LENGTH_SHORT).show();
    }

    public static void snack(View view, CharSequence text) {
        if (view == null) {
            return;
        }
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
    }

    public static void snack(View view, CharSequence mainText, CharSequence actionText, final View.OnClickListener listener) {
        if (view == null) {
            return;
        }
        Snackbar.make(view, mainText, Snackbar.LENGTH_SHORT)
                .setAction(actionText, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            listener.onClick(view);
                        }
                    }
                })
                //还可以通过Color.parse来修改颜色
                .setActionTextColor(Color.parseColor("#2ecc71"))
                .show();
    }


    /**
     * 隐藏软键盘
     *
     * @param view
     */
    public static void hideInputMethod(View view) {
        InputMethodManager imm = (InputMethodManager) appContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     *
     * @param view
     */
    public static void showInputMethodForQuery(View view) {

        InputMethodManager imm = (InputMethodManager) appContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, 0);
        }
    }


    /**
     * 根据名字获取对应的图片资源id
     * 不需要R.drawable前缀
     *
     * @param s
     * @return
     */
    public static int getResourceIdByString(String s, int defaultResId) {
        try {
            Field field = R.drawable.class.getField(s);
            return Integer.parseInt(field.get(null).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultResId;
    }

    /**
     * 此方法只是获取手机的当前时间
     * 必须要加上服务器下发的时间跟系统的时间差(offset = serverTime - systemTime)。
     *
     * @return
     */
    public static long getCurrentFixTime() {
        int offset = 0;
        return Calendar.getInstance().getTimeInMillis() + offset;
    }

    /**
     * 获取屏幕的高度
     *
     * @return
     */
    public static int getScreenHeight() {
        WindowManager manager = (WindowManager) appContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getScreenWidth() {
        WindowManager manager = (WindowManager) appContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * dp 转 px
     *
     * @param dpVal
     * @return
     */
    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, appContext.getResources().getDisplayMetrics());
    }


    /**
     * px 转  dp
     *
     * @param pxVal
     * @return
     */
    public static float px2dp(float pxVal) {
        final float scale = appContext.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    // 通过文件头来判断是否gif
    public static boolean isGifByFile(File file) {
        try {
            int length = 10;
            InputStream is = new FileInputStream(file);
            byte[] data = new byte[length];
            is.read(data);
            String type = getType(data);
            is.close();

            if (type.equals("gif")) {
                return true;
            }
        } catch (Exception e) {
            LogTool.v("GIF", e.getMessage());
        }

        return false;
    }

    private static String getType(byte[] data) {
        String type = "";
        if (data[1] == 'P' && data[2] == 'N' && data[3] == 'G') {
            type = "png";
        } else if (data[0] == 'G' && data[1] == 'I' && data[2] == 'F') {
            type = "gif";
        } else if (data[6] == 'J' && data[7] == 'F' && data[8] == 'I'
                && data[9] == 'F') {
            type = "jpg";
        }
        return type;
    }

    /**
     * 根据值来显示男女
     */

//    public static String getStringByGender(Context context, int gender) {
//        Resources resources = context.getResources();
//        String sex = "";
//        if (gender == MALE) {
//            sex = resources.getString(R.string.common_sex_male);
//        } else {
//            sex = resources.getString(R.string.common_sex_female);
//        }
//        return sex;
//    }

    /**
     * 将一个String集合进行逗号拼装
     *
     * @param list
     * @return
     */
    public static String getStringListByPoint(ArrayList<String> list) {
        String result = "";
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            String text = (String) iterator.next();
            result += text + ",";
        }
        result = result.substring(0, result.length() - 1);
        return result;
    }

    /**
     * 判断是否以某个字符结尾,是则去掉字符串最后一个字符
     *
     * @param str
     * @return
     */
    public static String replaceLastCharByEmpty(String str, String Char) {
        String result = str.endsWith(Char) ? str.substring(0, str.length() - 1) : str;
        return result;
    }


    /**
     * 获取图片的File路径
     *
     * @param uri
     * @return
     */
    @SuppressLint("NewApi")
    private static String getPath(final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(appContext, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(appContext, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(appContext, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(appContext, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

//    /**
//     * 需要在onactivityresult里用REQ_PICTURE接收本地图片数据
//     *
//     * @param context
//     */
//    public static void SkipToImagePickActivity(Context context) {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        intent.setType("image/*");
//        ((Activity) context).startActivityForResult(Intent.createChooser(intent, context.getString(R.string.pick_title_choose_picture)), REQ_PICTURE);
//    }

    /**
     * 从Intent获取本地path
     * 用法：用在onActivityResult里
     *
     * @param context
     * @param data
     * @return
     */
//    public static void getImageFromIntent(Context context, Intent data, PictureCallBack callback) {
//        String path = "";
//        Uri uri = data.getData();
//        path = Functions.getPath(context, uri);
//        upload(path, callback);
//    }

    /**
     * 判断SD卡是否存在
     *
     * @return
     */
    public static boolean isSDCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 输入文件夹、文件名，返回一个路径处理好的文件
     *
     * @param folder
     * @param fileName 需要自行添加后缀
     * @return
     */
    public static File getSDCardFile(String folder, String fileName) {
        String sdPath = "";
//        获取sd卡路径,如果没有sd卡路径,就在/data/data下创建
        if (isSDCardExist()) {
            sdPath = Environment.getExternalStorageDirectory() + "/Study_Master/";
        } else {
            sdPath = Environment.getDataDirectory() + "/Study_Master/";
        }
        File fileDir = new File(sdPath + folder);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        File file = new File(fileDir, fileName);
        return file;
    }

    public static File getSDCardFolder(String folder) {
        String sdPath = "";
//        获取sd卡路径,如果没有sd卡路径,就在/data/data下创建
        if (isSDCardExist()) {
            sdPath = Environment.getExternalStorageDirectory() + "/Study_Master/";
        } else {
            sdPath = Environment.getDataDirectory() + "/Study_Master/";
        }
        File fileDir = new File(sdPath + folder);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return fileDir;
    }

    public static String getSDCardPath() {
        String sdPath = "";
//        获取sd卡路径,如果没有sd卡路径,就在/data/data下创建
        if (isSDCardExist()) {
            sdPath = Environment.getExternalStorageDirectory() + "/Study_Master/";
        } else {
            sdPath = Environment.getDataDirectory() + "/Study_Master/";
        }
        return sdPath;
    }

    /**
     * 查看APK版本 展示所用 1.2.0
     */
    public static String getAppVersionName() {
        String versionName = "";
//        PackageManager manager = context.getPackageManager();
//        try {
//            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
//            versionName = info.versionName;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
        versionName = BuildConfig.VERSION_NAME;
        return versionName;
    }

    public static String getChannel() {
        String chanel = "Offcial";
        String gitVersion = "";
        try {
            ApplicationInfo info = appContext.getPackageManager().getApplicationInfo(appContext.getPackageName(), PackageManager.GET_META_DATA);
            gitVersion = info.metaData.getString("GIT");
//            chanel = info.metaData.getString("UMENG_CHANNEL");
//            chanel = String.format("%s-%s-%s", chanel, gitVersion, BuildConfig.BUILD_TYPE);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        chanel = String.format("%s-%s-%s", BuildConfig.FLAVOR, gitVersion, BuildConfig.BUILD_TYPE);
        return chanel;
    }

    /**
     * 获取版本ID 用于与服务端版本号对比，从而决定是否需要升级
     * <p/>
     * 可使用BuildType直接获取版本号、版本、渠道号、BuildType等数据
     *
     * @return
     */
    public static int getAppVersionCode() {
        int versionCode = 0;
//        PackageManager manager = appContext.getPackageManager();
//        try {
//            PackageInfo info = manager.getPackageInfo(appContext.getPackageName(), 0);
//            versionCode = info.versionCode;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
        versionCode = BuildConfig.VERSION_CODE;
        return versionCode;
    }

    public static void SkipToAppDetail() {
        //跳转到当前应用详情页面
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.fromParts("package", appContext.getPackageName(), null));
        appContext.startActivity(intent);
    }

    /**
     * 获取文件夹的大小
     * 单位是M
     *
     * @param file
     * @return 清除缓存功能的实现
     * 1.编写计算文件夹大小的方法
     * 2.计算内部存储（getCacheDir、getFileDir）、外部存储(getExtralCacheDir、getExtralFileDIr)、SharePreference、数据库的文件夹大小
     * 3.删除这些文件
     * 数据库路径： "/data/data/"+getActivity().getPackageName() + "/databases"
     * SharePrefence路径："/data/data/"getActivity().getPackageName() + "/shared_prefs"
     */
    public static double getFileDirSize(File file) {
        double size = 0;
        //如果文件不存在，则直接返回
        if (file.exists()) {
            //如果是文件，则直接返回其大小
            if (!file.isDirectory()) {
                size = (double) file.length() / 1024 / 1024;
            } else {
                //如果是文件夹，则遍历递归该文件夹
                File[] dir = file.listFiles();
                for (File childFile : dir) {
                    size += getFileDirSize(childFile);
                }
            }
        } else {
            return size;
        }
        return size;
    }

    public static double getCacheSize() {
        return getFileDirSize(appContext.getFilesDir())
                + getFileDirSize(appContext.getCacheDir())
                + getFileDirSize(new File("/data/data/" + appContext.getPackageName() + "/shared_prefs"))
                + getFileDirSize(new File("/data/data/" + appContext.getPackageName() + "/databases"));
    }


    private static void deleteAllFile(File dir) {
        if (dir.exists()) {
            if (dir.isDirectory()) {
                File[] files = dir.listFiles();
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteAllFile(file.getAbsoluteFile());
                    } else {
                        file.delete();
                    }
                }
            } else {
                dir.delete();
            }
        }
    }

    public static void clearCache() {
        deleteAllFile(appContext.getFilesDir());
        deleteAllFile(appContext.getCacheDir());
        deleteAllFile(new File("/data/data/" + appContext.getPackageName() + "/shared_prefs"));
        deleteAllFile(new File("/data/data/" + appContext.getPackageName() + "/databases"));
    }

    public static void startActivity(Class cls) {
        Intent intent = new Intent(appContext, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        appContext.startActivity(intent);
    }

    /**
     * 根据url获取文件名
     *
     * @param url
     * @return
     */
    public static String getFileNameFromUrlNoTail(String url) {
        String fileName = url.substring(url.lastIndexOf('/') + 1);
        String result = removeFileTail(fileName);
        return result;
    }


    public static String getFileNameFromUrl(String url) {
        String result = url.substring(url.lastIndexOf('/') + 1);
        return result;
    }


    public static String getTailFromFileName(String fileName) {
        String suffixes = "avi|mpeg|3gp|mp3|mp4|wav|jpeg|gif|jpg|png|apk|exe|pdf|rar|zip|docx|doc";
        Pattern pat = Pattern.compile("[\\w]+[\\.](" + suffixes + ")");//正则判断
        Matcher mc = pat.matcher(fileName);//条件匹配
        while (mc.find()) {
            String substring = mc.group();//截取文件名后缀名
            return substring;
        }
        return "";
    }

    public static String removeFileTail(String fileName) {
        int index = fileName.indexOf(".");
        String result = fileName.substring(0, index);
        return result;
    }

//    public static File uri2File(Context context, Uri uri) {
//        String path = null;
//        if ("file".equals(uri.getScheme())) {
//            path = uri.getEncodedPath();
//            if (path != null) {
//                path = Uri.decode(path);
//                ContentResolver cr = context.getContentResolver();
//                StringBuffer buff = new StringBuffer();
//                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=").append("'" + path + "'").append(")");
//                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA}, buff.toString(), null, null);
//                int index = 0;
//                int dataIdx = 0;
//                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
//                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
//                    index = cur.getInt(index);
//                    dataIdx = cur.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//                    path = cur.getString(dataIdx);
//                }
//                cur.close();
//                if (index == 0) {
//                } else {
//                    Uri u = Uri.parse("content://media/external/images/media/" + index);
//                    System.out.println("temp uri is :" + u);
//                }
//            }
//            if (path != null) {
//                return new File(path);
//            }
//        } else if ("content".equals(uri.getScheme())) {
//            // 4.2.2以后
//            String[] proj = {MediaStore.Images.Media.DATA};
//            Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
//            if (cursor.moveToFirst()) {
//                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                path = cursor.getString(columnIndex);
//            }
//            cursor.close();
//
//            return new File(path);
//        } else {
//            //Log.i(TAG, "Uri Scheme:" + uri.getScheme());
//        }
//        return null;
//    }
//
//    public static Uri file2Uri(Context context, File imageFile) {
//        String filePath = imageFile.getAbsolutePath();
//        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=? ",
//                new String[]{filePath}, null);
//        if (cursor != null && cursor.moveToFirst()) {
//            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
//            Uri baseUri = Uri.parse("content://media/external/images/media");
//            return Uri.withAppendedPath(baseUri, "" + id);
//        } else {
//            if (imageFile.exists()) {
//                ContentValues values = new ContentValues();
//                values.put(MediaStore.Images.Media.DATA, filePath);
//                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//            } else {
//                return null;
//            }
//        }
//    }

}
