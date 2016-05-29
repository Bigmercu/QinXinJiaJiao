package com.bigmercu.qinxinjiajiao.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bigmercu on 16/4/19.
 */
@JsonObject
public class MainInfoHome implements Parcelable {

    /**
     * code : 1
     * mes : 登陆成功
     * data : [{"tutor_name":"吴老师","tutor_profile":"Uploads/20160509/57307fec61dc0.JPEG","tutor_course":"初中生物,高中地理,","tutor_school":"江西医科大学"},{"tutor_name":"夏老师","tutor_profile":"Uploads/20160509/57307fec61dc0.JPEG","tutor_course":"初中语文,初中英语,高中历史,","tutor_school":"江西财经大学"},{"tutor_name":"兰老师","tutor_profile":"Uploads/20160509/57307fec61dc0.JPEG","tutor_course":"高中地理","tutor_school":"信阳师范大学"},{"tutor_name":"徐老师","tutor_profile":"Uploads/20160509/57307fec61dc0.JPEG","tutor_course":"初中物理,初中生物,","tutor_school":"河南师范大学"},{"tutor_name":"方老师","tutor_profile":"Uploads/20160509/57307fec61dc0.JPEG","tutor_course":"初中物理,高中地理,","tutor_school":"河南理工大学"},{"tutor_name":"李老师","tutor_profile":"Uploads/20160509/57307fec61dc0.JPEG","tutor_course":"初中英语","tutor_school":"江西师范大学"},{"tutor_name":"肖老师","tutor_profile":"Uploads/20160509/57307fec61dc0.JPEG","tutor_course":"初中物理,初中英语,高中历史,","tutor_school":"江西南昌大学"},{"tutor_name":"吴老师","tutor_profile":"Uploads/20160509/57307fec61dc0.JPEG","tutor_course":"初中生物,高中地理,","tutor_school":"江西医科大学"},{"tutor_name":"王老师","tutor_profile":"Uploads/20160509/57307fec61dc0.JPEG","tutor_course":"初中生物","tutor_school":"洛阳师范大学"},{"tutor_name":"曹老师","tutor_profile":"Uploads/20160509/57307fec61dc0.JPEG","tutor_course":"初中物理","tutor_school":"河南财经大学"},{"tutor_name":"曹老师","tutor_profile":"Uploads/20160509/57307fec61dc0.JPEG","tutor_course":"初中物理","tutor_school":"河南财经大学"},{"tutor_name":"肖老师","tutor_profile":"Uploads/20160509/57307fec61dc0.JPEG","tutor_course":"初中物理,初中英语,高中历史,","tutor_school":"江西南昌大学"},{"tutor_name":"李老师","tutor_profile":"Uploads/20160509/57307fec61dc0.JPEG","tutor_course":"初中英语","tutor_school":"江西师范大学"},{"tutor_name":"方老师","tutor_profile":"Uploads/20160509/57307fec61dc0.JPEG","tutor_course":"初中物理,高中地理,","tutor_school":"河南理工大学"},{"tutor_name":"徐老师","tutor_profile":"Uploads/20160509/57307fec61dc0.JPEG","tutor_course":"初中物理,初中生物,","tutor_school":"河南师范大学"},{"tutor_name":"兰老师","tutor_profile":"Uploads/20160509/57307fec61dc0.JPEG","tutor_course":"高中地理","tutor_school":"信阳师范大学"},{"tutor_name":"夏老师","tutor_profile":"Uploads/20160509/57307fec61dc0.JPEG","tutor_course":"初中语文,初中英语,高中历史,","tutor_school":"江西财经大学"},{"tutor_name":"王老师","tutor_profile":"Uploads/20160509/57307fec61dc0.JPEG","tutor_course":"初中生物","tutor_school":"洛阳师范大学"},{"tutor_name":"你老师","tutor_profile":"Uploads/20160516/5739aa6e0340c.JPEG","tutor_course":"初中全科作业辅导,高中政治,小学全科作业辅导,高中地理,初中历史,","tutor_school":"你大爷你大爷"}]
     * uuid : 55f8f313e4bee83a4f7184c4c1b01746
     */

    @JsonField
    private int code;
    @JsonField
    private String mes;
    @JsonField
    private String uuid;
    /**
     * tutor_name : 吴老师
     * tutor_profile : Uploads/20160509/57307fec61dc0.JPEG
     * tutor_course : 初中生物,高中地理,
     * tutor_school : 江西医科大学
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
        private String tutor_profile;
        @JsonField
        private String tutor_course;
        @JsonField
        private String tutor_school;
        @JsonField
        private String tutor_phone_num;

        public String getTutor_phone_num() {
            return tutor_phone_num;
        }

        public void setTutor_phone_num(String tutor_phone_num) {
            this.tutor_phone_num = tutor_phone_num;
        }

        public String getTutor_name() {
            return tutor_name;
        }

        public void setTutor_name(String tutor_name) {
            this.tutor_name = tutor_name;
        }

        public String getTutor_profile() {
            return tutor_profile;
        }

        public void setTutor_profile(String tutor_profile) {
            this.tutor_profile = tutor_profile;
        }

        public String getTutor_course() {
            return tutor_course;
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.tutor_name);
            dest.writeString(this.tutor_profile);
            dest.writeString(this.tutor_course);
            dest.writeString(this.tutor_school);
            dest.writeString(this.tutor_phone_num);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.tutor_name = in.readString();
            this.tutor_profile = in.readString();
            this.tutor_course = in.readString();
            this.tutor_school = in.readString();
            this.tutor_phone_num = in.readString();
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
        dest.writeString(this.uuid);
        dest.writeList(this.data);
    }

    public MainInfoHome() {
    }

    protected MainInfoHome(Parcel in) {
        this.code = in.readInt();
        this.mes = in.readString();
        this.uuid = in.readString();
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<MainInfoHome> CREATOR = new Parcelable.Creator<MainInfoHome>() {
        @Override
        public MainInfoHome createFromParcel(Parcel source) {
            return new MainInfoHome(source);
        }

        @Override
        public MainInfoHome[] newArray(int size) {
            return new MainInfoHome[size];
        }
    };
}
