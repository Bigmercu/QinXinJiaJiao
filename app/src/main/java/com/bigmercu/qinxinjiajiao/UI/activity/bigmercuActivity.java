package com.bigmercu.qinxinjiajiao.UI.activity;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bigmercu.qinxinjiajiao.R;
import com.bigmercu.qinxinjiajiao.UI.fragment.B_SearchFragment;
import com.bigmercu.qinxinjiajiao.UI.fragment.FindFragment;
import com.bigmercu.qinxinjiajiao.UI.fragment.HomeFragment;
import com.bigmercu.qinxinjiajiao.UI.fragment.LocationFragment;
import com.bigmercu.qinxinjiajiao.UI.fragment.MeFragment;
import com.bigmercu.qinxinjiajiao.UI.fragment.MessageFragment;
import com.bigmercu.qinxinjiajiao.Util.Adapter.FragmentAdapter;
import com.bigmercu.qinxinjiajiao.Util.OnItemSelectedListener;
import com.bigmercu.qinxinjiajiao.Util.RxBus;
import com.bigmercu.qinxinjiajiao.dao.DaoMaster;
import com.bigmercu.qinxinjiajiao.dao.DaoSession;
import com.bigmercu.qinxinjiajiao.dao.Person;
import com.bigmercu.qinxinjiajiao.dao.PersonDao;
import com.bigmercu.qinxinjiajiao.entity.loginEntity;
import com.bigmercu.qinxinjiajiao.entity.messageEntity;
import com.bigmercu.qinxinjiajiao.presenter.impl.homePresenterImpl;
import com.hhl.library.FlowTagLayout;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.dao.query.Query;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import sj.mblog.L;

