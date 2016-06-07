package com.bigmercu.qinxinjiajiao.UI.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.appyvet.rangebar.RangeBar;
import com.bigmercu.qinxinjiajiao.R;
import com.bigmercu.qinxinjiajiao.contract.bigmercuContract;
import com.bigmercu.qinxinjiajiao.entity.DetialInfoEntity;
import com.bigmercu.qinxinjiajiao.presenter.impl.bigmercuPresenterImpl;
import com.github.florent37.picassopalette.PicassoPalette;
import com.hhl.library.FlowTagLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
//import me.gujun.android.taggroup.TagGroup;
import sj.mblog.L;

public class MainDetialActivity extends AppCompatActivity implements
        AMapLocationListener,
        LocationSource,
        bigmercuContract.View {

    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private AMap aMap;
    private MapView mapView;
    private OnLocationChangedListener mListener;
    private String uuid = null;
    private bigmercuContract.Presenter presenter;
    private LatLng latLng = null;
    private byte isZaned = 1 << 1;
    private boolean isAuto = false;
    private boolean isAuto1 = false;

    @Bind(R.id.textView37)
    TextView loading;
    //    @Bind(R.id.submit)
//    TextView text;
    @Bind(R.id.ns)
    NestedScrollView ns;
    @Bind(R.id.ns_1)
    NestedScrollView ns_1;

    @Bind(R.id.toolbar_detial)
    Toolbar toolbar1;
    @Bind(R.id.app_bar_a)
    AppBarLayout appbar;
    @Bind(R.id.detialTouxiang)
    CircleImageView circleImageView;
    @Bind(R.id.textView44)
    TextView textView1;
    @Bind(R.id.textView45)
    TextView textView2;
    @Bind(R.id.wokao)
    TextView detial_name;
    @Bind(R.id.textView33)
    TextView detial_name_1;
    @Bind(R.id.detial_fab)
    FloatingActionButton fab;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.dewrewtre)
    RelativeLayout rela;
    @Bind(R.id.imageButton2)
    ImageButton back;
    @Bind(R.id.imageButton3)
    ImageButton zan;
    @Bind(R.id.erweima)
    ImageView erweima;
    @Bind(R.id.l11)
    CardView cardView;
    @Bind(R.id.erweima_big)
    ImageView erweimaBig;
    @Bind(R.id.checkBox5)
    CheckBox allChoose;
    @Bind(R.id.checkBox6)
    CheckBox hoose1;
    @Bind(R.id.checkBox7)
    CheckBox hoose2;
    @Bind(R.id.checkBox8)
    CheckBox hoose3;
    @Bind(R.id.checkBox9)
    CheckBox hoose4;
    @Bind(R.id.checkBox10)
    CheckBox hoose5;
    @Bind(R.id.checkBox11)
    CheckBox hoose6;
    @Bind(R.id.checkBox12)
    CheckBox hoose7;
    @Bind(R.id.textView13)
    TextView textViewKeshi;
    @Bind(R.id.textView47)
    TextView textViewKeshiLong;
    @Bind(R.id.rangebar_keshifei)
    RangeBar rangeBarKeshi;
    @Bind(R.id.rangebar_keshi)
    RangeBar rangeBarKeshiLong;
    @Bind(R.id.tagChecked_detial)
    FlowTagLayout tagContainerLayout;
    @Bind(R.id.tagChecked_detial_checked)
    FlowTagLayout tagContainerLayout_checked;


    private String tutorId;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_detial);
        ButterKnife.bind(this);
