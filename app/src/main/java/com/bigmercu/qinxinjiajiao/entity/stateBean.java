package com.bigmercu.qinxinjiajiao.entity;

/**
 * Created by bigmercu on 16/4/18.
 */
public class stateBean {
    String state;
    int position;
    public stateBean(String state){
        this.state = state;
    }

    public stateBean(int adapterPosition) {
        this.position = adapterPosition;
    }


    public String getState(){
        return state;
    }
}
