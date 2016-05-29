package com.bigmercu.qinxinjiajiao.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bigmercu on 16/4/7.
 */
public class registerEntity {
    public String mes;

    @SerializedName("identity")
    public String identity;

    @SerializedName("phone")
    public String phone;

    @SerializedName("password")
    public String password;


    public void setIdentity(String identity){
        this.identity = identity;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getIdentity() {
        return identity;
    }

    public String getPhone(){
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public void setCode(String Code){
        this.mes = Code;
    }

    public String getCode(){
        return mes;
    }
}