public class bigmercuActivity extends AppCompatActivity implements
        HomeFragment.OnFragmentInteractionListener,
        FindFragment.OnFragmentInteractionListener,
        MeFragment.OnFragmentInteractionListener,
        LocationFragment.OnFragmentInteractionListener,
        MessageFragment.OnFragmentInteractionListener,
        B_SearchFragment.OnFragmentInteractionListener,
        AppBarLayout.OnOffsetChangedListener{
    @Bind(R.id.ntb)
    BottomNavigationBar bottomNavigationBar;
    @Bind(R.id.coor)
    CoordinatorLayout coo;
    private View bottomsheet;
    @Bind(R.id.search_view)
    MaterialSearchView searchView;
    @Bind(R.id.cardView)
    CardView cardView;
    @Bind(R.id.appbar_main)
    AppBarLayout appBarLayout;


    private MaterialBetterSpinner spinner2;
    private BottomSheetBehavior behavior;
    private BottomSheetBehavior behavior1;
    private FragmentAdapter fragmentAdapter;
    private Subscription rxSubscription;
    private List<String> ClassList = new ArrayList<String>();
    private List<String> ClassListChecked = new ArrayList<String>();
    private ArrayList<Fragment> fragmentArrayList;
    private Fragment currentFragment;
    private BottomSheetDialog bottomSheetDialog;
    private ArrayList<String> xiaoxue = new ArrayList<>();
    private ArrayList<String> chuzhong = new ArrayList<>();
    private ArrayList<String> gaozhong = new ArrayList<>();
    private ArrayList<String> yishu = new ArrayList<>();
    private ArrayList<String> arrayList = new ArrayList<String>();
    private Toolbar toolbar;
    private int[] tag = new int[]{0, 0, 0, 0};
    private int[] tagAdded = new int[]{0, 0, 0, 0};
//    private Toolbar toolbar1;
    private AppBarLayout appbar;
    private loginEntity loginEntity;
    private FlowTagLayout tagLayout;
//    private TagGroup tagChenkedLayout;
    private String school = "";
    private int sex = -1;
    private int more = -1;
    private CheckBox checkBoxBoy;
    private CheckBox checkBoxGirl;
    private Switch switchCompat;
    private FloatingActionButton floatingActionButton;
    private int y;

    private Subscription subscription;
    private Subscription subscription1;
    private Bundle data;
    private String uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigmercu);
        ButterKnife.bind(this);
        initToolBar();
        setSupportActionBar(toolbar);
        data = getIntent().getExtras();
        if(data != null) {
            uuid = data.getString("uuid");
        }
        L.d("ui");
        initUI(savedInstanceState);
        L.d("db");
        initDB();
        L.d("yes");
    }

    private void initDB() {
        Subscription sub = Single.create(new Single.OnSubscribe<SingleSubscriber>() {
            @Override
            public void call(SingleSubscriber s) {
                initData();
                DaoMaster.DevOpenHelper db = new DaoMaster.DevOpenHelper(bigmercuActivity.this, "PERSON", null);
                DaoMaster daoMaster = new DaoMaster(db.getWritableDatabase());
                DaoSession daoSession = daoMaster.newSession();
                Query(daoSession);
                db.close();

            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    private void initData() {
        xiaoxue.add("小学全科作业辅导");
        chuzhong.add("初中全科作业辅导");
        gaozhong.add("高中全科作业辅导");
        xiaoxue.add("小学数学");
        chuzhong.add("初中数学");
        gaozhong.add("高中数学");
        xiaoxue.add("小学英语");
        chuzhong.add("初中英语");
        gaozhong.add("高中英语");
        xiaoxue.add("小学语文");
        chuzhong.add("初中语文");
        gaozhong.add("高中语文");
        chuzhong.add("初中物理");
        gaozhong.add("高中物理");
        chuzhong.add("初中化学");
        gaozhong.add("高中化学");
        chuzhong.add("初中生物");
        gaozhong.add("高中生物");
        chuzhong.add("初中地理");
        gaozhong.add("高中地理");
        chuzhong.add("初中历史");
        gaozhong.add("高中历史");
        chuzhong.add("初中政治");
        gaozhong.add("高中政治");
        yishu.add("吉他");
        yishu.add("钢琴");
        yishu.add("绘画");
        yishu.add("书法");
        yishu.add("舞蹈");
        yishu.add("小提琴");
        yishu.add("古筝");
        yishu.add("架子鼓");
        yishu.add("笛子");
        yishu.add("电吉他");
        yishu.add("萨克斯");
        yishu.add("葫芦丝");
        yishu.add("其他");
    }


    private void initToolBar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        assert toolbar != null;
        toolbar.setLogo(R.drawable.ic_launcher);
        toolbar.setTitle("倾心家教");
    }


    private void Query(DaoSession daoSession) {
        Query query = daoSession.getPersonDao()
                .queryBuilder()
                .where(PersonDao.Properties.Phone.eq("13125375549"))
                .orderAsc(PersonDao.Properties.Date)
                .build();
        List<Person> Person = query.list();
        for (Person person : Person) {
            L.d(person.getId() + "/" + person.getComment() + "/" + person.getPhone() + " /" + person.getUuid());
        }
    }

    private static void tickleInvalidationFlag(View view) {
        final float y = ViewCompat.getTranslationY(view);
        ViewCompat.setTranslationY(view, y + 1);
        ViewCompat.setTranslationY(view, y);
    }


    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    //***********************************************changeFragment***********************************************//

    public void changeF(int position) {
//        L.d("lklk"+position);
//        if (position == 5) {
//            DisplayMetrics displayMetrics = new DisplayMetrics();
//            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//            float finalRadius = (float) Math.hypot(displayMetrics.widthPixels / 2, displayMetrics.heightPixels / 2);
//            SupportAnimator animator = ViewAnimationUtils
//                    .createCircularReveal(rela_load, displayMetrics.widthPixels / 2,
//                            displayMetrics.heightPixels / 2, 100, finalRadius);
//            animator.setInterpolator(new AccelerateDecelerateInterpolator());
//            animator.setDuration(1000);
//            animator.start();
//        }
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (null != currentFragment) {
            transaction.hide(currentFragment);
        }

        Fragment fragment = getSupportFragmentManager().
                findFragmentByTag(fragmentArrayList.get(position).getClass().getName());
        if (fragment == null) {
            fragment = fragmentArrayList.get(position);
        }

        currentFragment = fragment;

        if (!fragment.isAdded()) {
            transaction.add(R.id.MainFragment, fragment, fragment.getClass().getName());
        } else {
            transaction.show(fragment);
        }
        transaction.commit();
    }




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (behavior1.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                hideBehaverDetial(0x01);
                return true;
            }
            if (searchView.isSearchOpen()) {
                searchView.closeSearch();
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    private void showDialogFragment() {
        if(bottomsheet == null){
            bottomsheet = LayoutInflater.from(this).inflate(R.layout.layout_bottom_sheet1, null);
            bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(bottomsheet);
        }
        bottomSheetDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                if (more == 1) {
                    switchCompat.setChecked(true);
                    switch (sex) {
                        case 2:
                            checkBoxBoy.setChecked(true);
                            checkBoxGirl.setChecked(true);
                            break;
                        case -1:
                            checkBoxBoy.setChecked(false);
                            checkBoxGirl.setChecked(false);
                            break;
                        case 0:
                            checkBoxBoy.setChecked(false);
                            checkBoxGirl.setChecked(true);
                            break;
                        case 1:
                            checkBoxBoy.setChecked(true);
                            checkBoxGirl.setChecked(false);
                            break;
                    }
                    if (!school.equals("")) {
                        spinner2.setHint(school);
                    }
                }
            }
        });

        final BottomSheetBehavior mBehavior = BottomSheetBehavior.from((View) bottomsheet.getParent());
        if (bottomSheetDialog != null) {
            bottomSheetDialog.show();
            mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            setTags(tag);
            initListeaner();
        }

        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == 4) {
                    moveFab(1);
                } else if (newState == 3) {
                    moveFab(2);
                }
            }

            @Override
            public void onSlide(@NonNull final View bottomSheet, float slideOffset) {
//                L.d(slideOffset);
//                final int max = bottomSheet.getHeight();
//                if(slideOffset > 0.4) {
//                    Observable.just(slideOffset)
//                            .map(new Func1<Float, Float>() {
//                                @Override
//                                public Float call(Float aFloat) {
//                                    return (max * (1-aFloat));
//                                }
//                            }).subscribe(new Action1<Float>() {
//                        @Override
//                        public void call(Float integer) {
//                            floatingActionButton.setTranslationY(-integer);
//                            L.d(integer);
//                        }
//                    });
//                }

                if (slideOffset == -1.0) {
                    bottomSheetDialog.dismiss();
                }
            }
        });

        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                bottomNavigationBar.unHide();
            }
        });
    }

    private void moveFab(final int a) {
        int viewHight = dip2px(260);
        int rooty = bottomsheet.getHeight();
        y = rooty - viewHight;
        int longTime;

        if (a == 1) {
            y = -y;
            longTime = 150;
        } else {
            y = 0;
            longTime = 400;
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(floatingActionButton, "translationY", y).setDuration(longTime);
        if (a == 1) {
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
        } else {
            animator.setInterpolator(new OvershootInterpolator());
        }
        animator.start();
        otherAnimation(a);
    }

    private void otherAnimation(int i) {
        int j = 0;
        if (i == 1) {
            j = -switchCompat.getLeft() + dip2px(10);
        } else {
            j = 0;
        }
        L.d(j);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(switchCompat, "translationX", j);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();
    }

    private void initListeaner() {
        spinner2 = (MaterialBetterSpinner) bottomsheet.findViewById(R.id.spinnermm);
        CheckBox checkBoxXX = (CheckBox) bottomsheet.findViewById(R.id.xxcheck);
        CheckBox checkBoxCZ = (CheckBox) bottomsheet.findViewById(R.id.czcheck);
        CheckBox checkBoxGZ = (CheckBox) bottomsheet.findViewById(R.id.gzcheck);
        CheckBox checkBoxYY = (CheckBox) bottomsheet.findViewById(R.id.yycheck);
        checkBoxBoy = (CheckBox) bottomsheet.findViewById(R.id.boycheck);
        checkBoxGirl = (CheckBox) bottomsheet.findViewById(R.id.girlcheck);
        final LinearLayout linearLayout = (LinearLayout) bottomsheet.findViewById(R.id.moreOption);
        LinearLayout linearLayout_t = (LinearLayout) bottomsheet.findViewById(R.id.layout_transition);
        LinearLayout linearLayout1 = (LinearLayout) bottomsheet.findViewById(R.id.linear_t);
        switchCompat = (Switch) bottomsheet.findViewById(R.id.otherOptions);
        String[] COUNTRIES = getResources().getStringArray(R.array.xuexiao);
        floatingActionButton = (FloatingActionButton) bottomsheet.findViewById(R.id.submitOptions);
        final ImageView imageView = (ImageView) bottomsheet.findViewById(R.id.imageView12);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        checkBoxBoy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (sex == -1) {
                        sex = 1;
                    } else if (sex == 0) {
                        sex = 2;
                    }
                } else {
                    if (sex == 2) {
                        sex = 0;
                    } else if (sex == 1) {
                        sex = -1;
                    }
                }
            }
        });
        checkBoxGirl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (sex == -1) {
                        sex = 0;
                    } else if (sex == 1) {
                        sex = 2;
                    }
                } else {
                    if (sex == 2) {
                        sex = 1;
                    } else if (sex == 0) {
                        sex = -1;
                    }
                }
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在此处提交筛选  筛选本地 请求20条
                bottomSheetDialog.dismiss();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);

        final LayoutTransition layoutTransition = new LayoutTransition();
        final LayoutTransition layoutTransition1 = new LayoutTransition();
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING);
        linearLayout_t.setLayoutTransition(layoutTransition);
        linearLayout1.setLayoutTransition(layoutTransition);

        spinner2.setAdapter(adapter);

        spinner2.addTextChangedListener(new OnItemSelectedListener() {
            @Override
            protected void onItemSelected(String string) {
                school = string;
                L.d(string);
            }
        });

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
//                    optionAnimRun(linearLayout);
                    more = 1;
                    linearLayout.setVisibility(View.VISIBLE);
