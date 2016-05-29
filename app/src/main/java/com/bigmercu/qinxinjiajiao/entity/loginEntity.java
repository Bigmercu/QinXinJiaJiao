package com.bigmercu.qinxinjiajiao.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bigmercu on 16/4/8.
 */
@JsonObject
public class loginEntity implements Parcelable {

    /**
     * code : 1
     * mes : 登陆成功
     * data : [{"tutor_name":"王老师","tutor_course":"初中物理,初中生物,高中地理,","tutor_school":null}]
     */

    @JsonField
    private int code;

    @JsonField
    private String mes;


    /**
     * tutor_name : 王老师
     * tutor_course : 初中物理,初中生物,高中地理,
     * tutor_school : null
     */
    @JsonField
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @JsonObject
    public static class DataBean implements Parcelable {
        @JsonField
        private String tutor_name;
        @JsonField
        private String tutor_course;
        @JsonField
        private String tutor_school;
        @JsonField
        private String tutor_profile;

        public String getTutor_name() {
            return tutor_name;
        }

        public void setTutor_name(String tutor_name) {
            this.tutor_name = tutor_name;
        }

        public String getTutor_course() {
            return tutor_course;
        }
        public String getTutor_profile(){
            return tutor_profile;
        }

        public void setTutor_course(String tutor_course) {
            this.tutor_course = tutor_course;
        }

        public String getTutor_school() {
            return tutor_school;
        }

        public void setTutor_school(String tutor_school) {
            this.tutor_school = tutor_school;
        }

        public void setTutor_profile(String tutor_profile){
            this.tutor_profile = tutor_profile;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.tutor_name);
            dest.writeString(this.tutor_course);
            dest.writeString(this.tutor_school);
            dest.writeString(this.tutor_profile);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.tutor_name = in.readString();
            this.tutor_course = in.readString();
            this.tutor_school = in.readString();
            this.tutor_profile = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeString(this.mes);
        dest.writeList(this.data);
    }

    public loginEntity() {
    }

    protected loginEntity(Parcel in) {
        this.code = in.readInt();
        this.mes = in.readString();
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Creator<loginEntity> CREATOR = new Creator<loginEntity>() {
        @Override
        public loginEntity createFromParcel(Parcel source) {
            return new loginEntity(source);
        }

        @Override
        public loginEntity[] newArray(int size) {
            return new loginEntity[size];
        }
    };
}
