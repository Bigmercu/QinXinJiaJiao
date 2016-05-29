package com.bigmercu.qinxinjiajiao.UI.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigmercu.qinxinjiajiao.R;
import com.bigmercu.qinxinjiajiao.contract.fullScreenContract;
import com.bigmercu.qinxinjiajiao.dao.DaoMaster;
import com.bigmercu.qinxinjiajiao.dao.DaoSession;
import com.bigmercu.qinxinjiajiao.dao.Person;
import com.bigmercu.qinxinjiajiao.dao.PersonDao;
import com.bigmercu.qinxinjiajiao.entity.MainInfoHome;
import com.bigmercu.qinxinjiajiao.presenter.impl.fullScreenPresenter;
import com.github.florent37.picassopalette.PicassoPalette;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.dao.query.QueryBuilder;
import de.hdodenhof.circleimageview.CircleImageView;
import sj.mblog.L;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity implements fullScreenContract.View {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private fullScreenContract.Presenter fullScreenPresenter;
    private String uuid;


    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    //    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
//            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Bind(R.id.start_image)
    CircleImageView imageView;
    @Bind(R.id.start_rela)
    RelativeLayout relativeLayout;
    @Bind(R.id.start_card)
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        ButterKnife.bind(this);
        mVisible = true;
//        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);
        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });
        new fullScreenPresenter(this);
        Picasso.with(getApplicationContext()).load(R.drawable.bigmercu).into(imageView,
                PicassoPalette.with(String.valueOf(R.drawable.touxiang),imageView)
                        .use(PicassoPalette.Profile.VIBRANT_DARK)
                        .intoBackground(relativeLayout)
                .use(PicassoPalette.Profile.MUTED_DARK)
                .intoBackground(cardView)
        );
        animate();
        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
//        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
//        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
        }
        super.onWindowFocusChanged(hasFocus);
    }

    public void animate() {
        //中央的logo图标
        ImageView logoImg = (ImageView) findViewById(R.id.start_image);
        //下方的文字图片
        TextView onePieceImg = (TextView) findViewById(R.id.fullscreen_content);
        //logo图标上移动画
        ObjectAnimator moveImg = ObjectAnimator.ofFloat(logoImg, "translationY", -100);
        moveImg.setDuration(1000);
        //设置延迟300毫秒开始动画
        moveImg.setStartDelay(400);
        moveImg.setInterpolator(new DecelerateInterpolator(1.5f));
        //文字图片下移动画
        ObjectAnimator onePieceAnimate = ObjectAnimator.ofFloat(onePieceImg, "translationY", 80);
        onePieceAnimate.setDuration(1000);
        onePieceAnimate.setStartDelay(600);
        onePieceAnimate.setInterpolator(new DecelerateInterpolator(1.5f));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(moveImg);
        animatorSet.playTogether(onePieceAnimate);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        animatorSet.start();
        //使用AnimatorSet 播放多个动画
        DaoMaster.DevOpenHelper db = new DaoMaster.DevOpenHelper(this, "PERSON", null);
        DaoMaster daoMaster = new DaoMaster(db.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        PersonDao personDao = daoSession.getPersonDao();
        QueryBuilder queryBuilder = personDao.queryBuilder();
        queryBuilder.where(PersonDao.Properties.Uuid.isNotNull());
        List<Person> persons = queryBuilder.list();
        if (persons.size() > 0) {
            L.d(persons.get(0).getUuid());
            uuid = persons.get(0).getUuid();
        }
        if (uuid != null) {
            Map<String, String> map = new HashMap<>();
            map.put("uuid", uuid);
            fullScreenPresenter.getInfo(map);
        } else {
            startActivity(new Intent(FullscreenActivity.this, LoginActivity.class));
            overridePendingTransition(R.animator.s_in, R.animator.s_out);
            finish();
        }
    }


    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }


    @Override
    public void setPresenter(fullScreenContract.Presenter presenter) {
        this.fullScreenPresenter = presenter;
    }

    @Override
    public void onFiled(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        startActivity(new Intent(FullscreenActivity.this, LoginActivity.class));
        overridePendingTransition(R.animator.s_in, R.animator.s_out);
        finish();
    }

    @Override
    public void onSuccess(final MainInfoHome mainInfoHome) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("mainInfoHome", mainInfoHome);
        bundle.putString("uuid", uuid);
        final Intent i = new Intent(FullscreenActivity.this, bigmercuActivity.class);
        i.putExtras(bundle);
        startActivity(i);
        L.d("start");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },1000);
        overridePendingTransition(R.animator.s_in, R.animator.s_out);
    }
}
