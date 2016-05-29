package com.bigmercu.qinxinjiajiao.entity;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

/**
 * Created by bigmercu on 16/4/11.
 */
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class identifyEntity {
    /**
     * code : 1
     * mes : 登陆成功
     * data : [{"user_phone_num":"13125375549","user_password":"14e1b600b1fd579f47433b88e8d85291","user_address":"wangpw","user_type":"1","user_reg_time":"back_touxiang-05-06 15:51:51","user_login_time":"back_touxiang-05-06 15:51:51","tutor_phone_num":"13125375549","tutor_profile":"Uploads/20160509/57307fec61dc0.JPEG","tutor_type":"0","tutor_name":"王鹏伟","tutor_sex":"0","tutor_pn":"412829199205200838","tutor_school":null,"tutor_grade":null,"tutor_course":"初中物理,初中生物,高中地理,","tutor_time_pay":"70","teach_tutor_pro":"计算机中加","teach_tutor_certificate":"Uploads/20160509/57307fec62f6c.JPEG,Uploads/20160509/57307fec63e39.JPEG,Uploads/20160509/57307fec64ce2.JPEG,Uploads/20160509/57307fec65abf.JPEG,Uploads/20160509/57307fec6684d.JPEG,Uploads/20160509/57307fec6784f.JPEG,Uploads/20160509/57307fec687c0.JPEG,Uploads/20160509/57307fec69673.JPEG,Uploads/20160509/57307fec69b26.JPEG,Uploads/20160509/57307fec6a9c6.JPEG,Uploads/20160509/57307fec6afb8.JPEG,Uploads/20160509/57307fec6bdc6.JPEG","teach_tutor_exp":"哈哈哈哈哈还盖茨比"}]
     */

    @JsonField(name = "code")
    private int code;
    @JsonField(name = "mes")
    private String mes;
    /**
     * user_phone_num : 13125375549
     * user_password : 14e1b600b1fd579f47433b88e8d85291
     * user_address : wangpw
     * user_type : 1
     * user_reg_time : back_touxiang-05-06 15:51:51
     * user_login_time : back_touxiang-05-06 15:51:51
     * tutor_phone_num : 13125375549
     * tutor_profile : Uploads/20160509/57307fec61dc0.JPEG
     * tutor_type : 0
     * tutor_name : 王鹏伟
     * tutor_sex : 0
     * tutor_pn : 412829199205200838
     * tutor_school : null
     * tutor_grade : null
     * tutor_course : 初中物理,初中生物,高中地理,
     * tutor_time_pay : 70
     * teach_tutor_pro : 计算机中加
     * teach_tutor_certificate : Uploads/20160509/57307fec62f6c.JPEG,Uploads/20160509/57307fec63e39.JPEG,Uploads/20160509/57307fec64ce2.JPEG,Uploads/20160509/57307fec65abf.JPEG,Uploads/20160509/57307fec6684d.JPEG,Uploads/20160509/57307fec6784f.JPEG,Uploads/20160509/57307fec687c0.JPEG,Uploads/20160509/57307fec69673.JPEG,Uploads/20160509/57307fec69b26.JPEG,Uploads/20160509/57307fec6a9c6.JPEG,Uploads/20160509/57307fec6afb8.JPEG,Uploads/20160509/57307fec6bdc6.JPEG
     * teach_tutor_exp : 哈哈哈哈哈还盖茨比
     */

    @JsonField(name = "data")
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

    public static class DataBean {
        private String user_phone_num;
        private String user_password;
        private String user_address;
        private String user_type;
        private String user_reg_time;
        private String user_login_time;
        private String tutor_phone_num;
        private String tutor_profile;
        private String tutor_type;
        private String tutor_name;
        private String tutor_sex;
        private String tutor_pn;
        private Object tutor_school;
        private Object tutor_grade;
        private String tutor_course;
        private String tutor_time_pay;
        private String teach_tutor_pro;
        private String teach_tutor_certificate;
        private String teach_tutor_exp;

        public String getUser_phone_num() {
            return user_phone_num;
        }

        public void setUser_phone_num(String user_phone_num) {
            this.user_phone_num = user_phone_num;
        }

        public String getUser_password() {
            return user_password;
        }

        public void setUser_password(String user_password) {
            this.user_password = user_password;
        }

        public String getUser_address() {
            return user_address;
        }

        public void setUser_address(String user_address) {
            this.user_address = user_address;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getUser_reg_time() {
            return user_reg_time;
        }

        public void setUser_reg_time(String user_reg_time) {
            this.user_reg_time = user_reg_time;
        }

        public String getUser_login_time() {
            return user_login_time;
        }

        public void setUser_login_time(String user_login_time) {
            this.user_login_time = user_login_time;
        }

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

        public Object getTutor_school() {
            return tutor_school;
        }

        public void setTutor_school(Object tutor_school) {
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


//    @JsonField(name = "mes")
//    String mes;
//    @JsonField(name = "code")
//    String code;
//
//    public void setMes(String mes) {
//        this.mes = mes;
//    }
//
//    public String getMes() {
//        return mes;
//    }
//public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getCode() {
//        return code;
//    }




}
