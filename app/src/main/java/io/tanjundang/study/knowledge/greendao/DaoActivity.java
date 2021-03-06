package io.tanjundang.study.knowledge.greendao;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.tanjundang.study.R;

/**
 * @Author: TanJunDang
 * @Date: 2019/2/25 14:58
 * @Description: 数据库GreenDao的使用
 * https://www.jianshu.com/p/53083f782ea2
 */
public class DaoActivity extends AppCompatActivity {

    @BindView(R.id.btnInsert)
    Button btnInsert;
    @BindView(R.id.loadData)
    Button loadData;
    @BindView(R.id.btnDel)
    Button btnDel;
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.btnDelByKey)
    Button btnDelByKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dao);
        ButterKnife.bind(this);
    }

    long count = 0;

    @OnClick({R.id.btnInsert, R.id.loadData, R.id.btnDel, R.id.btnDelByKey})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnInsert:

                break;
            case R.id.loadData:

                break;
            case R.id.btnDelByKey:
                Snackbar.make(view, "删除数据成功", Snackbar.LENGTH_LONG).show();
                break;
            case R.id.btnDel:
                count = 0;
                tvContent.setText(null);
                Snackbar.make(view, "删除所有数据", Snackbar.LENGTH_LONG).show();
                break;

        }
    }

    @OnClick()
    public void onViewClicked() {
    }
}