//                    switchCompat.setEnabled(false);
                } else {
                    more = 0;
                    linearLayout.setVisibility(View.GONE);
                }
            }
        });

        checkBoxXX.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tag[0] = 1;
                } else {
                    tag[0] = 0;
                }
                setTags(tag);
            }
        });
        checkBoxCZ.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tag[1] = 1;
                } else {
                    tag[1] = 0;
                }
                setTags(tag);
            }
        });
        checkBoxGZ.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tag[2] = 1;
                } else {
                    tag[2] = 0;
                }
                setTags(tag);

            }
        });
        checkBoxYY.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tag[3] = 1;
                } else {
                    tag[3] = 0;
                }
                setTags(tag);
            }
        });
    }

    public void hideBehaverDetial(int where) {
        if (appBarLayout.getTranslationY() == -appBarLayout.getHeight()) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(-appBarLayout.getHeight(), 0);
            valueAnimator.setTarget(appBarLayout);
            valueAnimator.setDuration(250);
            valueAnimator.start();
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    appBarLayout.setTranslationY((float) animation.getAnimatedValue());
                }
            });
        }
        if (where == 0x01) {
            behavior1.setState(BottomSheetBehavior.STATE_HIDDEN);
            bottomNavigationBar.unHide();
        }
    }

    private void setTags(final int[] tag) {
        tagLayout = (FlowTagLayout) bottomsheet.findViewById(R.id.Ctag);
//        tagChenkedLayout = (TagGroup) bottomsheet.findViewById(R.id.CtagChecked);
        TextView textView = (TextView) bottomsheet.findViewById(R.id.textView14);
        TextView textView1 = (TextView) bottomsheet.findViewById(R.id.textView15);
        TextView textView2 = (TextView) bottomsheet.findViewById(R.id.textView16);
        final TextView textView3 = (TextView) bottomsheet.findViewById(R.id.textView17);
        CheckBox checkBoxXX = (CheckBox) bottomsheet.findViewById(R.id.xxcheck);
        CheckBox checkBoxCZ = (CheckBox) bottomsheet.findViewById(R.id.czcheck);
        CheckBox checkBoxGZ = (CheckBox) bottomsheet.findViewById(R.id.gzcheck);
        CheckBox checkBoxYY = (CheckBox) bottomsheet.findViewById(R.id.yycheck);
        RelativeLayout relativeLayout1 = (RelativeLayout) bottomsheet.findViewById(R.id.rela_animation);
        RelativeLayout relativeLayout = (RelativeLayout) bottomsheet.findViewById(R.id.checkedrela);


        if (tag[0] == 1) {
            if (tagAdded[0] == 0) {
                arrayList.addAll(xiaoxue);
                tagAdded[0] = 1;
            }
            checkBoxXX.setChecked(true);
        } else {
            tagAdded[0] = 0;
            Iterator<String> it = ClassListChecked.listIterator();
            Iterator<String> it1 = arrayList.listIterator();
            while (it.hasNext()) {
                if (it.next().startsWith("小学")) {
                    it.remove();
                }
            }
            while (it1.hasNext()) {
                if (it1.next().startsWith("小学")) {
                    it1.remove();
                }
            }
        }
        if (tag[1] == 1) {
            if (tagAdded[1] == 0) {
                arrayList.addAll(chuzhong);
                tagAdded[1] = 1;
            }
            checkBoxCZ.setChecked(true);
        } else {
            tagAdded[1] = 0;
            Iterator<String> it = ClassListChecked.listIterator();
            Iterator<String> it1 = arrayList.listIterator();
            while (it.hasNext()) {
                if (it.next().startsWith("初中")) {
                    it.remove();
                }
            }
            while (it1.hasNext()) {
                if (it1.next().startsWith("初中")) {
                    it1.remove();
                }
            }
        }
        if (tag[2] == 1) {
            if (tagAdded[2] == 0) {
                arrayList.addAll(gaozhong);
                tagAdded[2] = 1;
            }
            checkBoxGZ.setChecked(true);
        } else {
            tagAdded[2] = 0;
            Iterator<String> it = ClassListChecked.listIterator();
            Iterator<String> it1 = arrayList.listIterator();
            while (it.hasNext()) {
                if (it.next().startsWith("高中")) {
                    it.remove();
                }
            }
            while (it1.hasNext()) {
                if (it1.next().startsWith("高中")) {
                    it1.remove();
                }
            }
        }
        if (tag[3] == 1) {
            if (tagAdded[3] == 0) {
                arrayList.addAll(yishu);
                tagAdded[3] = 1;
            }
            checkBoxYY.setChecked(true);
        } else {
            tagAdded[3] = 0;
            Iterator<String> it = ClassListChecked.listIterator();
            Iterator<String> it1 = arrayList.listIterator();
            while (it.hasNext()) {
                if (yishu.indexOf(it.next()) != -1) {
                    it.remove();
                }
            }
            while (it1.hasNext()) {
                if (yishu.indexOf(it1.next()) != -1) {
                    it1.remove();
                }
            }
        }

//        if (ClassListChecked != null) {
//            tagChenkedLayout.setTags(ClassListChecked);
//        }
//        tagLayout.setTags(arrayList);

        if (arrayList.size() != 0) {
            relativeLayout.setVisibility(View.VISIBLE);
            textView1.setVisibility(View.VISIBLE);
            textView2.setVisibility(View.VISIBLE);
//            tagLayout.setTags(arrayList);
            textView.setVisibility(View.GONE);
            if (ClassListChecked.size() > 0) {
                textView3.setVisibility(View.GONE);
            }
        } else {
            relativeLayout.setVisibility(View.GONE);
            tagLayout.removeAllViews();
            textView.setVisibility(View.VISIBLE);
            textView1.setVisibility(View.GONE);
            textView2.setVisibility(View.GONE);
            if (ClassListChecked.size() > 0) {
                textView3.setVisibility(View.GONE);
            }
        }

//        tagLayout.setOnTagClickListener(new TagGroup.OnTagClickListener() {
//            @Override
//            public void onTagClick(String tag) {
//                ClassList = updateTag(tag,ClassList);
//                tagLayout.setTags(arrayList);
//                ClassListChecked.add(tag);
//                tagChenkedLayout.setTags(ClassListChecked);
//                if (ClassListChecked.size() > 0) {
//                    textView3.setVisibility(View.GONE);
//                } else {
//                    textView3.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//            tagChenkedLayout.setOnTagClickListener(new TagGroup.OnTagClickListener() {
//                @Override
//                public void onTagClick(String tag) {
//                    ClassListChecked = updateTag(tag, ClassList);
//                    tagChenkedLayout.setTags(ClassListChecked);
//                    ClassList.add(tag);
//                    tagLayout.setTags(ClassList);
//                    if (ClassListChecked.size() > 0) {
//                        textView3.setVisibility(View.GONE);
//                    } else {
//                        textView3.setVisibility(View.VISIBLE);
//                    }
//                }
//            });

        }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
        //解决Fragment重叠问题
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
//        L.d(percentage);
    }

    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    public static List<String> updateTag(String tag, List<String> arrayList){
        Iterator<String> it = arrayList.listIterator();
        while (it.hasNext()){
            if(tag.equals(it.next())){
                it.remove();
            }
        }
        return arrayList;
    }

    //***********************************************getHight***********************************************//

    private int getAppBarHeight() {
        return dip2px(56) + getStatusBarHeight();
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }

    private int dip2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
