package com.bigmercu.qinxinjiajiao.entity;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by bigmercu on 16/5/10.
 */
@JsonObject
public class messageEntity {
    @JsonField
    private int code;
    @JsonField
    private String mes;

    public messageEntity(String id) {
        this.mes = id;
    }

    public messageEntity() {

    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getMes() {
        return mes;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
