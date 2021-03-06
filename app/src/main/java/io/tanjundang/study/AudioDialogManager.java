package io.tanjundang.study;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.tanjundang.study.common.tools.Functions;

/**
 * @Author: TanJunDang
 * @Date: 2018/3/9
 * @Description:
 */
public class AudioDialogManager {
    AlertDialog mDialog;
    AlertDialog.Builder builder;
    View rootview;
    TextView tvContent;
    ImageView ivRecall;
    ImageView ivVoice;
    RelativeLayout rlRecord;

    public AudioDialogManager(Context context) {
        rootview = LayoutInflater.from(context).inflate(R.layout.view_audio_dialog, null);
        tvContent = (TextView) rootview.findViewById(R.id.tvContent);
        rlRecord = (RelativeLayout) rootview.findViewById(R.id.rlRecord);
        ivRecall = (ImageView) rootview.findViewById(R.id.ivRecall);
        ivVoice = (ImageView) rootview.findViewById(R.id.ivVoice);
        builder = new AlertDialog.Builder(context, R.style.CustomDialog);
        builder.setCancelable(false);
        builder.setView(rootview);
        mDialog = builder.create();
    }

    public void showRecordingDialog() {
        tvContent.setText("松开结束");
        mDialog.show();
    }

    /**
     * @param level 1-7
     */
    public void updateVoiceLevel(int level) {
        if (level == 0) level = 1;
        ivVoice.setImageResource(Functions.getResourceIdByString("ic_voice_" + level, R.drawable.ic_voice_1));
    }

    public void modifyText(int resid) {
        switch (resid) {
            case R.string.str_audio_btn_normal:

                break;
            case R.string.str_audio_btn_recording:
//                停止录音、保存文件
                rlRecord.setVisibility(View.VISIBLE);
                ivRecall.setVisibility(View.GONE);
                break;
            case R.string.str_audio_btn_want_to_cancel:
//                取消录音
                rlRecord.setVisibility(View.GONE);
                ivRecall.setVisibility(View.VISIBLE);
                ivRecall.setImageResource(R.drawable.ic_audio_recall);
                break;
            case R.string.str_audio_time_short:
                rlRecord.setVisibility(View.GONE);
                ivRecall.setVisibility(View.VISIBLE);
                ivRecall.setImageResource(R.drawable.ic_audio_time_short);
                break;
        }
        tvContent.setText(resid);
    }

    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }
}


