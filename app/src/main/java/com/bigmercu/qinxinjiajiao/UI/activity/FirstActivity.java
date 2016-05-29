package com.bigmercu.qinxinjiajiao.UI.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.bigmercu.qinxinjiajiao.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirstActivity extends AppCompatActivity{

    @Bind(R.id.click_me_BN)
    Button clickButton;
    @Bind(R.id.parent)
    Button parent;
    @Bind(R.id.teacher)
    Button teacher;

    public boolean aBoolean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        aBoolean = true;
        clickButton.setEnabled(false);
    }

    @OnClick(R.id.click_me_BN)
    public void submit(){
        Intent i = new Intent(FirstActivity.this,RegisterActivity.class);
        i.putExtra("identify",aBoolean);
        startActivity(i);
        overridePendingTransition(R.animator.push_out_right,R.animator.pull_in_left);
        finish();
    }

    @OnClick(R.id.parent)
    public void onClickP(){
        aBoolean = true;
        setBackground();
        if(!clickButton.isEnabled())
        {
            clickButton.setEnabled(true);
            clickButton.setBackgroundResource(R.drawable.bg_btn_yes);
        }
    }

    @OnClick(R.id.teacher)
    public void onClick(){
        aBoolean = false;
        setBackground();
        if(!clickButton.isEnabled())
        {
            clickButton.setEnabled(true);
            clickButton.setBackgroundResource(R.drawable.bg_btn_yes);
        }
    }

    public void setBackground(){
        if(aBoolean){
            parent.setBackgroundResource(R.drawable.bg_btn_yes);
            teacher.setBackgroundResource(R.drawable.bg_btn_no);
        }else if(!aBoolean){
            parent.setBackgroundResource(R.drawable.bg_btn_no);
            teacher.setBackgroundResource(R.drawable.bg_btn_yes);
        }
    }
}
