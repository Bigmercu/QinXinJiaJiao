package com.bigmercu.qinxinjiajiao.UI.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appyvet.rangebar.RangeBar;
import com.bigmercu.qinxinjiajiao.R;
import com.bigmercu.qinxinjiajiao.Util.DeviceUuidFactory;
import com.bigmercu.qinxinjiajiao.contract.identifyVerifyContract;
import com.bigmercu.qinxinjiajiao.presenter.impl.identifyPresenterImpl;
import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import me.nereo.multi_image_selector.adapter.ImageGridAdapter;
import me.nereo.multi_image_selector.bean.Image;
import rx.Observable;
import rx.functions.Action1;
import sj.mblog.L;

public class identityVerify extends AppCompatActivity implements identifyVerifyContract.View, AppBarLayout.OnOffsetChangedListener {


    private static final int REQUEST_IMAGE_PHOTO = 0x01;
    private static final int REQUEST_IMAGE_PHOTO_BG = 0x03;
    private static final int REQUEST_IMAGE_CA = 0x02;


    @Bind(R.id.trueName)
    TextInputLayout name;
    @Bind(R.id.photo_touxiang)
    ImageView photo;
    @Bind(R.id.IDnumber)
    TextInputLayout idNumber;
    @Bind(R.id.school)
    TextInputLayout sch;
    @Bind(R.id.grade)
    Spinner grade;
    @Bind(R.id.major)
    TextInputLayout major;
    @Bind(R.id.teachCareer)
    TextInputLayout teachCareer;
    @Bind(R.id.RXX)
    RadioButton radioButtonXX;
    @Bind(R.id.ROO)
    RadioButton radioButtonOO;
    @Bind(R.id.tagChecked)
    co.lujun.androidtagview.TagContainerLayout tagContainerLayout;
    @Bind(R.id.tag)
    co.lujun.androidtagview.TagContainerLayout mTagContainerLayout;
    @Bind(R.id.welcomename)
    TextView welcome;
    @Bind(R.id.imageButton)
    ImageButton cameraButton;
    @Bind(R.id.photoRecy)
    GridView photoRecy;
    @Bind(R.id.addPhoto)
    ImageButton addPhoto;
    @Bind(R.id.photo_bg)
    ImageView imageViewPhoto;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rangebar)
    RangeBar rangeBar;
    @Bind(R.id.lowV)
    TextView low;
    @Bind(R.id.hightV)
    TextView hight;
    @Bind(R.id.picNum)
    TextView PicNum;
    @Bind(R.id.net)
    NestedScrollView nestedScrollView;
    @Bind(R.id.collapsing)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.coo)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.appbar)
    AppBarLayout appBarLayout;
    @Bind(R.id.rela_identify)
    RelativeLayout relativeLayout;
    @Bind(R.id.gridLinear)
    LinearLayout linearLayout;
    @Bind(R.id.maintitle)
    LinearLayout mTitleContainer;
    @Bind(R.id.main_textview_title)
    TextView mTitle;
    @Bind(R.id.loading)
    RelativeLayout rela_load;

    public ImageGridAdapter photoGridAdapter;
    private identifyVerifyContract.Presenter identifyPresenter;

    public boolean isFirst = true;
    public boolean isFirstB = true;
    public boolean isStudent = true;

    private List<String> list = new ArrayList<String>();
    private List<String> gradeList = new ArrayList<String>();
    private List<String> gradeList_teacher = new ArrayList<String>();
    private List<String> ClassList = new ArrayList<String>();
    private List<String> ClassListChecked = new ArrayList<String>();
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private Map<String, String> ListMap = new HashMap<String, String>();
    private ArrayList<String> photos = new ArrayList<String>();
    private ArrayList<String> photos_bg = new ArrayList<String>();
    private ArrayList<String> photosOfCa = new ArrayList<String>();
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.25f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.1f;
    private static final int ALPHA_ANIMATIONS_DURATION = 300;
    private static final int ALPHA_ANIMATIONS_DURATION_TOOLBAR = 500;

    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;
    private String path = "";
    private String truename;
    private String idnumber;
    private String sex;
    private String grad;
    private List<String> subject;
    private String timepaylow, timepayhight;
    private String school;
    private List<String> certiPhoPath;
    private String teachCaree;
    public static UUID uuid;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //解决键盘遮住输入框的问题
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_identity_verify);
        ButterKnife.bind(this);
        new identifyPresenterImpl(this);
        initData();
        initUI();
    }

    private void initData() {
        uuid = new DeviceUuidFactory(this).getDeviceUuid();

        gradeList.add("大一");
        gradeList.add("大二");
        gradeList.add("大三");
        gradeList.add("大四");
        gradeList.add("研究生");
        gradeList.add("博士");
        gradeList.add("其他学历或已毕业");

        gradeList_teacher.add("本科");
        gradeList_teacher.add("研究生");
        gradeList_teacher.add("博士");


        ClassList.add("小学数学");
        ClassList.add("初中数学");
        ClassList.add("高中数学");
        ClassList.add("小学英语");
        ClassList.add("初中英语");
        ClassList.add("高中英语");
        ClassList.add("小学语文");
        ClassList.add("初中语文");
        ClassList.add("高中语文");
        ClassList.add("初中物理");
        ClassList.add("初中物理");
        ClassList.add("初中化学");
        ClassList.add("初中化学");
        ClassList.add("初中生物");
        ClassList.add("初中生物");
        ClassList.add("初中地理");
        ClassList.add("高中地理");
        ClassList.add("初中历史");
        ClassList.add("高中历史");
        ClassList.add("初中政治");
        ClassList.add("高中政治");
        ClassList.add("小学全科作业辅导");
        ClassList.add("初中全科作业辅导");
        ClassList.add("高中全科作业辅导");
        ClassList.add("吉他");
        ClassList.add("钢琴");
        ClassList.add("绘画");
        ClassList.add("书法");
        ClassList.add("舞蹈");
        ClassList.add("小提琴");
        ClassList.add("古筝");
        ClassList.add("架子鼓");
        ClassList.add("笛子");
        ClassList.add("电吉他");
        ClassList.add("萨克斯");
        ClassList.add("葫芦丝");
        ClassList.add("其他");
    }

    private void initUI() {
        Glide.with(getApplicationContext())
                .load(R.drawable.back_touxiang)
                .into(imageViewPhoto);
        RxPermissions.getInstance(this)
                .request(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            L.d("yesyes");
                        } else {
                            L.d("nono");
                        }
                    }
                });

        identifyPresenter = new identifyPresenterImpl(this);
        name.setHint("真实姓名");
        idNumber.setHint("身份证号");
        major.setHint("专业");
        teachCareer.setHint("教学经历");
        phone = getIntent().getStringExtra("phone");
        appBarLayout.addOnOffsetChangedListener(this);

        startAlphaAnimation(mTitle, 0, View.INVISIBLE);
        startAlphaAnimation(toolbar, 0, View.INVISIBLE);


        collapsingToolbarLayout.setContentScrimResource(R.drawable.camera);

        final ArrayAdapter<String> arrayAdapter1 =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, gradeList);
        final ArrayAdapter<String> arrayAdapter2 =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, gradeList_teacher);


        grade.setAdapter(arrayAdapter1);
        grade.setVisibility(View.VISIBLE);
        sch.setVisibility(View.VISIBLE);
        isStudent = true;
        sch.setHint("学校");
        low.setText("25");
        hight.setText("50");
        rangeBar.setTickStart(25);
        rangeBar.setTickEnd(80);
        rangeBar.setTickInterval(5);
        rangeBar.setRangePinsByValue(25, 50);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("请选择您的身份");
        builder.setSingleChoiceItems(new String[]{"大学生", "在职老师"}, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    grade.setAdapter(arrayAdapter1);
                    grade.setVisibility(View.VISIBLE);
                    sch.setVisibility(View.VISIBLE);
                    isStudent = true;
                    sch.setHint("学校");
                    rangeBar.setTickStart(25);
                    rangeBar.setTickEnd(80);
                    rangeBar.setTickInterval(5);
                    low.setText("25");
                    hight.setText("50");
                    rangeBar.setRangePinsByValue(25, 50);
                } else {
                    grade.setAdapter(arrayAdapter2);
                    grade.setVisibility(View.GONE);
                    isStudent = false;
                    sch.setVisibility(View.GONE);
                    rangeBar.setTickStart(50);
                    rangeBar.setTickEnd(300);
                    rangeBar.setTickInterval(10);
                    low.setText("50");
                    hight.setText("150");
                    rangeBar.setRangePinsByValue(50, 150);
                }
            }
        });
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    grade.setVisibility(View.GONE);
                    isStudent = false;
                } else {
                    grade.setVisibility(View.VISIBLE);
                    isStudent = true;
                }
            }
        });
        builder.setCancelable(false);
        builder.create();
        builder.show();
        photoGridAdapter = new ImageGridAdapter(this, false, 3);
        photoGridAdapter.showSelectIndicator(false);

        name.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                welcome.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if(verticalOffset == 0)
