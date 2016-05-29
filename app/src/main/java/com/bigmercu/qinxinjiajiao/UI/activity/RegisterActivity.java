package com.bigmercu.qinxinjiajiao.UI.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bigmercu.qinxinjiajiao.R;
import com.bigmercu.qinxinjiajiao.contract.registerContract;
import com.bigmercu.qinxinjiajiao.presenter.impl.RegisterPresenterImpl;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import sj.mblog.L;

public class RegisterActivity extends AppCompatActivity implements registerContract.View {

    @Bind(R.id.input_phoneNum)
    TextInputLayout inputLayout_phone;
    @Bind(R.id.input_YZCode)
    TextInputLayout inputLayout_code;
    @Bind(R.id.input_password)
    TextInputLayout inputLayout_passwd;
    @Bind(R.id.input_passwordAgain)
    TextInputLayout getInputLayout_passwdAgain;
    @Bind(R.id.loading)
    RelativeLayout rela_load;
    @Bind(R.id.getCode)
    Button getCode;
    @Bind(R.id.yes2)
    ImageView yes2;
    @Bind(R.id.yes4)
    ImageView yes3;
    @Bind(R.id.submit_re)
    Button submit;

    private registerContract.Presenter presenter;
    public boolean identify;
    private static final String EMAIL_PATTERN = "^\\w{6,12}$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;
    private String phone;
    private Subscription subscription = null;
    private int num = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        new RegisterPresenterImpl(this);
        identify = getIntent().getExtras().getBoolean("identify");
        inputLayout_phone.setHint("手机号");
        inputLayout_code.setHint("验证码");
        inputLayout_passwd.setHint("密码");
        getInputLayout_passwdAgain.setHint("确认密码");
        setEnable(true,true,true,true,true,false);
        input();
    }

    @OnClick(R.id.getCode)
    public void getCode() {
        if (getCode.getText().equals("获取验证码")) {
            String phone = String.valueOf(inputLayout_phone.getEditText().getText());
            if(pattern.matcher(phone).matches()) {
                getCode.setEnabled(false);
                subscription = Observable.interval(1000, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Long>() {
                            @Override
                            public void onCompleted() {
                                L.d("complete");
                            }

                            @Override
                            public void onError(Throwable e) {
                                L.d("error" + e.getMessage());

                            }

                            @Override
                            public void onNext(Long aLong) {
//                                L.d(aLong);
                                int time = 180 - aLong.intValue();
                                getCode.setText("获取(" + time + "s)");
                                if (aLong == 180 && !subscription.isUnsubscribed()) {
                                    getCode.setEnabled(true);
                                    getCode.setBackgroundResource(R.drawable.bg_btn_yes);
                                    getCode.setText("再次获取");
                                    subscription.unsubscribe();
                                    num++;
                                }

                            }
                        });
                getCode.setBackgroundResource(R.drawable.bg_btn_no);
                setEnable(true, false, true, true, true, true);
            }else {
                Snackbar.make(submit,"请核对您的电话号码",Snackbar.LENGTH_LONG).show();
            }
        }else if(getCode.getText().equals("确定")) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            SupportAnimator animator = ViewAnimationUtils.createCircularReveal(rela_load,displayMetrics.widthPixels/2,
                            displayMetrics.heightPixels/2,displayMetrics.widthPixels,displayMetrics.heightPixels);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(1500);
            animator.start();
//            rela_load.setVisibility(View.VISIBLE);
            presenter.verifyCode(getCode.getText().toString());
        }
    }

    public void input(){

        inputLayout_phone.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 11)
                {
                    if(getCode.getText().equals("获取验证码")) {
                        getCode.setBackgroundResource(R.drawable.bg_btn_yes);
                        getCode.setEnabled(true);
                    }
                }else {
                    if(getCode.getVisibility() == View.VISIBLE)
                    {
                        getCode.setBackgroundResource(R.drawable.bg_btn_no);
                        getCode.setEnabled(false);
                    }
                }
            }
        });

        inputLayout_code.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 6)
                {
                    presenter.verifyCode(s.toString());
//                    getCode.setBackgroundResource(R.drawable.bg_btn_yes);
//                    getCode.setEnabled(true);
//                }else {
//                    getCode.setBackgroundResource(R.drawable.bg_btn_no);
//                    getCode.setEnabled(false);
                }
            }
        });

        inputLayout_passwd.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                matcher = pattern.matcher(s);
                if(!matcher.matches()){
                    inputLayout_passwd.setError("密码必须大于6位");
                }else{
                    inputLayout_passwd.setError(null);
                }
            }
        });

        getInputLayout_passwdAgain.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                matcher = pattern.matcher(s);
                String a = String.valueOf(inputLayout_passwd.getEditText().getText());
                String b = String.valueOf(s);
                if(a.equals(b)&& matcher.matches()){
                    submit.setBackgroundResource(R.drawable.bg_btn_yes);
                    submit.setEnabled(true);
                    yes3.setVisibility(View.VISIBLE);
                }else {
                    submit.setBackgroundResource(R.drawable.bg_btn_no);
                    submit.setEnabled(false);
                }
            }
        });
    }


    private void setEnable(boolean a,boolean b,boolean c,boolean d,boolean e,boolean f){
        inputLayout_phone.getEditText().setEnabled(a);
        getCode.setEnabled(b);
        inputLayout_code.getEditText().setEnabled(c);
        inputLayout_passwd.getEditText().setEnabled(d);
        getInputLayout_passwdAgain.getEditText().setEnabled(e);
        submit.setEnabled(f);
    }


    @OnClick(R.id.submit_re)
    public void submit(){
        submit.setEnabled(false);
        inputLayout_passwd.getEditText().setEnabled(false);
        getInputLayout_passwdAgain.getEditText().setEnabled(false);
        rela_load.setVisibility(View.VISIBLE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float finalRadius = (float) Math.hypot(displayMetrics.widthPixels/2, displayMetrics.heightPixels/2);
        SupportAnimator animator = ViewAnimationUtils
                .createCircularReveal(rela_load,displayMetrics.widthPixels/2,
                        displayMetrics.heightPixels/2,100,finalRadius);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(1000);
        animator.start();
        String identifY;
        if(identify)
            identifY = "parent";
        else
            identifY = "tutor";
        phone =  inputLayout_phone.getEditText().getText().toString();
        presenter.uploadInfo(identifY,
               phone,
                inputLayout_passwd.getEditText().getText().toString());
    }

    @Override
    public void setYZCode(String code) {
        inputLayout_code.getEditText().setText(code);
    }


    @Override
    public void hideDialog(String message) {
        yes3.setVisibility(View.INVISIBLE);
        yes2.setVisibility(View.INVISIBLE);
        submit.setEnabled(false);
        if(!message.equals(""))
            Snackbar.make(submit,message,Snackbar.LENGTH_LONG).show();
        getCode.setVisibility(View.VISIBLE);
        setEnable(true,false,false,true,true,true);
        rela_load.setVisibility(View.GONE);
    }

    @Override
    public void startActivity() {
        Intent i = new Intent(RegisterActivity.this,identityVerify.class);
        i.putExtra("phone",phone);
        startActivity(i);
    }

    @Override
    public void onFiled(String msg) {
        Snackbar.make(submit,msg,Snackbar.LENGTH_INDEFINITE).show();
    }

    @Override
    public void VerifyCode(boolean isOk) {
        if(isOk){
            rela_load.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),"校验成功",Toast.LENGTH_SHORT).show();
            yes2.setVisibility(View.VISIBLE);
            getCode.setVisibility(View.INVISIBLE);
            inputLayout_phone.getEditText().setEnabled(false);
            inputLayout_code.getEditText().setEnabled(false);
//                inputLayout_passwd.getEditText().setEnabled(true);
//                getInputLayout_passwdAgain.getEditText().setEnabled(true);
        }else {
            rela_load.setVisibility(View.GONE);
            Snackbar.make(submit,"校验码校验失败，请核对后再次提交",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription !=null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
       if(keyCode == KeyEvent.KEYCODE_BACK){
           hideDialog("");
           presenter.cancle();
           return true;
       }
        return false;
    }

    @Override
    public void setPresenter(registerContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
