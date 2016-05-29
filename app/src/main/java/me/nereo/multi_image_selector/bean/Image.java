package me.nereo.multi_image_selector.bean;

import android.graphics.Bitmap;

/**
 * 图片实体
 * Created by Nereo on 2015/4/7.
 */
public class Image {
    public String path;
    public Bitmap bitmap;
    public String name;
    public long time;

    public Image(String path, String name, long time){
        this.path = path;
        this.name = name;
        this.time = time;
    }

    public Image(String path) {
        this.path = path;
    }
    @Override
    public boolean equals(Object o) {
        try {
            Image other = (Image) o;
            return this.path.equalsIgnoreCase(other.path);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        return super.equals(o);
    }
}
