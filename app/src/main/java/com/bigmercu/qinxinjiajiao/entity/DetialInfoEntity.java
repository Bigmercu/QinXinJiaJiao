package com.bigmercu.qinxinjiajiao.entity;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by bigmercu on 16/5/17.
 */
@JsonObject
public class DetialInfoEntity {
    /**
     * code : 1
     * mes : 返回数据成功！
     * data : {"tutor_phone_num":"18146686082","tutor_profile":"Uploads/20160509/57307fec61dc0.JPEG","tutor_type":"0","tutor_name":"夏老师4","tutor_sex":"0","tutor_pn":"412829199205200838","tutor_school":"江西财经大学","tutor_grade":null,"tutor_course":"初中语文,初中英语,高中历史,","tutor_time_pay":"70","teach_tutor_pro":"计算机中加2","teach_tutor_certificate":"Uploads/20160509/57307fec62f6c.JPEG,Uploads/20160509/57307fec63e39.JPEG,Uploads/20160509/57307fec64ce2.JPEG,Uploads/20160509/57307fec65abf.JPEG,Uploads/20160509/57307fec6684d.JPEG,Uploads/20160509/57307fec6784f.JPEG,Uploads/20160509/57307fec687c0.JPEG,Uploads/20160509/57307fec69673.JPEG,Uploads/20160509/57307fec69b26.JPEG,Uploads/20160509/57307fec6a9c6.JPEG,Uploads/20160509/57307fec6afb8.JPEG,Uploads/20160509/57307fec6bdc6.JPEG","teach_tutor_exp":"哈哈哈哈哈还盖茨比"}
     */
    @JsonField
    private int code;
    @JsonField
    private String mes;
    /**
     * tutor_phone_num : 18146686082
     * tutor_profile : Uploads/20160509/57307fec61dc0.JPEG
     * tutor_type : 0
     * tutor_name : 夏老师4
     * tutor_sex : 0
     * tutor_pn : 412829199205200838
     * tutor_school : 江西财经大学
     * tutor_grade : null
     * tutor_course : 初中语文,初中英语,高中历史,
     * tutor_time_pay : 70
     * teach_tutor_pro : 计算机中加2
     * teach_tutor_certificate : Uploads/20160509/57307fec62f6c.JPEG,Uploads/20160509/57307fec63e39.JPEG,Uploads/20160509/57307fec64ce2.JPEG,Uploads/20160509/57307fec65abf.JPEG,Uploads/20160509/57307fec6684d.JPEG,Uploads/20160509/57307fec6784f.JPEG,Uploads/20160509/57307fec687c0.JPEG,Uploads/20160509/57307fec69673.JPEG,Uploads/20160509/57307fec69b26.JPEG,Uploads/20160509/57307fec6a9c6.JPEG,Uploads/20160509/57307fec6afb8.JPEG,Uploads/20160509/57307fec6bdc6.JPEG
     * teach_tutor_exp : 哈哈哈哈哈还盖茨比
     */
    @JsonField
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @JsonObject
    public static class DataBean {
        @JsonField
        private String tutor_phone_num;
        @JsonField
        private String tutor_profile;
        @JsonField
        private String tutor_type;
        @JsonField
        private String tutor_name;
        @JsonField
        private String tutor_sex;
        @JsonField
        private String tutor_pn;
        @JsonField
        private String tutor_school;
        @JsonField
        private Object tutor_grade;
        @JsonField
        private String tutor_course;
        @JsonField
        private String tutor_time_pay;
        @JsonField
        private String teach_tutor_pro;
        @JsonField
        private String teach_tutor_certificate;
        @JsonField
        private String teach_tutor_exp;

        public String getTutor_phone_num() {
            return tutor_phone_num;
        }

        public void setTutor_phone_num(String tutor_phone_num) {
            this.tutor_phone_num = tutor_phone_num;
        }

        public String getTutor_profile() {
            return tutor_profile;
        }

        public void setTutor_profile(String tutor_profile) {
            this.tutor_profile = tutor_profile;
        }

        public String getTutor_type() {
            return tutor_type;
        }

        public void setTutor_type(String tutor_type) {
            this.tutor_type = tutor_type;
        }

        public String getTutor_name() {
            return tutor_name;
        }

        public void setTutor_name(String tutor_name) {
            this.tutor_name = tutor_name;
        }

        public String getTutor_sex() {
            return tutor_sex;
        }

        public void setTutor_sex(String tutor_sex) {
            this.tutor_sex = tutor_sex;
        }

        public String getTutor_pn() {
            return tutor_pn;
        }

        public void setTutor_pn(String tutor_pn) {
            this.tutor_pn = tutor_pn;
        }

        public String getTutor_school() {
            return tutor_school;
        }

        public void setTutor_school(String tutor_school) {
            this.tutor_school = tutor_school;
        }

        public Object getTutor_grade() {
            return tutor_grade;
        }

        public void setTutor_grade(Object tutor_grade) {
            this.tutor_grade = tutor_grade;
        }

        public String getTutor_course() {
            return tutor_course;
        }

        public void setTutor_course(String tutor_course) {
            this.tutor_course = tutor_course;
        }

        public String getTutor_time_pay() {
            return tutor_time_pay;
        }

        public void setTutor_time_pay(String tutor_time_pay) {
            this.tutor_time_pay = tutor_time_pay;
        }

        public String getTeach_tutor_pro() {
            return teach_tutor_pro;
        }

        public void setTeach_tutor_pro(String teach_tutor_pro) {
            this.teach_tutor_pro = teach_tutor_pro;
        }

        public String getTeach_tutor_certificate() {
            return teach_tutor_certificate;
        }

        public void setTeach_tutor_certificate(String teach_tutor_certificate) {
            this.teach_tutor_certificate = teach_tutor_certificate;
        }

        public String getTeach_tutor_exp() {
            return teach_tutor_exp;
        }

        public void setTeach_tutor_exp(String teach_tutor_exp) {
            this.teach_tutor_exp = teach_tutor_exp;
        }
    }
}