//                {
//                    appBarLayout.setExpanded(true);
//                }else {
//                    appBarLayout.setExpanded(false);
//                }
            }
        });

        mTagContainerLayout.setTags(ClassList);

        mTagContainerLayout.setOnTagClickListener(new co.lujun.androidtagview.TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                tagContainerLayout.addTag(ClassList.get(position));
                ClassListChecked.add(ClassList.get(position));
                mTagContainerLayout.removeTag(position);
                ClassList.remove(position);
            }

            @Override
            public void onTagLongClick(int position, String text) {
            }
        });

        tagContainerLayout.setOnTagClickListener(new co.lujun.androidtagview.TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                mTagContainerLayout.addTag(ClassListChecked.get(position));
                ClassList.add(ClassListChecked.get(position));
                tagContainerLayout.removeTag(position);
                ClassListChecked.remove(position);
            }

            @Override
            public void onTagLongClick(int position, String text) {

            }
        });

        rangeBar.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                low.setText(String.valueOf(leftPinValue));
                hight.setText(String.valueOf(rightPinValue));
            }
        });

        radioButtonXX.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                radioButtonOO.setChecked(false);
                radioButtonXX.setChecked(isChecked);
            }
        });
        radioButtonOO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                radioButtonXX.setChecked(false);
                radioButtonOO.setChecked(isChecked);
            }
        });
    }

    public void previewPhoto(Intent intent) {
        startActivityForResult(intent, 7);
    }

    @OnItemSelected(R.id.grade)
    public void OnItemClickGrade(View view, int position) {
        grad = gradeList.get(position);
    }


    @OnClick(R.id.addPhoto)
    public void addPhoto() {
        Intent intent = new Intent(getApplicationContext(), MultiImageSelectorActivity.class);
        // 是否显示调用相机拍照
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大图片选择数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);
        // 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者 多选/MultiImageSelectorActivity.MODE_MULTI)
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE,
                MultiImageSelectorActivity.MODE_MULTI);
        // 默认选择图片,回填选项(支持String ArrayList)
        intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, photos);
        startActivityForResult(intent, REQUEST_IMAGE_CA);
    }

    @OnClick(R.id.imageButton)
    public void takePhoto() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("照片选择");
        builder.setSingleChoiceItems(new String[]{"头像", "头像背景"}, 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (which == 0) {
                    Intent intent = new Intent(getApplicationContext(),
                            MultiImageSelectorActivity.class);
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE,
                            MultiImageSelectorActivity.MODE_SINGLE);
                    intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, photosOfCa);
                    startActivityForResult(intent, REQUEST_IMAGE_PHOTO);
                } else {
                    Intent intent = new Intent(getApplicationContext(),
                            MultiImageSelectorActivity.class);
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE,
                            MultiImageSelectorActivity.MODE_SINGLE);
                    intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, photosOfCa);
                    startActivityForResult(intent, REQUEST_IMAGE_PHOTO_BG);
                }
            }
        });
        builder.setCancelable(true);
        builder.create();
        builder.show();
    }

    //回调图片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CA) {
            L.d(requestCode);
            if (data != null) {
                photosOfCa = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                String text = photosOfCa.size() + "/" + "9";
                PicNum.setText(text);
                selectedPhotos.clear();
                if (photosOfCa != null) {
                    selectedPhotos.addAll(photosOfCa);
                }
                int i = 0;
                List<Image> images = new ArrayList<>();
                for (String path : photosOfCa) {

                    File file = new File(path);
                    L.d("A" + file.length());
                    Image image = null;
                    image = new Image(path);
                    images.add(image);
                }
                if (images.size() > 3 && images.size() <= 6) {
                    linearLayout.setMinimumHeight(photoGridAdapter.getmGridWidth() * 2);
                } else if (images.size() > 6 && images.size() <= 9) {
                    linearLayout.setMinimumHeight(photoGridAdapter.getmGridWidth() * 3);
                } else {
                    linearLayout.setMinimumHeight(photoGridAdapter.getmGridWidth());
                }
                photoGridAdapter.setData(images);
                photoRecy.setAdapter(photoGridAdapter);
                photoGridAdapter.notifyDataSetChanged();
            }
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_PHOTO) {
            if (data != null) {
                photos = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                if (photos.size() != 0) {
                    Uri uri = Uri.fromFile(new File(photos.get(0)));
                    String outUriA = photos.get(0).substring(photos.get(0).length() - 4, photos.get(0).length());
                    String outUriB = photos.get(0).substring(0, photos.get(0).length() - 4);
                    outUriB = outUriB + getString(R.string.photo_houzui) + outUriA;
                    Uri utiOutPut = Uri.fromFile(new File(outUriB));
                    int width = photo.getWidth();
                    int hight = photo.getHeight();

                    if (width < 500) {
                        width *= 2;
                        hight *= 2;
                    }

                    UCrop.Options options = new UCrop.Options();
                    options.setCompressionFormat(Bitmap.CompressFormat.PNG);
                    options.setCompressionQuality(30);
                    options.setMaxBitmapSize(hight);
                    options.setAllowedGestures(UCropActivity.ALL, UCropActivity.NONE, UCropActivity.NONE);
                    options.setToolbarTitle("头像裁剪");
                    options.setToolbarColor(Color.rgb(63, 114, 181));
                    options.setStatusBarColor(Color.rgb(53, 96, 153));
                    options.setActiveWidgetColor(Color.RED);

                    UCrop uCrop = UCrop.of(uri, utiOutPut)
                            .withOptions(options)
                            .withAspectRatio(width, hight)
                            .withMaxResultSize(width, hight);
                    uCrop.start(identityVerify.this);
                }
            }
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_PHOTO_BG) {
            photos_bg = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            if (photos_bg.size() != 0) {
                Glide.with(getApplicationContext())
                        .load(photos_bg.get(0))
                        .into(imageViewPhoto);
            }
        } else if (requestCode == UCrop.REQUEST_CROP) {
            if (data != null) {
                final Uri resultUri = UCrop.getOutput(data);
                Drawable drawable = Drawable.createFromPath(resultUri.getPath());
                path = resultUri.getPath();
                File file = new File(path);
                L.d(file.length());
                photo.setImageDrawable(drawable);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }

    @OnClick(R.id.submitInfo)
    public void submit() {
        truename = String.valueOf(name.getEditText().getText());
        idnumber = String.valueOf(idNumber.getEditText().getText());
        school = String.valueOf(sch.getEditText().getText());
        teachCaree = String.valueOf(teachCareer.getEditText().getText());
        if (radioButtonOO.isChecked()) {
            sex = "girl";
        } else {
            sex = "boy";
        }
        final String identify;
        if (isStudent) {
            identify = "student";
        } else {
            identify = "teacher";
        }

        final String majo = String.valueOf(major.getEditText().getText());

        if (low.getText() != null) {
            timepaylow = (String) low.getText();
            timepayhight = (String) hight.getText();
        }
        final Pattern namePattern = Pattern.compile("^[\\u4e00-\\u9fa5]{2,4}$");
        final Pattern schoolPattern = Pattern.compile("^[\\u4e00-\\u9fa5]*$");
        final Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");

        if (path.equals("")) {
            appBarLayout.setExpanded(true);
            nestedScrollView.smoothScrollTo(appBarLayout.getRight(), appBarLayout.getTop());
            Snackbar.make(photoRecy, "请务必在顶部添加一张本人照片", Snackbar.LENGTH_INDEFINITE).setAction("好的", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            }).show();
        } else if (!namePattern.matcher(truename).matches()) {
            name.setErrorEnabled(true);
            name.setError("请务必填写真实姓名，以获取家长信任。");
            name.getEditText().requestFocus();
            nestedScrollView.smoothScrollTo(name.getRight(), name.getTop());
            name.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (namePattern.matcher(s).matches()) {
                        name.setErrorEnabled(false);
                    }
                }
            });

        } else if (!idNumPattern.matcher(idnumber).matches()) {
            idNumber.setErrorEnabled(true);
            idNumber.setError("请填写身份证号，它仅用于验证您的身份，对于您的基本信息我们将以最高规格保密");
            idNumber.getEditText().requestFocus();
            nestedScrollView.smoothScrollTo(idNumber.getRight(), idNumber.getTop());
            idNumber.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (idNumPattern.matcher(s).matches()) {
                        idNumber.setErrorEnabled(false);
                    }
                }
            });

        } else if (!schoolPattern.matcher(school).matches()) {
            sch.setErrorEnabled(true);
            sch.setError("请认证填写学校");
            sch.getEditText().requestFocus();
            nestedScrollView.smoothScrollTo(sch.getRight(), sch.getTop());
            sch.getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (schoolPattern.matcher(s).matches()) {
                        sch.setErrorEnabled(false);
                    }
                }
            });
        } else if (ClassListChecked.size() == 0) {
            Snackbar.make(photoRecy, "请务必选择您希望教授的科目", Snackbar.LENGTH_LONG).show();
            nestedScrollView.smoothScrollTo(tagContainerLayout.getRight(), tagContainerLayout.getTop());
            tagContainerLayout.requestFocus();
        } else {

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setTitle("确认提交？");
            builder.setMessage("丰富的信息更能获取家长的青睐哦，您有部分信息未填写，确认现在提交吗？");
            builder.setPositiveButton("确定提交", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    rela_load.setVisibility(View.VISIBLE);
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    float finalRadius = (float) Math.hypot(displayMetrics.widthPixels / 2, displayMetrics.heightPixels / 2);
                    SupportAnimator animator = ViewAnimationUtils
                            .createCircularReveal(rela_load, displayMetrics.widthPixels / 2,
                                    displayMetrics.heightPixels / 2, 100, finalRadius);
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator.setDuration(1000);
                    animator.start();
                    int i = 0;
                    final String[] subjectData = {""};
                    Observable.from(ClassListChecked)
                            .subscribe(new Action1<String>() {
                                @Override
                                public void call(String s) {
                                    subjectData[0] += (s + ",");
                                }
                            });

                    L.d(idnumber);
                    ListMap.put("identify", identify);
                    ListMap.put("name", truename);
                    ListMap.put("idNumber", idnumber);
                    ListMap.put("grade", grad);
                    ListMap.put("timePayLow", timepaylow);
                    ListMap.put("timePayHight", timepayhight);
                    ListMap.put("major", majo);
                    ListMap.put("schoolteach", school);
                    ListMap.put("teachCareer", teachCaree);
                    ListMap.put("sex", sex);
                    ListMap.put("subject", subjectData[0]);
                    ListMap.put("uuid", String.valueOf(uuid));
                    ListMap.put("phone", "13125375549");
                    if (path != null) {
                        selectedPhotos.add(path);
                    }

                    if (photos != null) {
                        selectedPhotos.addAll(photos);
                    }
                    identifyPresenter.updateInfo(ListMap, selectedPhotos);
                }
            });
            builder.setNegativeButton("还有补充", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create();
            builder.show();
        }
    }

    //上层与下层交流层传输数据的接口
    @Override
    public void updateInfo() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onFiled("");
            exitBy2Click(); //调用双击退出函数
            //if uploading
//           identifyPresenter.cancle();
            return true;
        }
        return false;
    }

    @Override
    public void onSuccess(String info) {
        Intent i = new Intent(identityVerify.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onFiled(String msg) {
        if (!msg.equals(""))
            Snackbar.make(relativeLayout, msg, Snackbar.LENGTH_LONG).show();
        rela_load.setVisibility(View.GONE);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {
            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if (!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                startAlphaAnimation(toolbar, ALPHA_ANIMATIONS_DURATION_TOOLBAR, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {
            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                startAlphaAnimation(toolbar, 100, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    @Override
    public void setPresenter(identifyVerifyContract.Presenter presenter) {
        identifyPresenter = presenter;
    }


    private static Boolean isExiting = false;


    private void exitBy2Click() {
        Timer tExit = null;
        if (!isExiting) {
            isExiting = true; // 准备退出
            Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExiting = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            finish();
            System.exit(0);
        }
    }
}
