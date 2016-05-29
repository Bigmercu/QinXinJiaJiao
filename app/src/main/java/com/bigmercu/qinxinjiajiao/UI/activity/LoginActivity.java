package com.bigmercu.qinxinjiajiao.UI.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigmercu.qinxinjiajiao.R;
import com.bigmercu.qinxinjiajiao.Util.DeviceUuidFactory;
import com.bigmercu.qinxinjiajiao.contract.loginContract;
import com.bigmercu.qinxinjiajiao.dao.DaoMaster;
import com.bigmercu.qinxinjiajiao.dao.DaoSession;
import com.bigmercu.qinxinjiajiao.dao.Person;
import com.bigmercu.qinxinjiajiao.dao.PersonDao;
import com.bigmercu.qinxinjiajiao.presenter.impl.LoginPresenterImpl;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sj.mblog.L;

public class LoginActivity extends AppCompatActivity implements loginContract.View {

    @Bind(R.id.input_passwd)
    TextInputLayout inputPasswd;
    @Bind(R.id.input_id)
    TextInputLayout inputID;
    @Bind(R.id.loading)
    RelativeLayout loading;
    @Bind(R.id.btn_login)
    Button login;
    @Bind(R.id.login_view)
    LinearLayout loginView;
    @Bind(R.id.createAcc)
    TextView create;
    @Bind(R.id.forgetPasswd)
    TextView forget;
    private loginContract.Presenter loginPresenter;
    public static UUID uuid;
    private String phone;
    private String password;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        inputID.setHint("手机号");
        inputPasswd.setHint("密码");
        new LoginPresenterImpl(this);
    }


    @OnClick(R.id.btn_login)
    public void Login(){
        Map<String,String> map = new HashMap<String, String>();
        DeviceUuidFactory deviceUuidFactory = new DeviceUuidFactory(this);
        uuid = deviceUuidFactory.getDeviceUuid();
        phone = String.valueOf(inputID.getEditText().getText());
        password = String.valueOf(inputPasswd.getEditText().getText());
        map.put("phone",phone);
        map.put("password",password);
        map.put("uuid", String.valueOf(uuid));
        loginPresenter.Login(map);
        sh(false);
    }
    @OnClick(R.id.createAcc)
    public void CreateAcc(){
        startActivity(new Intent(this,FirstActivity.class));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(loading.getVisibility() == View.VISIBLE) {
                sh(true);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void showAndHide(boolean option) {
//      sh(option);
    }


    public void  sh(boolean option){
        if(option) {
            loading.setVisibility(View.GONE);
        }else {
            loading.setVisibility(View.VISIBLE);
        }
        inputPasswd.getEditText().setEnabled(option);
        inputID.getEditText().setEnabled(option);
        login.setEnabled(option);
        create.setEnabled(option);
        forget.setEnabled(option);
    }


    @Override
    public void Onsuccess() {
        Intent i = new Intent(LoginActivity.this,bigmercuActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putParcelable("loginEntity",data);
//        i.putExtras(bundle);
        DaoMaster.DevOpenHelper db = new DaoMaster.DevOpenHelper(this, "PERSON", null);
        DaoMaster daoMaster = new DaoMaster(db.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        PersonDao personDao = daoSession.getPersonDao();
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM);
        String comment = "添加于:" + df.format(new Date());
        Person person = new Person(null,phone,String.valueOf(uuid),password,new Date(),comment);
        personDao.insert(person);
        L.d(person.getId());
        startActivity(i);
        finish();
        overridePendingTransition(R.animator.s_in,R.animator.s_out);
    }

    @Override
    public void OnFiled(String msg) {
        Snackbar.make(loginView,"登录失败,"+msg,Snackbar.LENGTH_LONG).show();
        sh(true);
    }

    @Override
    public void setPresenter(loginContract.Presenter presenter) {
        L.d("getPresenter");
        loginPresenter = presenter;
    }
}
