package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by bigmercu on 16/5/19.
 */
public class GreenDaoGenerate {
    public static void main(String[] args){
        Schema schema = new Schema(1, "com.bigmercu.qinxinjiajiao.dao");
        addPerson(schema);
        try {
            new DaoGenerator().generateAll(schema, "../QinXinJiaJiao/app/src/main/java-gen");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addPerson(Schema schema) {
        Entity person = schema.addEntity("Person");
        person.addIdProperty().primaryKey();
        person.addStringProperty("phone").notNull();
        person.addStringProperty("uuid").notNull();
        person.addStringProperty("password").notNull();
        person.addDateProperty("date");
        person.addStringProperty("comment");
    }
}