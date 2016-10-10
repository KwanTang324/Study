package io.tanjundang.study.knowledge.launchmode;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import io.tanjundang.study.R;
import io.tanjundang.study.base.BaseActivity;

import static io.tanjundang.study.knowledge.launchmode.TwoActivity.SkipToActivityTwo;

public class OneActivity extends BaseActivity {

    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_single_top_one);
        TextView tvTest = (TextView) findViewById(R.id.tvTest);
        tvTest.setText("当前Activity实例：" + this);
    }

    @Override
    protected void initData() {
        type = getIntent().getStringExtra("type");
    }

    public void SkipToTwo(View v) {
        SkipToActivityTwo(this, type);
    }

    public static void SkipToActivityOne(Context context, String type) {
        Intent intent = new Intent(context, OneActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

}