//***********************************************getHight***********************************************//


    public void optionAnimRun(View view) {
        L.d("checks");
        ObjectAnimator.ofFloat(view, "translationY", -200f, 0f)
                .setDuration(1000)
                .start();
    }


    private void initUI(Bundle savedInstanceState) {
        fragmentArrayList = new ArrayList<>(5);
        HomeFragment homeFragment = HomeFragment.newInstance();
        new homePresenterImpl(homeFragment,data);
        fragmentArrayList.add(homeFragment);
        fragmentArrayList.add(FindFragment.newInstance());
        fragmentArrayList.add(LocationFragment.newInstance());
        fragmentArrayList.add(MessageFragment.newInstance());
        fragmentArrayList.add(MeFragment.newInstance());
        appBarLayout.addOnOffsetChangedListener(this);
        subscription = RxBus.getDefault().toObservable(messageEntity.class)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<messageEntity>() {
                    @Override
                    public void call(final messageEntity messageEntity) {
                        Intent i = new Intent(bigmercuActivity.this,MainDetialActivity.class);
                        i.putExtra("id",messageEntity.getMes());
                        i.putExtra("uuid",uuid);
                        startActivity(i);
                        overridePendingTransition(R.anim.pull_in_right,R.anim.push_out_left);
                    }
                });
        if (savedInstanceState == null) {
            changeF(0);
        }
        bottomNavigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        bottomNavigationBar
                .setActiveColor(R.color.colorPrimary)
                .setInActiveColor("#FFFFFF")
                .setBarBackgroundColor("#ECECEC");
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Home").setInActiveColor(R.color.wite).setActiveColor(R.color.grey))
                .addItem(new BottomNavigationItem(R.mipmap.find, "找老师").setInActiveColor(R.color.wite).setActiveColor(R.color.brown))
                .addItem(new BottomNavigationItem(R.mipmap.location, "周边家长").setInActiveColor(R.color.wite).setActiveColor(R.color.blue))
                .addItem(new BottomNavigationItem(R.mipmap.message, "消息").setInActiveColor(R.color.wite).setActiveColor(R.color.orange))
                .addItem(new BottomNavigationItem(R.mipmap.me, "我").setInActiveColor(R.color.wite).setActiveColor(R.color.teal))
                .initialise();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                changeF(position);
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_ss:
                        showDialogFragment();
                        ViewCompat.postInvalidateOnAnimation(coo);
                        bottomNavigationBar.hide();
                        break;
                }
                return false;
            }
        });

        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        searchView.setHint("搜索...");
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                byte status = 1 << 1;
                final ObjectAnimator objectAniamtor = ObjectAnimator.ofFloat(bottomNavigationBar, "translationY", bottomNavigationBar.getHeight());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        objectAniamtor.start();
                    }
                }, 100);
            }

            @Override
            public void onSearchViewClosed() {
                byte status = 1 << 2;
                final ObjectAnimator objectAniamtor = ObjectAnimator.ofFloat(bottomNavigationBar, "translationY", 0);
                objectAniamtor.setInterpolator(new AccelerateInterpolator());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        objectAniamtor.start();
                    }
                }, 150);
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                L.i(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        if(!subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
        if(!rxSubscription.isUnsubscribed()){
            rxSubscription.unsubscribe();
        }
    }

}