//        text.setText("正在从云端加载家教");
        loading.setVisibility(View.VISIBLE);
        new bigmercuPresenterImpl(this);
        toolbar1.inflateMenu(R.menu.menu_detial);
        tutorId = getIntent().getStringExtra("id");
        userId = getIntent().getStringExtra("uuid");
        presenter.getDetialInfo(tutorId);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        initMap(savedInstanceState);
        initDetialLinstner();
    }

        /*tutor_id 家教id, user_id 预约者id,
        王老师  19:33:37
        （coachSub） 预约课程
        王老师  19:33:45
        (schooltime) 上课时间
        王老师  19:33:55
        (hour)一次上几个小时
        王老师  19:34:03
        (timePay) 每小时多少钱
        王老师  19:34:09
        (classPI)预约地点*/

    @OnClick(R.id.detial_fab)
    void fabClick() {
        if (ns_1.getVisibility() == View.GONE) {
            ns.setVisibility(View.GONE);
            ns_1.setVisibility(View.VISIBLE);
            fab.setImageResource(R.mipmap.gou);
            mlocationClient.startLocation();
        } else {
            loading.setVisibility(View.VISIBLE);
            String tutorID = tutorId;
            String userID = uuid;
            String coachSub;
            String schoolTime = getSchoolTime();
            String hour = rangeBarKeshiLong.getLeftPinValue();
            L.d("hour");
            String timePay = rangeBarKeshiLong.getLeftPinValue();
            String classPI;
        }
    }

    private void initMap(Bundle savedInstanceState) {
        // 自定义系统定位蓝点
//        MyLocationStyle myLocationStyle = new MyLocationStyle();
//        // 自定义定位蓝点图标
//        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.in));
//        // 自定义精度范围的圆形边框颜色
//        myLocationStyle.strokeColor(Color.RED);
//        //自定义精度范围的圆形边框宽度
//        myLocationStyle.strokeWidth(2);
        // 将自定义的 myLocationStyle 对象添加到地图上
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
//        aMap.setMyLocationStyle(myLocationStyle);

        aMap.setLocationSource(this);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.getUiSettings().setZoomGesturesEnabled(true);
        aMap.getUiSettings().setZoomPosition(10);
        aMap.setMyLocationEnabled(true);
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    ns_1.requestDisallowInterceptTouchEvent(false);
                } else {
                    ns_1.requestDisallowInterceptTouchEvent(true);
                }
            }
        });
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                List<Marker> markerOptions = aMap.getMapScreenMarkers();
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.on);
                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
                if (markerOptions.size() > 0) {
                    markerOptions.get(0).setIcon(bitmapDescriptor);
                    markerOptions.get(0).setFlat(true);
                    markerOptions.get(0).setPosition(cameraPosition.target);
                }
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                List<Marker> markerOptions = aMap.getMapScreenMarkers();
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.in);
                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
                if (markerOptions.size() > 0) {
                    markerOptions.get(0).setIcon(bitmapDescriptor);
                    markerOptions.get(0).setFlat(true);
                    markerOptions.get(0).setPosition(cameraPosition.target);
                }
            }
        });
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
                latLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                deactivate();
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                L.d(errText);
            }
        }
        if (aMap != null) {
            aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(fab, "translationX", -150);
            ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(fab, "translationY", -150);
            objectAnimator.setDuration(1000);
            objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(objectAnimator1)
                    .with(objectAnimator);
            animatorSet.start();
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setInterval(10000);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        }
    }

    private void initDetialLinstner() {
        rangeBarKeshi.setSeekPinByValue(50);
        rangeBarKeshi.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                textViewKeshi.setText(rightPinValue);
            }
        });
        rangeBarKeshiLong.setSeekPinByValue(3);
        rangeBarKeshiLong.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                textViewKeshiLong.setText(rightPinValue);
            }
        });

//        tagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() {
//            @Override
//            public void onTagClick(int position, String text) {
//                L.d(text);
//                tagContainerLayout_checked.addTag(text);
//                tagContainerLayout.removeTag(position);
//            }
//
//            @Override
//            public void onTagLongClick(int position, String text) {
//
//            }
//        });


        toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ns.getVisibility() == View.GONE) {
                    ns.setVisibility(View.VISIBLE);
                    ns_1.setVisibility(View.GONE);
                    ns.smoothScrollTo(0, 0);
                } else {
                    finish();
                }
            }
        });
        zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((isZaned & (1 << 1)) > 0) {
                    zan.setImageResource(R.mipmap.zan_b);
                    isZaned = 1 << 2;
                } else {
                    zan.setImageResource(R.mipmap.zan_a);
                    isZaned = 1 << 1;
                }
            }
        });
