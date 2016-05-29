package com.bigmercu.qinxinjiajiao.Util.Adapter;

import android.content.Context;

import com.bigmercu.qinxinjiajiao.R;
import com.bigmercu.qinxinjiajiao.entity.MainInfoHome;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by bigmercu on 16/4/16.
 */
public class MainAdapter extends BaseQuickAdapter<MainInfoHome.DataBean> {


    public MainAdapter(Context context, int layoutResId,List<MainInfoHome.DataBean> dataBeanList) {
        super(context, layoutResId, dataBeanList);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final MainInfoHome.DataBean dataBean) {
//        baseViewHolder.setText(R.id.textView5,dataBean.getTutor_course());

        Observable.just(dataBean).map(new Func1<MainInfoHome.DataBean, String>() {
            @Override
            public String call(MainInfoHome.DataBean dataBean) {
                return "http://192.168.1.106/enterprise/Public/" + dataBean.getTutor_profile();
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                baseViewHolder.setImageUrl(R.id.profile_image,s);
            }
        });

        baseViewHolder.setText(R.id.textView8,dataBean.getTutor_course());
        baseViewHolder.setText(R.id.textView6,dataBean.getTutor_school());
        baseViewHolder.setText(R.id.textView5,dataBean.getTutor_name());
    }
}
