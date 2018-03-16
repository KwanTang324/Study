package io.tanjundang.study;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.socialize.UMShareAPI;

import java.io.IOException;
import java.util.ArrayList;

import io.tanjundang.study.R;
import io.tanjundang.study.base.BaseActivity;
import io.tanjundang.study.common.tools.Functions;
import io.tanjundang.study.common.tools.LogTool;
import io.tanjundang.study.common.tools.PermissionTool;

public class TestActivity extends BaseActivity {

    TextView tvMsg;
    ImageView ivVoice;
    private ArrayList permissionList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_test);
        tvMsg = (TextView) findViewById(R.id.tvMsg);
        ivVoice = (ImageView) findViewById(R.id.ivVoice);
    }

    @Override
    protected void initData() {
        permissionList.add(Manifest.permission.WRITE_CONTACTS);
        permissionList.add(Manifest.permission.RECORD_AUDIO);
        permissionList.add(Manifest.permission.INTERNET);
        permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    public void checkPermission(final View view) {
        PermissionTool.getInstance(this).requestPermissions(permissionList, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dothing();
                start(view);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    public void start(View v) {
        AudioRecordTool.getInstance().start();
    }

    public void voice(View v) {
    }


    public void stop(View v) {
        AudioRecordTool.getInstance().stop();
    }

    public void cancel(View v) {
        AudioRecordTool.getInstance().cancel();
    }


    public void play(View v) {
        AudioRecordTool.getInstance().play();
    }

    public void stopPlayer(View v) {
        AudioRecordTool.getInstance().stopPlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void dothing() {
        ContentResolver resolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        Cursor cursor = resolver.query(uri, null, null, null, null);
        int i = 0;
        StringBuffer sb = new StringBuffer("");
        while (cursor.moveToNext()) {
            i++;
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            sb.append("名字：" + name + "手机：" + phone + "\n");
            if (i == 10) {
                break;
            }
        }
        tvMsg.setText(sb.toString());
        Functions.toast("Success");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionTool.getInstance(this).onRequestPermissionsResult(permissionList, requestCode, permissions, grantResults);
    }
}