//        tagContainerLayout = (TagContainerLayout) findViewById(R.id.tagChecked_detial);
        ns.setVisibility(View.INVISIBLE);
        ns_1.setVisibility(View.GONE);
        Picasso.with(getApplicationContext())
                .load(R.drawable.touxiang)
                .into(circleImageView);
        textView1.setText("");
        textView2.setText("");
        detial_name.setText("倾心家教");
        detial_name_1.setText("倾心家教");
        fab.setImageResource(R.mipmap.yuyue);
    }

    @Override
    public void setDetialInfo(DetialInfoEntity detialInfo) {
        loading.setVisibility(View.GONE);
        ns.setVisibility(View.VISIBLE);
//        tagContainerLayout.setTags(new String[]{"sadasdas", "sadasdas", "sadasdas", "sadasdas", "sadasdas", "sadasdas", "sadasdas"});
        String uri = "http://192.168.1.106/enterprise/Public/" + detialInfo.getData().getTutor_profile();
        Picasso.with(getApplicationContext()).load(R.drawable.touxiang).into(circleImageView,
                PicassoPalette.with(uri, circleImageView)
                        .use(PicassoPalette.Profile.VIBRANT_DARK)
                        .intoBackground(toolbar1)
                        .intoBackground(collapsingToolbarLayout)
                        .intoCallBack(new PicassoPalette.CallBack() {
                            @Override
                            public void onPaletteLoaded(Palette palette) {
                                int color = palette.getDarkVibrantColor(Color.RED);
                                rela.setBackgroundColor(color);
                            }
                        })
                        .use(PicassoPalette.Profile.VIBRANT_LIGHT)
                        .intoTextColor(detial_name)
                        .intoTextColor(textViewKeshi)
                        .intoTextColor(textViewKeshiLong)
                        .intoBackground(tagContainerLayout)
        );
        detial_name_1.setText(detialInfo.getData().getTutor_name());
        detial_name.setText(detialInfo.getData().getTutor_name());
        textView1.setText(detialInfo.getData().getTutor_time_pay());
        textView2.setText(detialInfo.getData().getTutor_school());
//        if(latLng!=null){
//            deactivate();
//        }
    }

    @Override
    public void orderSuccess() {
        L.d("success");
    }

    @Override
    public void orderFiled(String mes) {
        Snackbar.make(ns_1, mes, Snackbar.LENGTH_LONG).show();
    }

    @OnClick(R.id.erweima)
    void erweima() {
        erweima.setVisibility(View.GONE);
        erweimaBig.setVisibility(View.VISIBLE);
    }

    @OnCheckedChanged(R.id.checkBox5)
    void checkAll(boolean checked) {
        if (!isAuto) {
            hoose1.setChecked(checked);
            hoose2.setChecked(checked);
            hoose3.setChecked(checked);
            hoose4.setChecked(checked);
            hoose5.setChecked(checked);
            hoose6.setChecked(checked);
            hoose7.setChecked(checked);
            isAuto1 = true;
        } else {
            isAuto1 = false;
            isAuto = false;
        }
    }

    @OnCheckedChanged({R.id.checkBox6, R.id.checkBox7,
            R.id.checkBox8, R.id.checkBox9,
            R.id.checkBox10, R.id.checkBox11, R.id.checkBox12})
    void checkOne() {
        if (!isAuto1) {
            if (hoose1.isChecked() &&
                    hoose2.isChecked() && hoose3.isChecked() && hoose4.isChecked() && hoose5.isChecked() && hoose6.isChecked() && hoose7.isChecked()) {
                if (hoose1.isChecked()) {
                    isAuto = true;
                    allChoose.setChecked(true);
                } else {
                    allChoose.setChecked(false);
                    isAuto = true;
                }
            } else {
                if (allChoose.isChecked()) {
                    isAuto = true;
                    allChoose.setChecked(false);
                }
            }
        }
    }


    @OnClick(R.id.imageButton2)
    public void finsh() {
        finish();
    }

    @Override
    public void deactivate() {
        L.d("deactivate");
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        if (mapView != null)
            mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void setPresenter(bigmercuContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private String getSchoolTime() {
        String schoolTime = "";
        if (hoose1.isChecked()) {
            schoolTime += 1;
        }
        if (hoose2.isChecked()) {
            schoolTime += 2;
        }
        if (hoose3.isChecked()) {
            schoolTime += 3;
        }
        if (hoose4.isChecked()) {
            schoolTime += 4;
        }
        if (hoose5.isChecked()) {
            schoolTime += 5;
        }
        if (hoose6.isChecked()) {
            schoolTime += 6;
        }
        if (hoose7.isChecked()) {
            schoolTime += 7;
        }
        return schoolTime;
    }
}
