package com.bigmercu.qinxinjiajiao.UI.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bigmercu.qinxinjiajiao.R;
import com.bigmercu.qinxinjiajiao.Util.Adapter.MainAdapter;
import com.bigmercu.qinxinjiajiao.Util.DividerItemDecoration;
import com.bigmercu.qinxinjiajiao.Util.RxBus;
import com.bigmercu.qinxinjiajiao.contract.homeContract;
import com.bigmercu.qinxinjiajiao.entity.MainInfoHome;
import com.bigmercu.qinxinjiajiao.entity.countEntity;
import com.bigmercu.qinxinjiajiao.entity.messageEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.animation.BaseAnimation;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import sj.mblog.L;


public class HomeFragment extends Fragment implements
        homeContract.View {

    private static final String TAG = "homeFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final String KEY_LAYOUT_MANAGER1 = "layoutManager1";
    protected RecyclerView.LayoutManager mLayoutManager;
    protected ArrayList<String> mDataset;
    private static final int PAGE_SIZE = 5;
    private int mCurrentCounter = 0;
    private homeContract.Presenter homePresenter;
    private int status = -1;


    // TODO: Rename and change types of parameters
    private MainAdapter mainAdapter;
    private OnFragmentInteractionListener mListener;
    private List<com.bigmercu.qinxinjiajiao.entity.MainInfoHome.DataBean> HomeDataBeanList;
    private int num = 2;
    private LinearLayoutManager linearLayoutManager;

    public HomeFragment() {
        // Required empty public constructor
    }
    public static HomeFragment HomeFragment(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.d("oncreate");
    }
    @Bind(R.id.recyclerView_main)
    RecyclerView recyclerView;
    @Bind(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        L.d("oncreateView");
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        view.setTag(TAG);
        ButterKnife.bind(this, view);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_red_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_purple,
                android.R.color.holo_blue_dark);
        initAdapter();
        return view;
    }

    private void initAdapter() {
        mainAdapter = new MainAdapter(getActivity(), R.layout.card, HomeDataBeanList);
        //实现一个减慢smoothccroll的linearLayoutManager
        linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false){
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                LinearSmoothScroller smoothScroller
                        = new LinearSmoothScroller(recyclerView.getContext()) {
                    @Override
                    protected int calculateTimeForScrolling(int dx) {
                        dx*=3;
                        return super.calculateTimeForScrolling(dx);
                    }
                    @Override
                    public PointF computeScrollVectorForPosition(int targetPosition) {
                        return linearLayoutManager.computeScrollVectorForPosition(targetPosition);
                    }
                };
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            }
        };
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        this.mLayoutManager = linearLayoutManager;
        mCurrentCounter = mainAdapter.getItemCount();
        mainAdapter.openLoadAnimation(new BaseAnimation() {
            @Override
            public Animator[] getAnimators(View view) {
                return new Animator[]{
                        ObjectAnimator.ofFloat(view, "TranslationY", 10, 0)
                };
            }
        });

        final int[] lastVisibleItem = new int[1];//最后一条可见的item
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int totalCount = linearLayoutManager.getItemCount();//RecyclerView中的总的条目的数量(此处代表的是可见的和不可见的总数)
                if(newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem[0] + 1==totalCount){
                    //此处添加加载更多的代码，一般为取数据库或者加载网络等
                    onLoadMore();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //每次滑动的时候要更新最后一条可见的item的id
                lastVisibleItem[0] = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
        mainAdapter.openLoadMore(PAGE_SIZE, true);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.loading_more, null);
        mainAdapter.setLoadingView(view);
        mainAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                String id = mainAdapter.getItem(i).getTutor_phone_num();
                RxBus.getDefault().post(new messageEntity(id));
            }
        });
        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        view.setLayoutParams(layoutParams);
        mainAdapter.setEmptyView(view);
        recyclerView.setAdapter(mainAdapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        View topView = mLayoutManager.getChildAt(0);          //获取可视的第一个view
        if (topView != null) {
            int lastOffset = topView.getTop();                                   //获取与该view的顶部的偏移量
            int lastPosition = mLayoutManager.getPosition(topView);  //得到该View的数组位置
            savedInstanceState.putInt(KEY_LAYOUT_MANAGER, lastOffset);
            savedInstanceState.putInt(KEY_LAYOUT_MANAGER1, lastPosition);
        }
    }


    @Override
    public void onAttach(Context context) {
        L.d("getInfo2");
        super.onAttach(context);
        L.d("onAttach__HOME");
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        L.d("onResume");
        if (homePresenter != null) {
            homePresenter.start();
        } else {
            //初始化presenter
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        L.d("onDttach__HOME");
        mListener = null;
    }

    private void Refresh() {
        swipeRefreshLayout.setRefreshing(true);
        L.d("shuaxin");
        num = 2;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (status == -1) {
                    homePresenter.getMore(0x01, 1);
                    status = 2;
                } else if (status == 1) {
                    homePresenter.getMore(0x01, 1);
                    status = 2;
                    //cancle loading more
                } else {
                    Toast.makeText(getContext(), "refresh", Toast.LENGTH_SHORT).show();
                }
            }
        },1000);
    }
    private void onLoadMore() {
        swipeRefreshLayout.setRefreshing(true);
        L.d("more");
        if (status == -1) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(homePresenter != null) {
                        homePresenter.getMore(0X02, num++);
                    }else {
                        //处理homePresenter=null的情况
                    }
                    status = 1;
                }
            }, 1000);
        }
    }

    @Override
    public void setPresenter(homeContract.Presenter presenter) {
        this.homePresenter = presenter;
    }


    @Override
    public void setData(MainInfoHome mainInfoHome) {
        swipeRefreshLayout.setRefreshing(false);
        final int offset = mainAdapter.getItemCount();
        Observable.just(mainInfoHome)
                .map(new Func1<MainInfoHome,
                        List<MainInfoHome.DataBean>>() {
                    @Override
                    public List<MainInfoHome.DataBean> call(MainInfoHome mainInfoHome1) {
                        return mainInfoHome1.getData();
                    }
                }).subscribe(new Action1<List<MainInfoHome.DataBean>>() {
            @Override
            public void call(List<MainInfoHome.DataBean> dataBeanList) {
                if (status == 1) {
                    mainAdapter.notifyDataChangedAfterLoadMore(dataBeanList, true);
                    recyclerView.smoothScrollToPosition(offset + 1);
                } else {
                    mainAdapter.setNewData(dataBeanList);
                }
                status = -1;
            }
        });
        L.d("yes");
    }



    @Override
    public void OnLoadingMoreFiled(String msg) {
        Snackbar.make(recyclerView,msg,Snackbar.LENGTH_SHORT).show();
        mainAdapter.notifyDataChangedAfterLoadMore(false);
        status = -1;
        swipeRefreshLayout.setRefreshing(false);
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
